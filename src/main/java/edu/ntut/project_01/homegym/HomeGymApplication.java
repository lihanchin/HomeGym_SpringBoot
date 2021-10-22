package edu.ntut.project_01.homegym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomeGymApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(HomeGymApplication.class, args);
    }

}
