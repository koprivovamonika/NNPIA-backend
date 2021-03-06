package com.example.nnpia_sem_backend;

import com.example.nnpia_sem_backend.dto.CreateReservationDtoIn;
import com.example.nnpia_sem_backend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class NnpiaSemBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NnpiaSemBackendApplication.class, args);


    }

}
