package uz.pdp.awsspringconfig;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    @PostMapping
    public void upload(@RequestParam MultipartFile file) {
        fileService.uploadFile(file);
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        byte[] bytes = fileService.downloadFile(filename);
        ByteArrayResource resource = new ByteArrayResource(bytes);
        return ResponseEntity
                .ok()
                .contentLength(bytes.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment;filename=\"" + filename + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{filename}")
    public String delete(@PathVariable String filename){
        return fileService.delete(filename);
    }

}
