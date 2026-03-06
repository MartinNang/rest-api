package org.pytch.backend.controller;

import jakarta.validation.Valid;
import org.pytch.backend.entity.PytchUser;
import org.pytch.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public PytchUser saveUser(
            @Valid @RequestBody PytchUser pytchUser)
    {
        return userService.saveUser(pytchUser);
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
}
