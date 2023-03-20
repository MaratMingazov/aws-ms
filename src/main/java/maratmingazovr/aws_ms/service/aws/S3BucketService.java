package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import maratmingazovr.aws_ms.model.aws.AwsBucket;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3BucketService {

    private final S3Client s3Client;

    public void createBucket(@NonNull String bucketName) {
        try {
            s3Client.createBucket(builder -> builder.bucket(bucketName).build());
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to create a bucket='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }
    }

    public void deleteBucket(@NonNull String bucketName) {
        try {
            s3Client.deleteBucket(builder -> builder.bucket(bucketName).build());
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to delete a bucket='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }
    }

    @NonNull
    public  List<AwsBucket> getBuckets() {
        try {
            val buckets = s3Client.listBuckets().buckets();
            return buckets.stream()
                          .map(bucket -> new AwsBucket(bucket.name(), bucket.creationDate()))
                          .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AwsSdkException("S3ServiceImplException: Unable to get buckets.", ex);
        }
    }
}
