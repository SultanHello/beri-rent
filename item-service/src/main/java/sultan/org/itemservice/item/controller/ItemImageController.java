package sultan.org.itemservice.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sultan.org.itemservice.item.service.ItemImageService;

@RestController
@RequestMapping("/items/{itemId}/images")
@RequiredArgsConstructor
public class ItemImageController {

    private final ItemImageService itemImageService;
    @PostMapping("/upload")
    public ResponseEntity<Void> uploadImage(
            @PathVariable Long itemId,
            @RequestParam MultipartFile file) {
        // 1. загружаем файл в MinIO
        String imageUrl = minioService.upload(file);
        // 2. сохраняем URL в БД
        itemImageService.addImage(itemId, imageUrl);
        return ResponseEntity.status(201).build();
    }


    @PostMapping("/main")
    public ResponseEntity<Void> setMainImage(
            @PathVariable Long itemId,
            @RequestParam String imageUrl) {
        itemImageService.setMainImage(itemId, imageUrl);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> addImage(
            @PathVariable Long itemId,
            @RequestParam String imageUrl) {
        itemImageService.addImage(itemId, imageUrl);
        return ResponseEntity.status(201).build();
    }


    @DeleteMapping("/{imageId}")
    public ResponseEntity<Void> deleteImage(

            @PathVariable Long imageId) {
        itemImageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }
}