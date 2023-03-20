package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import maratmingazovr.aws_ms.model.aws.AwsInstanceInfo;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;

@Log4j2
@Service
@RequiredArgsConstructor
public class EC2Service {

    @NonNull
    public AwsInstanceInfo getInstanceInfo() {
        try {
            return new AwsInstanceInfo(EC2MetadataUtils.getAvailabilityZone());
        } catch (Exception e) {
            throw new AwsSdkException(String.format("EC2ServiceException: Unable get instanceInfo. '%s'", e.getMessage()), e);
        }

    }
}
