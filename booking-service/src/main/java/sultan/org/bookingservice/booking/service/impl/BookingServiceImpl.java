package sultan.org.bookingservice.booking.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import sultan.org.bookingservice.booking.client.ItemClient;
import sultan.org.bookingservice.booking.client.PaymentClient;
import sultan.org.bookingservice.booking.client.ReviewClient;
import sultan.org.bookingservice.booking.enums.Status;
import sultan.org.bookingservice.booking.exceptions.BookingNotFoundException;
import sultan.org.bookingservice.booking.kafka.entity.BookingEvent;
import sultan.org.bookingservice.booking.model.dto.AvailabilityRequest;
import sultan.org.bookingservice.booking.model.dto.CreateBookingRequest;

import sultan.org.bookingservice.booking.model.dto.UpdateBookingRequest;
import sultan.org.bookingservice.booking.model.entity.Booking;
import sultan.org.bookingservice.booking.repository.BookingRepository;
import sultan.org.bookingservice.booking.service.BookingService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final ReviewClient reviewClient;
    private final ItemClient itemClient;
    private final PaymentClient paymentClient;
    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public List<Booking> getPendingReviews(UUID renterId) {

        List<Booking> completedBookings =
                bookingRepository.findByRenterIdAndBookingStatus(
                        renterId,
                        Status.COMPLETED
                );

        return completedBookings.stream()
                .filter(booking ->
                        !reviewClient.existsReview(
                                booking.getId(),
                                renterId
                        )
                )
                .toList();
    }

    /* ================= CREATE ================= */

    @Override
    public Booking createBooking(CreateBookingRequest request, UUID renterId) {
        UUID ownerId = getOwnerId(request.getItemId());
        if(renterId.equals(ownerId)){
            throw new RuntimeException("owner cannot rent own item");
        }

        AvailabilityRequest availability = new AvailabilityRequest(
                request.getItemId(),
                request.getStartDate(),
                request.getEndDate()
        );
        if (!checkAvailability(availability)) {
            throw new IllegalStateException("Item is not available for selected dates");
        }

        Booking booking = Booking.builder()
                .itemId(request.getItemId())
                .renterId(renterId)
                .ownerId(ownerId)
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .bookingStatus(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();


        Booking savedBooking = bookingRepository.save(booking);
        kafkaTemplate.send("booking-created", new BookingEvent(
                booking.getId(),
                booking.getOwnerId(),
                booking.getRenterId(),
                "CREATED"
        ));
        return savedBooking;
    }
    private UUID getOwnerId(Long itemId){
        return itemClient.getItemById(itemId);
    }

    /* ================= UPDATE ================= */

    @Override
    public Booking updateBooking(Long bookingId,
                                 UpdateBookingRequest request,
                                 UUID renterId) {
        Booking booking = getInternalBooking(bookingId);
        if (!booking.getRenterId().equals(renterId)) {
            throw new AccessDeniedException("Only renter can update booking");
        }

        if (booking.getBookingStatus() != Status.PENDING) {
            throw new IllegalStateException("Only PENDING booking can be updated");
        }

        booking.setStartDate(request.getStartDate());
        booking.setEndDate(request.getEndDate());
        booking.setUpdatedAt(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    /* ================= CANCEL ================= */

    @Override
    public void cancelBooking(Long bookingId, UUID userId) {
        Booking booking = getInternalBooking(bookingId);

        if (!booking.getRenterId().equals(userId)
                && !booking.getOwnerId().equals(userId)) {
            throw new AccessDeniedException("No access to cancel booking");
        }
        if (booking.getBookingStatus() == Status.PAID) {
            Long paymentId = paymentClient.getByBooking(bookingId).getBookingId();
            paymentClient.refund(paymentId);
        }

        booking.setBookingStatus(Status.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());

        bookingRepository.save(booking);
        kafkaTemplate.send("booking-cancelled", new BookingEvent(
                booking.getId(),
                booking.getOwnerId(),
                booking.getRenterId(),
                "CANCELLED"
        ));
    }

    /* ================= RENTER ================= */

    @Override
    public List<Booking> getBookingsAsRenter(UUID renterId) {
        return bookingRepository.findByRenterId(renterId);
    }

    @Override
    public List<Booking> getUpcomingBookings(UUID renterId) {
        return bookingRepository
                .findByRenterIdAndStartDateAfter(
                        renterId,
                        LocalDateTime.now()
                );
    }

    @Override
    public List<Booking> getPastBookings(UUID renterId) {
        return bookingRepository
                .findByRenterIdAndEndDateBefore(
                        renterId,
                        LocalDateTime.now()
                );
    }

    /* ================= OWNER ================= */

    @Override
    public List<Booking> getBookingsAsOwner(UUID ownerId) {
        return bookingRepository.findByOwnerId(ownerId);
    }

    @Override
    public List<Booking> getPendingBookings(UUID ownerId) {
        return bookingRepository
                .findByOwnerIdAndBookingStatus(
                        ownerId,
                        Status.PENDING
                );
    }

    @Override
    public List<Booking> getActiveBookings(UUID ownerId) {
        return bookingRepository
                .findByOwnerIdAndBookingStatus(
                        ownerId,
                        Status.CONFIRMED
                );
    }

    /* ================= ACTIONS ================= */

    @Override
    public void confirmBooking(Long bookingId, UUID ownerId) {
        Booking booking = getInternalBooking(bookingId);

        if (!booking.getOwnerId().equals(ownerId)) {
            throw new AccessDeniedException("Only owner can confirm");
        }


        if (booking.getBookingStatus() != Status.PAID) {
            throw new IllegalStateException("Only PAID booking can be confirmed");
        }

        booking.setBookingStatus(Status.CONFIRMED);
        booking.setUpdatedAt(LocalDateTime.now());

        bookingRepository.save(booking);

        kafkaTemplate.send("booking-confirmed", new BookingEvent(
                booking.getId(),
                booking.getOwnerId(),
                booking.getRenterId(),
                "CONFIRMED"
        ));

    }

    @Override
    public void completeBooking(Long bookingId, UUID ownerId) {
        Booking booking = getInternalBooking(bookingId);

        if (!booking.getOwnerId().equals(ownerId)) {
            throw new AccessDeniedException("Only owner can complete");
        }

        if (booking.getBookingStatus() != Status.CONFIRMED) {
            throw new IllegalStateException("Only CONFIRMED booking can be completed");
        }

        booking.setBookingStatus(Status.COMPLETED);
        booking.setUpdatedAt(LocalDateTime.now());

        bookingRepository.save(booking);
        kafkaTemplate.send("booking-completed", new BookingEvent(
                booking.getId(),
                booking.getOwnerId(),
                booking.getRenterId(),
                "COMPLETED"
        ));
    }

    /* ================= AVAILABILITY ================= */

    @Override
    public boolean checkAvailability(AvailabilityRequest request) {
        return bookingRepository
                .findConflictingBookings(
                        request.getItemId(),
                        request.getStartDate(),
                        request.getEndDate()
                ).isEmpty();
    }

    /* ================= INTERNAL ================= */

    @Override
    public Booking getInternalBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(bookingId));
    }

    @Override
    public void updateStatusFromPayment(Long bookingId, Status status) {
        Booking booking = getInternalBooking(bookingId);
        booking.setBookingStatus(status);
        booking.setUpdatedAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

}
