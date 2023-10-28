package com.MoodMusicApplicaiton.java;

//import com.service.TokenRequests;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        System.out.println("Hello World!");
//        TokenRequests tokenRequests = new TokenRequests();
//        tokenRequests.getTokenID();
        SpringApplication.run(Application.class, args);
    }
}
