package maratmingazovr.aws_ms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import maratmingazovr.aws_ms.model.aws.AwsObject;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Value
@Validated
public class AwsObjectResponseDTO {

    @NonNull
    @JsonProperty("fileName")
    String fileName;

    @NonNull
    @JsonProperty("lastModified")
    Instant lastModified;

    @NonNull
    @JsonProperty("ownerName")
    String ownerName;

    public AwsObjectResponseDTO(@NonNull AwsObject object) {
        this.fileName = object.getFileName();
        this.lastModified = object.getLastModified();
        this.ownerName = object.getOwnerName();
    }
}
