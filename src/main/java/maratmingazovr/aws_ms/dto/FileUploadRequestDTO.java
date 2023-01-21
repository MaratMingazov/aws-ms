package maratmingazovr.aws_ms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Value
@Validated
public class FileUploadRequestDTO {

    @NotNull
    String bucketName;

    @NotNull
    MultipartFile file;

    public FileUploadRequestDTO(@JsonProperty("bucketName") @NotNull String bucketName,
                                @JsonProperty("file") @NotNull MultipartFile file) {
        this.bucketName = bucketName;
        this.file = file;
    }
}
