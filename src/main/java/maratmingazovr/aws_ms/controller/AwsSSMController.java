package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.service.aws.SSMService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/aws/ssm")
public class AwsSSMController {

    private final SSMService ssmService;

    @GetMapping("/bucketname")
    public ResponseEntity<String> getBucketName() {
        val bucketName = ssmService.getBucketName();

        return ResponseEntity.ok(bucketName);
    }
}
