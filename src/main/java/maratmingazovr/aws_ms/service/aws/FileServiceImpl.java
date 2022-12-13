package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

    @NonNull
    @Override
    public InputStream getInputStream(@NonNull MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            return multipartFile.getInputStream();
        } catch (IOException ex) {
            throw new IllegalArgumentException(String.format("FileServiceImplException: Unable to get inputStream from multipartFile, ex='%s'", ex.getMessage()),ex);
        }
    }

}
