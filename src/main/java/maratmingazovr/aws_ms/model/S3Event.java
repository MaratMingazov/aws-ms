package maratmingazovr.aws_ms.model;

import lombok.NonNull;
import lombok.Value;
import maratmingazovr.aws_ms.entity.S3EventDAO;

import java.time.Instant;

@Value
public class S3Event {

    @NonNull
    String bucket;
    @NonNull
    String filename;
    @NonNull Instant creationDate;

    public S3Event(@NonNull S3EventDAO entity) {
        this.bucket = entity.getBucket();
        this.filename = entity.getFilename();
        this.creationDate = entity.getCreationDate();
    }
}
