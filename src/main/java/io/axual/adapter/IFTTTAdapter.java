package io.axual.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IFTTTAdapter {

    public static void main(String[] args) {
        SpringApplication.run(IFTTTAdapter.class, args);
    }
}
