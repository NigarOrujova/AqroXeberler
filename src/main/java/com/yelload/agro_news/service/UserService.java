package com.yelload.agro_news.service;

import com.yelload.agro_news.entity.User;

public interface UserService {
    User getUser(Long id);

    User getUser(String username);

    User saveUser(User user);
}
