package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{

    private final S3Client s3Client;

    @Override
    public void createBucket(@NonNull String bucketName) {
        try {
            s3Client.createBucket(builder -> builder.bucket(bucketName).build());
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to create a bucket='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }
    }

    @Override
    public void deleteBucket(@NonNull String bucketName) {
        try {
            s3Client.deleteBucket(builder -> builder.bucket(bucketName).build());
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to delete a bucket='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }
    }
}
