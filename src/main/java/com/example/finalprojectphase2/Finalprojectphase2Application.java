package com.example.finalprojectphase2;

import com.example.finalprojectphase2.model.ServiceType;
import com.example.finalprojectphase2.model.ServiceItem;
import com.example.finalprojectphase2.model.User;
import com.example.finalprojectphase2.service.ServiceTypeService;
import com.example.finalprojectphase2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Finalprojectphase2Application {

    public static void main(String[] args) {
        SpringApplication.run(Finalprojectphase2Application.class, args);
    }

}