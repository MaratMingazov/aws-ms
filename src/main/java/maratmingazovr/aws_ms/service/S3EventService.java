package maratmingazovr.aws_ms.service;

import lombok.NonNull;
import maratmingazovr.aws_ms.model.S3Event;

import java.util.List;

public interface S3EventService {

    @NonNull
    S3Event createS3Event(@NonNull String bucket,
                          @NonNull String filename);

    @NonNull
    List<S3Event> getS3Events();
}
