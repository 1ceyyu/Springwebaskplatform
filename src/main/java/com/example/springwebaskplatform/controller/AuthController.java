package com.example.springwebaskplatform.controller;

import com.example.springwebaskplatform.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 登录页
     */
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    /**
     * 处理登录
     */
    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String captcha,
            HttpSession session,
            Model model) {

        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (captcha == null || !captcha.equals(sessionCaptcha)) {
            model.addAttribute("msg", "验证码错误");
            return "login";
        }

        if (userService.login(username, password)) {
            session.setAttribute("user", username);
            return "redirect:/questions";
        }

        model.addAttribute("msg", "用户名或密码错误");
        return "login";
    }

    /**
     * 注册页
     */
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    /**
     * 处理注册
     */
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirm,
            @RequestParam String captcha, // 1. 接收验证码
            HttpSession session,
            Model model) {

        // 2. 校验验证码逻辑
        String sessionCaptcha = (String) session.getAttribute("captcha");
        if (captcha == null || !captcha.equals(sessionCaptcha)) {
            model.addAttribute("msg", "验证码错误");
            return "register";
        }

        try {
            userService.register(username, password, confirm);
            session.setAttribute("user", username);
            return "redirect:/questions";
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage());
            return "register";
        }
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
