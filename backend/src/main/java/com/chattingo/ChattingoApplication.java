package com.chattingo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChattingoApplication {
    public static void main(String[] args) {
        // Only load .env when running locally
        if (System.getenv("DOCKER_ENV") == null) {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing()   // don’t crash if .env is missing
                    .load();
            dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
            );
        }

        SpringApplication.run(ChattingoApplication.class, args);
    }
}


