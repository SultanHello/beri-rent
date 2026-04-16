package sultan.org.itemservice.item.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sultan.org.itemservice.item.model.entity.Item;
import sultan.org.itemservice.item.model.entity.ItemImage;
import sultan.org.itemservice.item.repository.ItemImageRepository;
import sultan.org.itemservice.item.repository.ItemRepository;
import sultan.org.itemservice.item.service.ItemImageService;

@Service
@RequiredArgsConstructor
public class ItemImageServiceImpl implements ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final ItemRepository itemRepository;
    private final MinioService minioService;

    public void setMainImage(Long itemId, String imageUrl) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // Сбросить предыдущее главное фото
        itemImageRepository.findByItemIdAndIsMainTrue(itemId)
                .ifPresent(img -> {
                    img.setMain(false); // нужен сеттер или @Setter
                    itemImageRepository.save(img);
                });

        ItemImage image = ItemImage.builder()
                .item(item)
                .imageUrl(imageUrl)
                .isMain(true)
                .build();
        itemImageRepository.save(image);
    }

    public void addImage(Long itemId, String imageUrl) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        ItemImage image = ItemImage.builder()
                .item(item)
                .imageUrl(imageUrl)
                .isMain(false)
                .build();
        itemImageRepository.save(image);
    }

    public void deleteImage(Long itemId, Long imageId) {
        ItemImage image = itemImageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        if (!image.getItem().getId().equals(itemId)) {
            throw new RuntimeException("Image does not belong to this item");
        }

        minioService.delete(image.getImageUrl());
        itemImageRepository.delete(image);
    }
}