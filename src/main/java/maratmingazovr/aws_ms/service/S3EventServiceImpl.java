package maratmingazovr.aws_ms.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import lombok.extern.log4j.Log4j2;
import maratmingazovr.aws_ms.entity.S3EventDAO;
import maratmingazovr.aws_ms.model.S3Event;
import maratmingazovr.aws_ms.repository.S3EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3EventServiceImpl implements S3EventService{

    private final S3EventRepository s3EventRepository;

    @NonNull
    @Override
    public S3Event createS3Event(@NonNull String bucket,
                                          @NonNull String filename) {

        val s3EventDAO = new S3EventDAO(bucket, filename);
        val s3EventDAOsaved = s3EventRepository.save(s3EventDAO);
        val s3Event = new S3Event(s3EventDAOsaved);

        log.info("S3EventService: successfully saved s3Event = " + s3Event);
        return s3Event;
    }

    @NonNull
    @Override
    public  List<S3Event> getS3Events() {
        val events = s3EventRepository.findAll().stream()
                                      .map(S3Event::new)
                                      .collect(Collectors.toList());

        log.info("S3EventService: successfully returned s3Events, size = " + events.size());
        return events;
    }
}
