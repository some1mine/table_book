package site.jaeu95.table_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TableBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(TableBookApplication.class, args);
	}

}
