package maratmingazovr.aws_ms.exception.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.NonNull;
import lombok.Value;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GExceptionBody implements Serializable {

    @NonNull
    LocalDateTime timestamp;

    @NonNull
    @Size(max = 255)
    String title;

    @NonNull
    GExceptionCode code;

    @Min(100)
    @Max(999)
    int statusCode;

    @NonNull
    HttpStatus status;


    public GExceptionBody(@NonNull String title,
                          @NonNull GExceptionCode code,
                          @NonNull HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.title = title;
        this.code = code;
        this.status = status;
        this.statusCode = status.value();
    }
}