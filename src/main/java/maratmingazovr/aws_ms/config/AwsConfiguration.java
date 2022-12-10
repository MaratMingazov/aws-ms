package maratmingazovr.aws_ms.config;

import lombok.val;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
@EnableConfigurationProperties(AwsConfig.class)
public class AwsConfiguration {

    @Bean
    public S3Client s3Client(AwsConfig config) {
        val credentials = AwsBasicCredentials.create(config.accessKeyId, config.secretAccessKey);
        return S3Client.builder()
                .region(config.getRegion())
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}
