package org.pytch.backend.controller;

import jakarta.validation.Valid;
import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public PytchUser saveUser(
            @Valid @RequestBody PytchUserDto pytchUserDto)
    {
        return userService.saveUser(new PytchUser(pytchUserDto));
    }

    @GetMapping("/users")
    public List<PytchUser> getUsers()
    {
        return userService.getUsers();
    }

    @PutMapping("/users/{id}")
    public PytchUser updateUser (@RequestBody PytchUser pytchUser,
                                 @PathVariable Long userId)
    {
        return userService.updateUser(pytchUser, userId);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUserById(@PathVariable("id") Long userId)
    {
        return "Deleted successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody PytchUserDto pytchUserDto)
    {
        return "not implemented";
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody PytchUserDto pytchUserDto) {
        String res = userService.registerUser(pytchUserDto);

        return ResponseEntity.ok(res);
    }
}
