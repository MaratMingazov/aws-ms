package maratmingazovr.aws_ms.facade;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.service.S3EventService;
import maratmingazovr.aws_ms.service.aws.S3Service;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class S3Facade {

    private final S3Service s3Service;
    private final S3EventService s3EventService;

    @NonNull
    public String uploadFile(@NonNull String bucket,
                             @NonNull String filename,
                             @NonNull InputStream inputStream) {

        val url = s3Service.uploadFile(bucket, filename, inputStream);
        s3EventService.createS3Event(bucket, filename);
        return url;
    }
}
