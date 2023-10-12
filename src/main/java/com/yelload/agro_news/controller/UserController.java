package com.yelload.agro_news.controller;

import com.yelload.agro_news.entity.User;
import com.yelload.agro_news.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/{id}")
    public ResponseEntity<String> findById(@PathVariable Long id) {
        log.debug("Get user by id => findById()");
        return new ResponseEntity<>(userService.getUser(id).getUsername(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> createUser(@Valid @RequestBody User user) {
        log.debug("Create user => createUser()");
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
