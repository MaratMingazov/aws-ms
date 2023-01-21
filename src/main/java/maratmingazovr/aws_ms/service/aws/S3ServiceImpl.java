package maratmingazovr.aws_ms.service.aws;

import com.google.common.io.ByteStreams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import maratmingazovr.aws_ms.model.aws.AwsBucket;
import maratmingazovr.aws_ms.model.aws.AwsObject;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListBucketsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public void putObject(@NonNull String url, @NonNull InputStream inputStream) {
        try {
            val requestBody = RequestBody.fromBytes(ByteStreams.toByteArray(inputStream));
            putObject(url, requestBody, Map.of());
        } catch (IOException ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Could not convert inputStream to byteArray for url='%s'. '%s'", url, ex.getMessage()), ex);
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to upload file for url='%s'. '%s'", url, ex.getMessage()), ex);
        }

    }

    public List<AwsObject> getBucketFiles(@NonNull String bucketName) {
        try {
            val request = ListObjectsRequest.builder().bucket(bucketName).build();
            val response = s3Client.listObjects(request);
            List<S3Object> content = response.contents();
            return content.stream()
                          .map(AwsObject::new)
                          .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to get bucketFiles for bucketName='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }

    }

    private void putObject(@NonNull String url,
                           @NonNull RequestBody requestBody,
                           @NonNull Map<String, String> tagsMap) {
        val builder = PutObjectRequest.builder();
        addTagsIfNeeded(builder, tagsMap);
        val request = builder
                .bucket(S3Service.getBucketFromUrl(url))
                .key(S3Service.getPathFromUrl(url))
                .build();
        s3Client.putObject(request, requestBody);

    }

    private void addTagsIfNeeded(@NonNull PutObjectRequest.Builder builder,
                                 @NonNull Map<String, String> tagsMap) {

        val tagging = Tagging.builder()
                .tagSet(tagsMap.entrySet()
                                .stream()
                                .map(entry -> Tag.builder()
                                        .key(entry.getKey())
                                        .value(entry.getValue())
                                        .build())
                               .collect(Collectors.toList())
                       )
                .build();
        builder.tagging(tagging);
    }

    @NonNull
    @Override
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
