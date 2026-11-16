package com.codequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CodeQuest Application Entry Point.
 *
 * This Spring Boot application initializes the web server, Spring Data JPA repositories,
 * Spring Security filter chain, and executes the CommandLineRunner seeder.
 */
@SpringBootApplication
public class CodeQuestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeQuestApplication.class, args);
    }
}
