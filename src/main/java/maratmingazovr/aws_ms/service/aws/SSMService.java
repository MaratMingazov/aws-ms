package maratmingazovr.aws_ms.service.aws;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ssm.SsmClient;
import software.amazon.awssdk.services.ssm.model.GetParameterRequest;

@Log4j2
@Service
@RequiredArgsConstructor
public class SSMService {

    private final SsmClient ssmClient;

    private final String BUCKET_NAME = "bucketName";

    @NonNull
    public String getParamValue(@NonNull String paramName) {
        try {
            val parameterRequest = GetParameterRequest.builder()
                                                      .name(paramName)
                                                      .build();

            val parameterResponse = ssmClient.getParameter(parameterRequest);
            return parameterResponse.parameter().value();

        } catch (Exception e) {
            log.error("SSMServiceException: " + e.getMessage());
            throw e;
        }
    }

    public String getBucketName() {
        return getParamValue(BUCKET_NAME);
    }


}
