package org.pytch.backend.service.impl;

import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.model.Project;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.repository.ProjectRepository;
import org.pytch.backend.repository.UserRepository;
import org.pytch.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, @Lazy PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public PytchUser saveUser(PytchUser pytchUser) {
        return repository.save(pytchUser);
    }

    @Override
    public List<PytchUser> getUsers() {
        return (List<PytchUser>) repository.findAll();
    }

    @Override
    public PytchUser findUserById(Long id) {
        return  repository.findById(id).get();
    }

    @Override
    public PytchUser findUserByUsername(String username) {
        return repository.findPytchUserByUsername(username).get();
    }

    @Override
    public PytchUser updateUser(PytchUser pytchUser, Long userId) {
        PytchUser pytchUserDb = repository.findById(userId).get();

        String name = pytchUser.getUsername();
        if(name != null && name.isEmpty()) {
            pytchUserDb.setUsername(name);
        }

        String email = pytchUser.getEmail();
        if(email != null && email.isEmpty()) {
            pytchUserDb.setEmail(email);
        }

        String password = pytchUser.getPassword();
        if(password != null && password.isEmpty()) {
            pytchUserDb.setPassword(password);
        }

        return repository.save(pytchUserDb);
    }

    @Override
    public void deleteUserById(Long userId) {
        repository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PytchUser> user = this.repository.findPytchUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        PytchUser u = user.get();
        return User.withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(u.getAuthorities())
                .build();
    }

    @Override
    public String registerUser(PytchUserDto pytchUserDto) {
        PytchUser existingUser = repository.findPytchUserByEmail(pytchUserDto.getEmail()).orElse(null);

        if (existingUser != null) {
            return "user already exists";
        }

        PytchUser newUser = new PytchUser(pytchUserDto);

        newUser.setPassword(encoder.encode(pytchUserDto.getPassword()));

        newUser.setCreatedAt(Timestamp.from(Instant.now()));

        repository.save(newUser);

        return "registration successful";
    }
}
