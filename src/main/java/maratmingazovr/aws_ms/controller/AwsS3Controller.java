package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.dto.s3.AwsBucketResponseDTO;
import maratmingazovr.aws_ms.dto.s3.AwsObjectResponseDTO;
import maratmingazovr.aws_ms.dto.s3.CreateBucketRequestDTO;
import maratmingazovr.aws_ms.dto.s3.FileDeleteRequestDTO;
import maratmingazovr.aws_ms.dto.s3.FileUploadRequestDTO;
import maratmingazovr.aws_ms.facade.S3Facade;
import maratmingazovr.aws_ms.service.aws.FileService;
import maratmingazovr.aws_ms.service.aws.S3FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/aws/s3")
public class AwsS3Controller {

    private final S3FileService s3FileService;

    private final S3Facade s3Facade;

    private final FileService fileService;

    @PostMapping
    public ResponseEntity<Void> createBucket(@RequestBody CreateBucketRequestDTO request) {
        s3FileService.createBucket(request.getBucketName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBucket(@RequestParam String bucketName) {
        s3FileService.deleteBucket(bucketName);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AwsBucketResponseDTO>> getBuckets() {
        val buckets = s3FileService.getBuckets();
        val bucketsDto = buckets.stream()
                .map(AwsBucketResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bucketsDto);
    }

    @PostMapping("/files")
    public ResponseEntity<Void> uploadFile(@ModelAttribute FileUploadRequestDTO fileUploadRequestDTO) {
        val file = fileUploadRequestDTO.getFile();
        val inputStream = fileService.getInputStream(file);
        val bucket = fileUploadRequestDTO.getBucketName();
        val filename = file.getOriginalFilename();
        val url = s3Facade.uploadFile(bucket, filename, inputStream);

        log.info("AwsS3Controller: successfully have uploaded file = " + url);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/files")
    public ResponseEntity<List<AwsObjectResponseDTO>> getFiles(@RequestParam String bucketName) {
        val awsObjects = s3FileService.getBucketFiles(bucketName);
        val response = awsObjects.stream()
                                 .map(AwsObjectResponseDTO::new)
                                 .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/files")
    public ResponseEntity<Void> deleteFile(@RequestBody FileDeleteRequestDTO request) {
        val url = s3FileService.deleteFile(request.getBucketName(), request.getFileName());
        log.info("AwsS3Controller: successfully have deleted file = " + url);
        return ResponseEntity.ok().build();
    }
}
