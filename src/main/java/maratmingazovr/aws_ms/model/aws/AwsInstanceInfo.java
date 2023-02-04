package maratmingazovr.aws_ms.model.aws;

import lombok.NonNull;
import lombok.Value;

@Value
public class AwsInstanceInfo {

    @NonNull
    String availabilityZone;

    public AwsInstanceInfo(@NonNull String availabilityZone) {
        this.availabilityZone = availabilityZone;

    }
}
