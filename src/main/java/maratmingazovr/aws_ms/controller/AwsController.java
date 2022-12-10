package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.dto.CreateBucketRequestDTO;
import maratmingazovr.aws_ms.service.aws.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/aws")
public class AwsController {

    private final S3Service s3Service;

    @PostMapping("create_bucket")
    public ResponseEntity<Void> createBucket(@RequestBody CreateBucketRequestDTO request) {
        s3Service.createBucket(request.getBucketName());
        return ResponseEntity.ok().build();
    }
}
