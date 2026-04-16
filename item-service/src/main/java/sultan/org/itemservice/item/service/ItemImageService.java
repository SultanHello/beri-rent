package sultan.org.itemservice.item.service;

public interface ItemImageService {
    public void setMainImage(Long itemId, String imageUrl);
    public void addImage(Long itemId, String imageUrl);
    public void deleteImage(Long imageId);

}
