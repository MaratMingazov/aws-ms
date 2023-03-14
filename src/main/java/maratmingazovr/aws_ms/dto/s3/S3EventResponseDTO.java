package maratmingazovr.aws_ms.dto.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import maratmingazovr.aws_ms.model.S3Event;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Value
@Validated
public class S3EventResponseDTO {

    @NonNull
    @JsonProperty("events")
    List<S3Event> events;

    public S3EventResponseDTO (@NonNull List<S3Event> events) {
        this.events = events;
    }
}
