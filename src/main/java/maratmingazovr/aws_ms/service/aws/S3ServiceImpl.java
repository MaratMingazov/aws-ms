package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service{

    private final S3Client s3Client;

    @Override
    public void createBucket(@NonNull String bucketName) {
        CreateBucketRequest request = CreateBucketRequest
                .builder()
                .bucket(bucketName)
                .build();
        try {
            s3Client.createBucket(request);
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable create a bucket='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }
    }
}
