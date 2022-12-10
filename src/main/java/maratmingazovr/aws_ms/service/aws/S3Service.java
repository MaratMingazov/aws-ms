package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;

public interface S3Service {

    void createBucket(@NonNull String bucketName);

    @NonNull
    static String getBucketFromUrl(@NonNull String s3Url) {
        return convertS3UrlStringToUrl(s3Url).getHost();
    }

    @NonNull
    static String getPathFromUrl(@NonNull String s3Url) {
        return convertS3UrlStringToUrl(s3Url).getPath();
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
