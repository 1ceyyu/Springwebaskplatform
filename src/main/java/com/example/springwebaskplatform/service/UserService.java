package com.example.springwebaskplatform.service;

import com.example.springwebaskplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 登录校验
     */
    public boolean login(String username, String password) {
        return userRepository.check(username, password);
    }

    /**
     * 注册用户
     */
    public void register(String username, String password, String confirm) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("用户名不能为空");
        }
        if (password == null || !password.equals(confirm)) {
            throw new IllegalArgumentException("两次密码不一致");
        }
        if (userRepository.exists(username)) {
            throw new IllegalStateException("用户名已存在");
        }
        userRepository.save(username, password);
    }
}