package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.model.aws.AwsInstanceInfo;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.internal.util.EC2MetadataUtils;

@Log4j2
@Service
@RequiredArgsConstructor
public class EC2ServiceImpl implements EC2Service {

    @NonNull
    @Override
    public AwsInstanceInfo getInstanceInfo() {
        return new AwsInstanceInfo(EC2MetadataUtils.getAvailabilityZone());
    }
}
