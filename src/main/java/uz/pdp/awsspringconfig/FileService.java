package uz.pdp.awsspringconfig;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile file);
    byte[] downloadFile(String filename);
    String delete(String filename);
}
