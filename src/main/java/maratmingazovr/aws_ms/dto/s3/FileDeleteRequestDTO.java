package maratmingazovr.aws_ms.dto.s3;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Value
@Validated
public class FileDeleteRequestDTO {

    @NotNull
    String bucketName;

    @NotNull
    String fileName;

    public FileDeleteRequestDTO(@JsonProperty("bucketName") @NotNull String bucketName,
                                @JsonProperty("fileName") @NotNull String fileName) {
        this.bucketName = bucketName;
        this.fileName = fileName;
    }
}
