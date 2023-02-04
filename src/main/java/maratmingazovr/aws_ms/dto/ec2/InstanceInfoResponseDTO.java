package maratmingazovr.aws_ms.dto.ec2;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import lombok.Value;
import maratmingazovr.aws_ms.model.aws.AwsInstanceInfo;
import org.springframework.validation.annotation.Validated;

@Value
@Validated
public class InstanceInfoResponseDTO {

    @NonNull
    @JsonProperty("availabilityZone")
    String availabilityZone;

    public InstanceInfoResponseDTO(@NonNull AwsInstanceInfo instanceInfo) {
        this.availabilityZone = instanceInfo.getAvailabilityZone();
    }

}
