package org.pytch.backend.service;

import org.pytch.backend.entity.PytchUser;
import org.pytch.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PytchUser updateUser(PytchUser pytchUser, Long userId) {
        PytchUser pytchUserDb = userRepository.findById(userId).get();

        String name = pytchUser.getName();
        if(name != null && name.isEmpty()) {
            pytchUserDb.setName(name);
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
}
