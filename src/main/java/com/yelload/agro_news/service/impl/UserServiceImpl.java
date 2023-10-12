package com.yelload.agro_news.service.impl;

import com.yelload.agro_news.entity.User;
import com.yelload.agro_news.exception.EntityNotFoundException;
import com.yelload.agro_news.repository.UserRepository;
import com.yelload.agro_news.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        log.debug("Get user from db");
        return unwrapUser(user, id);
    }

    @Override
    public User getUser(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        log.debug("Get user my username");
        return unwrapUser(user);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.debug("Save user");
        return userRepository.save(user);
    }

    static User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(id, User.class);
    }

    static User unwrapUser(Optional<User> entity) {
        if (entity.isPresent())
            return entity.get();
        else
            throw new EntityNotFoundException(User.class);
    }
}
