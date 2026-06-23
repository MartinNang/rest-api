package org.pytch.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.pytch.backend.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PytchUserTests {

    @Autowired
    private UserController userController;



    @Test
    void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }
}