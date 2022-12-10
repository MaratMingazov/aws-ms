package maratmingazovr.aws_ms.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import software.amazon.awssdk.regions.Region;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
@NoArgsConstructor
@ConfigurationProperties(prefix="aws")
public class AwsConfig {

    @NotEmpty
    String accessKeyId;

    @NotEmpty
    String secretAccessKey;

    @NotNull
    Region region;

    @NotEmpty
    String accountId;
}
