package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.dto.s3.AwsBucketResponseDTO;
import maratmingazovr.aws_ms.dto.s3.CreateBucketRequestDTO;
import maratmingazovr.aws_ms.service.aws.S3BucketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/aws/s3/bucket")
public class AwsS3BucketController {

    private final S3BucketService s3BucketService;

    @PostMapping
    public ResponseEntity<Void> createBucket(@RequestBody CreateBucketRequestDTO request) {
        s3BucketService.createBucket(request.getBucketName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBucket(@RequestParam String bucketName) {
        s3BucketService.deleteBucket(bucketName);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AwsBucketResponseDTO>> getBuckets() {
        val buckets = s3BucketService.getBuckets();
        val bucketsDto = buckets.stream()
                .map(AwsBucketResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bucketsDto);
    }
}
