package com.example.springwebaskplatform.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class CaptchaController {

    @GetMapping("/captcha")
    public void captcha(HttpServletResponse response, HttpSession session) throws IOException {

        // 图片大小
        int width = 100;
        int height = 40;

        // 创建图片
        BufferedImage image =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();

        // 背景
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 随机验证码
        String code = String.valueOf(1000 + new Random().nextInt(9000));

        // 写入 Session
        session.setAttribute("captcha", code);

        // 画文字
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 28));
        g.drawString(code, 15, 30);

        g.dispose();

        // 设置响应头
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-store");

        // 输出图片
        ImageIO.write(image, "png", response.getOutputStream());
    }
}
