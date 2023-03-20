package maratmingazovr.aws_ms.service.aws;

import com.google.common.io.ByteStreams;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import maratmingazovr.aws_ms.model.aws.AwsObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.async.SdkPublisher;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompressionType;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ExpressionType;
import software.amazon.awssdk.services.s3.model.InputSerialization;
import software.amazon.awssdk.services.s3.model.JSONInput;
import software.amazon.awssdk.services.s3.model.JSONOutput;
import software.amazon.awssdk.services.s3.model.JSONType;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.OutputSerialization;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.RecordsEvent;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.model.SelectObjectContentEventStream;
import software.amazon.awssdk.services.s3.model.SelectObjectContentRequest;
import software.amazon.awssdk.services.s3.model.SelectObjectContentResponse;
import software.amazon.awssdk.services.s3.model.SelectObjectContentResponseHandler;
import software.amazon.awssdk.services.s3.model.Tag;
import software.amazon.awssdk.services.s3.model.Tagging;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3FileService {

    private final S3Client s3Client;
    private final S3AsyncClient s3AsyncClient;


    public List<AwsObject> getBucketFiles(@NonNull String bucketName) {
        try {
            val request = ListObjectsRequest.builder().bucket(bucketName).build();
            val response = s3Client.listObjects(request);
            List<S3Object> content = response.contents();
            return content.stream()
                          .map(AwsObject::new)
                          .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceException: Unable to get bucketFiles for bucketName='%s'. '%s'", bucketName, ex.getMessage()), ex);
        }
    }

    public void readFile(@NonNull String bucketName,
                         @NonNull String fileName) {
        val query = getQuery();
        val request = generateJsonRequest(bucketName, fileName, query);
        queryS3(request);
    }

    @NonNull
    public String uploadFile(@NonNull String bucketName,
                             @NonNull String fileName,
                             @NonNull InputStream inputStream) {
        val url = createURLFromBucketNameAndFileName(bucketName, fileName);
        uploadFile(url, inputStream);
        return url;
    }

    /**
     *
     * @param url format s3://bucket-name/path/file.txt
     * @param inputStream
     */
    private void uploadFile(@NonNull String url,
                            @NonNull InputStream inputStream) {
        try {
            val requestBody = RequestBody.fromBytes(ByteStreams.toByteArray(inputStream));
            uploadFile(url, requestBody, Map.of());
        } catch (IOException ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Could not convert inputStream to byteArray for url='%s'. '%s'", url, ex.getMessage()), ex);
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceImplException: Unable to upload file for url='%s'. '%s'", url, ex.getMessage()), ex);
        }
    }

    /**
     *
     * @param url format s3://bucket-name/path/file.txt
     * @param requestBody
     * @param tagsMap
     */
    private void uploadFile(@NonNull String url,
                            @NonNull RequestBody requestBody,
                            @NonNull Map<String, String> tagsMap) {
        val builder = PutObjectRequest.builder();
        addTagsIfNeeded(builder, tagsMap);
        val request = builder
                .bucket(getBucketFromUrl(url))
                .key(getPathFromUrl(url))
                .build();
        s3Client.putObject(request, requestBody);
    }

    @NonNull
    public String deleteFile(@NonNull String bucketName,
                           @NonNull String fileName) {
        val url = createURLFromBucketNameAndFileName(bucketName, fileName);
        deleteFile(url);
        return url;
    }

    /**
     * Delete file from bucket
     * @param url format s3://bucket-name/path/file.txt
     */
    private void deleteFile(@NonNull String url) {
        val deleteObjectRequest = DeleteObjectRequest
                .builder()
                .bucket(getBucketFromUrl(url))
                .key(getPathFromUrl(url))
                .build();
        try {
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception ex) {
            throw new AwsSdkException(String.format("S3ServiceException: Unable to delete file='%s'. '%s'", url, ex.getMessage()), ex);
        }
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
    public String getBucketFromUrl(@NonNull String s3Url) {
        return convertS3UrlStringToUrl(s3Url).getHost();
    }

    @NonNull
    public String getPathFromUrl(@NonNull String s3Url) {
        return convertS3UrlStringToUrl(s3Url).getPath().substring(1);
    }

    /**
     *
     * @param s3Url format s3://bucket-name/path/file.txt
     * @return
     */
    @NonNull
    private URI convertS3UrlStringToUrl(@NonNull String s3Url) {
        if (StringUtils.isEmpty(s3Url) || !s3Url.toLowerCase().startsWith("s3://")) {
            throw new IllegalArgumentException(String.format("S3ServiceException: s3Url invalid ('%s')", s3Url));
        }
        try {
            return new URI(s3Url);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(String.format("S3ServiceException: s3Url invalid ('%s')", s3Url), ex);
        }
    }

    /**
     *
     * @param bucketName given bucketName
     * @param fileName given fileName
     * @return url format s3://bucket-name/path/file.txt
     */
    @NonNull
    private String createURLFromBucketNameAndFileName(@NonNull String bucketName,
                                                     @NonNull String fileName) {
        return "s3://" + bucketName + "/" + fileName;
    }

    @NonNull
    private String getQuery() {
        return "select * from S3Object s";
    }

    @NonNull
    private SelectObjectContentRequest generateJsonRequest(@NonNull String bucketName,
                                                           @NonNull String fileName,
                                                           @NonNull String query) {

        val inputSerialization = InputSerialization
                .builder()
                .json(JSONInput.builder().type(JSONType.DOCUMENT).build())
                .compressionType(CompressionType.NONE)
                .build();

        val outputSerialization = OutputSerialization
                .builder()
                .json(JSONOutput.builder().build())
                .build();

        return SelectObjectContentRequest
                .builder()
                .bucket(bucketName)
                .key(fileName)
                .expression(query)
                .expressionType(ExpressionType.SQL)
                .inputSerialization(inputSerialization)
                .outputSerialization(outputSerialization)
                .build();
    }


    private void queryS3(@NonNull SelectObjectContentRequest request) {

        val handler = new ResponseHandler();

        try {
            s3AsyncClient.selectObjectContent(request, handler).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        RecordsEvent response = (RecordsEvent) handler.receivedEvents
                .stream()
                .filter(e -> e.sdkEventType() == SelectObjectContentEventStream.EventType.RECORDS)
                .findFirst()
                .orElse(null);
        System.out.println(response.payload().asUtf8String());
    }

    private static class ResponseHandler implements SelectObjectContentResponseHandler {
        private SelectObjectContentResponse response;
        private List<SelectObjectContentEventStream> receivedEvents = new ArrayList<>();
        private Throwable exception;

        @Override
        public void responseReceived(SelectObjectContentResponse response) {
            log.info("responseReceived");
            this.response = response;
        }

        @Override
        public void onEventStream(SdkPublisher<SelectObjectContentEventStream> publisher) {
            log.info("onEventStream");
            publisher.subscribe(receivedEvents::add);
        }

        @Override
        public void exceptionOccurred(Throwable throwable) {
            log.info("exceptionOccurred");
            exception = throwable;
        }

        @Override
        public void complete() {
            log.info("complete");
        }
    }
}
