package maratmingazovr.aws_ms.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.exception.AwsSdkException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class GExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public GExceptionBody handleServerException(AwsSdkException exception) {
        log.error(exception.getMessage());
        return new GExceptionBody(exception.getMessage(), GExceptionCode.ILLEGAL_ARGUMENT, HttpStatus.BAD_REQUEST);
    }



}
