package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.dto.ec2.InstanceInfoResponseDTO;
import maratmingazovr.aws_ms.service.aws.EC2Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/aws/ec2")
public class AwsEC2Controller {

    private final EC2Service ec2Service;

    @GetMapping
    public ResponseEntity<InstanceInfoResponseDTO> getInstanceInfo() {
        val instanceInfo = ec2Service.getInstanceInfo();
        val response = new InstanceInfoResponseDTO(instanceInfo);

        log.info("AwsEC2Controller: successfully returned instanceInfo = " + response);
        return ResponseEntity.ok(response);
    }
}
