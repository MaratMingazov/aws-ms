package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface FileService {

    @NonNull
    InputStream getInputStream(@NonNull MultipartFile multipartFile);
}
