package com.example.springwebaskplatform.repository;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserRepository {

    private final Map<String, String> users = new ConcurrentHashMap<>();

    // 初始化默认用户
    public UserRepository() {
        users.put("admin", "123456");
    }
    // 验证用户名和密码
    public boolean check(String username, String password) {
        return password != null && password.equals(users.get(username));
    }
    // 检查用户是否存在
    public boolean exists(String username) {
        return users.containsKey(username);
    }
    // 保存新用户
    public boolean save(String username, String password) {
        return users.putIfAbsent(username, password) == null;
    }
}