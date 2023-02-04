package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import maratmingazovr.aws_ms.model.aws.AwsInstanceInfo;

public interface EC2Service {

    @NonNull
    AwsInstanceInfo getInstanceInfo();
}
