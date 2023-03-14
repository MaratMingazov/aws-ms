package maratmingazovr.aws_ms.repository;

import maratmingazovr.aws_ms.entity.S3EventDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface S3EventRepository extends JpaRepository<S3EventDAO, Long> {
}
