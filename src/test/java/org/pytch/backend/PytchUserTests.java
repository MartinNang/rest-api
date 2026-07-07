package org.pytch.backend;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.constraints.AssertTrue;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.pytch.backend.controller.UserController;
import org.pytch.backend.dto.request.PytchUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;

@SpringBootTest
class PytchUserTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    void testRegisterUser() throws Exception {
        PytchUserDto testUserDto = new PytchUserDto("test", "test@test.com", "test");
        Assert.assertEquals(HttpStatusCode.valueOf(200), userController.register(testUserDto).getStatusCode());
    }
}