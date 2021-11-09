package sg.edu.smu.cs203.pandanews.service.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sg.edu.smu.cs203.pandanews.model.storage.UploadFileResponse;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public UploadFileResponse uploadFile(MultipartFile multipartFile) {
        LocalDateTime now = LocalDateTime.now();
        String fileName = fileStorageService.storeFile(multipartFile);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/getimage/")
                .path(fileName)
                .toUriString();
        return new UploadFileResponse(fileName, fileDownloadUri, multipartFile.getContentType(),
                multipartFile.getSize());
    }

    @Override
    public byte[] getImageWithMediaType(String fileName) throws IOException {
        try {
            return this.fileStorageService.getImageWithMediaType(fileName);
        } catch (IOException ioException) {
            throw ioException;
        }
    }
}
