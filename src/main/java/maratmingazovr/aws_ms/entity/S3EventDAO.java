package maratmingazovr.aws_ms.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Data
@ToString
@NoArgsConstructor
@Entity(name = "s3_event")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class S3EventDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Column(name = "bucket")
    private String bucket;

    @NotNull
    @Column(name = "filename")
    private String filename;

    @CreationTimestamp
    @Column(name = "creation_date", columnDefinition = "TIMESTAMP(6)")
    private Instant creationDate;

    public S3EventDAO(@NonNull String bucket,
                      @NonNull String filename) {
        this.bucket = bucket;
        this.filename = filename;
    }
}
