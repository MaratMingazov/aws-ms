package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.dto.AwsBucketDto;
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
    public ResponseEntity<List<AwsBucketDto>> getBuckets() {
        val buckets = s3Service.getBuckets();
        val bucketsDto = buckets.stream()
                .map(AwsBucketDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bucketsDto);
    }

    @PostMapping("/file/upload")
    public ResponseEntity<Void> uploadImage(@ModelAttribute FileUploadRequestDTO fileUploadRequestDTO) {
        val file = fileUploadRequestDTO.getFile();
        val inputStream = fileService.getInputStream(file);
        s3Service.putObject(file.getName(), inputStream);
        return ResponseEntity.ok().build();
    }
}
