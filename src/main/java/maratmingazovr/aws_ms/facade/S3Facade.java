package maratmingazovr.aws_ms.facade;

import lombok.NonNull;

import java.io.InputStream;

public interface S3Facade {

    @NonNull
    String uploadFile(@NonNull String bucket,
                    @NonNull String filename,
                    @NonNull InputStream inputStream);
}
