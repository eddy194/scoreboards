package com.williamhill.scoreboards;

import com.williamhill.scoreboards.model.Event;
import com.williamhill.scoreboards.repository.EventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ScoreboardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScoreboardsApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(EventRepository repo) {
		return args -> {
			var event = new Event();
			event.setId(1);
			event.setScoreTeamA(1);
			event.setScoreTeamB(2);
			event.setTeamA("Team A");
			event.setTeamB("Team B");
			event.setVersion(0);

			repo.save(event);
		};
	}

}
