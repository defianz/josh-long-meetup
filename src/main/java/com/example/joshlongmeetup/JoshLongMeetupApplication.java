package com.example.joshlongmeetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class JoshLongMeetupApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoshLongMeetupApplication.class, args);
    }

    @Bean
    RestClient restClient (RestClient.Builder builder){
        return builder.build();
    }
}



@Controller
@ResponseBody
class VirtualThreadsController {

    private final RestClient http;

    public VirtualThreadsController(RestClient http) {
        this.http = http;
    }

    @GetMapping("/wait")
     String block(){
        return this.http
                .get()
                .uri("https://httpbin.org/delay/5")
                .retrieve()
                .body(String.class);
    }
}