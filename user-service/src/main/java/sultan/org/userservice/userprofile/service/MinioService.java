package sultan.org.userservice.userprofile.service;

import org.springframework.web.multipart.MultipartFile;

public interface MinioService {
    public String upload(MultipartFile file);
    public void delete(String imageUrl);
}
