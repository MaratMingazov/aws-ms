package maratmingazovr.aws_ms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import maratmingazovr.aws_ms.model.aws.AwsBucket;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Value
@Validated
public class AwsBucketResponseDTO {

    @NonNull
    @JsonProperty("name")
    String name;

    @NonNull
    @JsonProperty("creationDate")
    Instant creationDate;

    public AwsBucketResponseDTO(@NonNull AwsBucket bucket) {
        this.name = bucket.getName();
        this.creationDate = bucket.getCreationDate();
    }
}
