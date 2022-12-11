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
@Import(S3ServiceImpl.class)
public class S3ServiceTest {

    @MockBean
    S3Client s3Client;

    @Autowired
    S3Service s3Service;

    @Test
    void getBucketFromUrlReturnCorrectBucketName() {
        assertEquals("bucket-name",S3Service.getBucketFromUrl("s3://bucket-name/path/to/file.txt"));
    }

    @Test
    void getPathFromUrlReturnCorrectBucketName() {
        assertEquals("path/to/file.txt",S3Service.getPathFromUrl("s3://bucket-name/path/to/file.txt"));
    }
}
