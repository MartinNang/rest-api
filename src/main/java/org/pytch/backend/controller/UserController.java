package org.pytch.backend.controller;

import jakarta.validation.Valid;
import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.model.AuthRequest;
import org.pytch.backend.model.Project;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.service.UserService;
import org.pytch.backend.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome()
    {
        return "Welcome!";
    }

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

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @GetMapping("/users/{user_id}")
    public PytchUser getUser(@PathVariable("user_id") Long userId, Principal principal)
    {
        // TODO: test if user is admin
        return userService.findUserById(userId);
    }

    @GetMapping("/users/profile")
    public PytchUserDto getCurrentUser (Principal principal)
    {
        PytchUser currentUser = userService.findUserByUsername(principal.getName());
        if (currentUser != null)
        {
            return new PytchUserDto(currentUser.getUsername(), currentUser.getEmail(), currentUser.getPassword());
        }

        throw new UsernameNotFoundException("Invalid user request!");
    }
}
