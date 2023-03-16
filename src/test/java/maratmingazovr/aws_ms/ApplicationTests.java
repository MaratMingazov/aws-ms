package maratmingazovr.aws_ms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

	static {
		System.setProperty("DB_URL", "jdbc:postgresql://localhost:5432/aws_ms_db");
		System.setProperty("DB_USERNAME", "postgres");
		System.setProperty("DB_PASSWORD", "password");
	}

	@Test
	void contextLoads() {
	}

}
