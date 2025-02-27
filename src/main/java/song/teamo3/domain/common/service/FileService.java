package song.teamo3.domain.common.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    @Value("${upload.path}")
    private String uploadPath;

    public String upload(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename().replace(" ", "");
        String uploadFileName = createUploadFileName(originalFileName);
        multipartFile.transferTo(new File(getFileFullPath(uploadFileName)));

        log.info("[File Uploaded] {}", uploadFileName);
        return uploadFileName;
    }

    public UrlResource findByUploadName(String uploadName) throws MalformedURLException {
        return new UrlResource("file:" + getFileFullPath(uploadName));
    }

    public String getContentType(String uploadFileName) {
        if (uploadFileName.endsWith(".jpg") || uploadFileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (uploadFileName.endsWith(".png")) {
            return "image/png";
        }
        return "application/octet-stream";
    }

    private String createUploadFileName(String originalFilename) {
        String ext = getExt(originalFilename);
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return originalFilename.substring(0, originalFilename.lastIndexOf(".")) + uuid + "." + ext;
    }

    private String getExt(String originalFilename) {
        int p = originalFilename.lastIndexOf(".");
        return originalFilename.substring(p + 1);
    }

    private String getFileFullPath(String uploadFileName) {
        return uploadPath + uploadFileName;
    }
}
