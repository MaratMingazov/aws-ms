package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;

import java.net.URI;
import java.net.URISyntaxException;

public interface S3Service {

    static URI convertS3UrlStringToUrl(@NonNull String s3Url) {
        try {
            return new URI(s3Url);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(String.format("S3ServiceException: s3Url invalid ('%s')", s3Url), ex);
        }
    }
}
