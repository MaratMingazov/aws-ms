package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import maratmingazovr.aws_ms.model.aws.AwsBucket;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public interface S3Service {

    void createBucket(@NonNull String bucketName);

    void deleteBucket(@NonNull String bucketName);

    /**
     *
     * @param url format s3://bucket-name/path/file.txt
     * @param inputStream
     */
    void putObject(@NonNull String url, @NonNull InputStream inputStream);

    @NonNull
    List<AwsBucket> getBuckets();

    @NonNull
    static String getBucketFromUrl(@NonNull String s3Url) {
        return convertS3UrlStringToUrl(s3Url).getHost();
    }

    @NonNull
    static String getPathFromUrl(@NonNull String s3Url) {
        return convertS3UrlStringToUrl(s3Url).getPath().substring(1);
    }

    @NonNull
    static URI convertS3UrlStringToUrl(@NonNull String s3Url) {
        if (StringUtils.isEmpty(s3Url) || !s3Url.toLowerCase().startsWith("s3://")) {
            throw new IllegalArgumentException(String.format("S3ServiceException: s3Url invalid ('%s')", s3Url));
        }
        try {
            return new URI(s3Url);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(String.format("S3ServiceException: s3Url invalid ('%s')", s3Url), ex);
        }
    }
}
