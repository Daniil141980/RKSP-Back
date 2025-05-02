package edu.tinkoff.tinkoffbackendacademypetproject;

import edu.tinkoff.tinkoffbackendacademypetproject.config.StorageProperties;
import edu.tinkoff.tinkoffbackendacademypetproject.scheduler.SchedulerProperties;
import edu.tinkoff.tinkoffbackendacademypetproject.services.StorageService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class, SchedulerProperties.class})
public class TinkoffBackendAcademyPetProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TinkoffBackendAcademyPetProjectApplication.class, args);
    }

    @Bean
    CommandLineRunner init(@Qualifier("fileService") StorageService fileService) {
        return (args) -> {
            fileService.init();
        };
    }

}
