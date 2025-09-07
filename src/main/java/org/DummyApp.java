package org;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org"})
public class DummyApp {
    public static void main(String[] args) {
        SpringApplication.run(DummyApp.class, args);
    }
}

