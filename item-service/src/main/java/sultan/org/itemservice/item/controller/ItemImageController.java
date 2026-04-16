package sultan.org.itemservice.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sultan.org.itemservice.item.service.ItemImageService;
import sultan.org.itemservice.item.service.impl.MinioService;

@RestController
@RequestMapping("/items/{itemId}/images")
@RequiredArgsConstructor
public class ItemImageController {

    private final ItemImageService itemImageService;
    private final MinioService minioService;

    @PostMapping
    public ResponseEntity<Void> addImage(
            @PathVariable Long itemId,
            @RequestParam MultipartFile file) {
        String imageUrl = minioService.upload(file);
        itemImageService.addImage(itemId, imageUrl);
        return ResponseEntity.status(201).build();
    }

    @PostMapping("/main")
    public ResponseEntity<Void> setMainImage(
            @PathVariable Long itemId,
            @RequestParam MultipartFile file) {
        String imageUrl = minioService.upload(file);
        itemImageService.setMainImage(itemId, imageUrl);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(
            @PathVariable Long itemId,
            @PathVariable Long imageId) {
        itemImageService.deleteImage(itemId, imageId);
        return ResponseEntity.noContent().build();
    }
}