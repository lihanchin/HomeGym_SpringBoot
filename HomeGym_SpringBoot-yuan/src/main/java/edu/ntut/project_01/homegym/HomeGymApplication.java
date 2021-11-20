package edu.ntut.project_01.homegym;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomeGymApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(HomeGymApplication.class, args);

    }
//  如果要在執行後馬上實作一些東西
//    @Bean
//    public ApplicationRunner run () {
//        return args -> {
//          repository.save(v);
//        };
//    }

}
