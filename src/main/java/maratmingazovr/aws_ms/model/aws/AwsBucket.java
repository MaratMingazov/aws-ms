package maratmingazovr.aws_ms.model.aws;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@AllArgsConstructor
public class AwsBucket {

    @NonNull
    String name;

    @NonNull
    Instant creationDate;
}
