package maratmingazovr.aws_ms.service.aws;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.services.s3.S3Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@Import(S3FileService.class)
public class S3FileServiceTest {

    @MockBean
    S3Client s3Client;

    @Autowired
    S3FileService s3FileService;

    @Test
    void getBucketFromUrlReturnCorrectBucketName() {
        assertEquals("bucket-name", s3FileService.getBucketFromUrl("s3://bucket-name/path/to/file.txt"));
    }

    @Test
    void getPathFromUrlReturnCorrectBucketName() {
        assertEquals("path/to/file.txt", s3FileService.getPathFromUrl("s3://bucket-name/path/to/file.txt"));
    }
}
