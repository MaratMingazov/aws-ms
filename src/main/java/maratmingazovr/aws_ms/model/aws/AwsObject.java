package maratmingazovr.aws_ms.model.aws;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.Value;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.time.Instant;

@Value
@AllArgsConstructor
public class AwsObject {

    @NonNull
    String fileName;

    @NonNull
    Instant lastModified;

    @NonNull
    String ownerName;

    public AwsObject(@NonNull S3Object object) {
        this.fileName = object.key();
        this.lastModified = object.lastModified();
        this.ownerName = object.owner().displayName();
    }


}
