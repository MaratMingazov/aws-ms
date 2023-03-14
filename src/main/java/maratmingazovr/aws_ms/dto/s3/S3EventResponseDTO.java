package maratmingazovr.aws_ms.dto.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import maratmingazovr.aws_ms.model.S3Event;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Value
@Validated
public class S3EventResponseDTO {

    @NonNull
    @JsonProperty("bucket")
    String bucket;

    @NonNull
    @JsonProperty("filename")
    String filename;

    @NonNull
    @JsonProperty("creationDate")
    Instant creationDate;

    public S3EventResponseDTO(@NonNull S3Event s3Event) {
        this.bucket = s3Event.getBucket();
        this.filename = s3Event.getFilename();
        this.creationDate = s3Event.getCreationDate();
    }
}
