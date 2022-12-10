package maratmingazovr.aws_ms.exception;

public class AwsSdkException extends RuntimeException {

    public AwsSdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public AwsSdkException(String message) {
        super(message);
    }
}
