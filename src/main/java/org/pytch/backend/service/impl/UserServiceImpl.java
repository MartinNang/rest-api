package org.pytch.backend.service.impl;

import org.pytch.backend.dto.request.PytchUserDto;
import org.pytch.backend.model.PytchUser;
import org.pytch.backend.repository.UserRepository;
import org.pytch.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public PytchUser saveUser(PytchUser pytchUser) {
        return userRepository.save(pytchUser);
    }

    @Override
    public List<PytchUser> getUsers() {
        return (List<PytchUser>) userRepository.findAll();
    }

    @Override
    public PytchUser findUserById(Long id) {
        return  userRepository.findById(id).get();
    }

    @Override
    public PytchUser updateUser(PytchUser pytchUser, Long userId) {
        PytchUser pytchUserDb = userRepository.findById(userId).get();

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

        return userRepository.save(pytchUserDb);
    }

    @Override
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<PytchUser> user = this.userRepository.findPytchUserByEmail(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    @Override
    public String registerUser(PytchUserDto pytchUserDto) {
        PytchUser existingUser = userRepository.findPytchUserByEmail(pytchUserDto.getEmail()).orElse(null);

        if (existingUser != null) {
            return "user already exists";
        }

        PytchUser newUser = new PytchUser(pytchUserDto);

        userRepository.save(newUser);

        return "registration successful";
    }
}
