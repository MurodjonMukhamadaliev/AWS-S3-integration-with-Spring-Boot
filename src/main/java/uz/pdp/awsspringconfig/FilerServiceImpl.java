package uz.pdp.awsspringconfig;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FilerServiceImpl implements FileService {
    private final AmazonS3 s3;
    @Value("${bucketName}")
    String bucketName;

    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile file) {
        String filename = file.getOriginalFilename();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        s3.putObject(bucketName, filename, file.getInputStream(), objectMetadata);
        return "file uploaded";
    }

    @SneakyThrows
    @Override
    public byte[] downloadFile(String filename) {
        S3Object object = s3.getObject(bucketName, filename);
        S3ObjectInputStream objectContent = object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectContent);
        return bytes;

    }

    @Override
    public String delete(String filename) {
        s3.deleteObject(bucketName, filename);
        return "deleted";
    }
}
