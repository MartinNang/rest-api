package org.pytch.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) throws URISyntaxException, IOException {
        SpringApplication.run(BackendApplication.class, args);
    }

}
