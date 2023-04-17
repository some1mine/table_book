package site.jaeu95.table_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 작동을 위해 추가했습니다.
public class TableBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TableBookApplication.class, args);
	}

}
