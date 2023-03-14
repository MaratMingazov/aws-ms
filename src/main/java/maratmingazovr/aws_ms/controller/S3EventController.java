package maratmingazovr.aws_ms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import maratmingazovr.aws_ms.dto.s3.S3EventResponseDTO;
import maratmingazovr.aws_ms.service.S3EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/api/s3events")
public class S3EventController {

    private final S3EventService s3EventService;

    @GetMapping
    public ResponseEntity<List<S3EventResponseDTO>> getS3Events() {
        val events = s3EventService.getS3Events();
        val eventsDto = events.stream()
                                .map(S3EventResponseDTO::new)
                                .collect(Collectors.toList());
        return ResponseEntity.ok(eventsDto);
    }
}
