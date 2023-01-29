package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.dto.AwsBucketResponseDTO;
import maratmingazovr.aws_ms.dto.AwsObjectResponseDTO;
import maratmingazovr.aws_ms.dto.CreateBucketRequestDTO;
import maratmingazovr.aws_ms.dto.FileUploadRequestDTO;
import maratmingazovr.aws_ms.service.aws.FileService;
import maratmingazovr.aws_ms.service.aws.S3Service;
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
@RequestMapping("/aws")
public class AwsController {

    private final S3Service s3Service;

    private final FileService fileService;

    @PostMapping("/buckets")
    public ResponseEntity<Void> createBucket(@RequestBody CreateBucketRequestDTO request) {
        s3Service.createBucket(request.getBucketName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/buckets")
    public ResponseEntity<Void> deleteBucket(@RequestParam String bucketName) {
        s3Service.deleteBucket(bucketName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buckets")
    public ResponseEntity<List<AwsBucketResponseDTO>> getBuckets() {
        val buckets = s3Service.getBuckets();
        val bucketsDto = buckets.stream()
                .map(AwsBucketResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bucketsDto);
    }

    @PostMapping("/files/upload")
    public ResponseEntity<Void> uploadFile(@ModelAttribute FileUploadRequestDTO fileUploadRequestDTO) {
        val file = fileUploadRequestDTO.getFile();
        val inputStream = fileService.getInputStream(file);
        val url = "s3://" + fileUploadRequestDTO.getBucketName() + "/" + file.getOriginalFilename();
        s3Service.putObject(url, inputStream);
        log.info("AwsController: successfully have uploaded file = " + url);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/files")
    public ResponseEntity<List<AwsObjectResponseDTO>> getFiles(@RequestParam String bucketName) {
        val awsObjects = s3Service.getBucketFiles(bucketName);
        val response = awsObjects.stream()
                                 .map(AwsObjectResponseDTO::new)
                                 .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
