package com.example.springwebaskplatform.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class Question {
    private int id;
    private String title;
    private String content;
    private String author;
    private long createdAt;

    // 修改处：使用同步列表保证线程安全
    private List<Reply> replies = Collections.synchronizedList(new ArrayList<>());

    public Question(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = System.currentTimeMillis();
    }

    public void addReply(Reply reply) {
        this.replies.add(reply);
    }
}