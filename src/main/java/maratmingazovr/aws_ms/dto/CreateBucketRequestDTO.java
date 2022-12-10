package maratmingazovr.aws_ms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
@Validated
public class CreateBucketRequestDTO {

    @NotNull(message = "CreateBucketRequestDTO: bucketName should not be null.")
    @NotBlank(message = "CreateBucketRequestDTO: bucketName should not be blank.")
    String bucketName;

    public CreateBucketRequestDTO(@JsonProperty("bucketName") @NotEmpty String bucketName) {
        this.bucketName = bucketName;
    }
}
