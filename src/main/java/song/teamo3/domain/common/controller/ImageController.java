package song.teamo3.domain.common.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import song.teamo3.domain.common.service.FileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final FileService fileService;
    private final static String DOWNLOAD_PATH = "http://localhost:8080/image/download/";

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> postUpload(@RequestParam("upload") MultipartFile upload) throws IOException {
        String uploadUrl = fileService.upload(upload);

        return ResponseEntity.ok().body(Map.of("url", DOWNLOAD_PATH + uploadUrl));
    }

    @PostMapping("/uploadMultiple")
    public ResponseEntity<List<Map<String, String>>> postUpload(@RequestParam("upload") MultipartFile[] uploads) throws IOException {
        List<Map<String, String>> results = new ArrayList<>();
        for (MultipartFile file : uploads) {
            String uploadUrl = fileService.upload(file);
            results.add(Map.of("url", DOWNLOAD_PATH + uploadUrl));
        }
        return ResponseEntity.ok().body(results);
    }

    @GetMapping("/download/{uploadName}")
    public ResponseEntity<Resource> getDownload(@PathVariable("uploadName") String uploadName) throws IOException {
        UrlResource resource = fileService.findByUploadName(uploadName);
        String contentType = fileService.getContentType(uploadName);

        log.info("[File Download]: {}", resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }
}
