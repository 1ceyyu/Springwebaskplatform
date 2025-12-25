package com.example.springwebaskplatform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {

    private String content;
    private String author;
    private long createdAt = System.currentTimeMillis();
    public Reply(String content, String author) {
        this.content = content;
        this.author = author;
        this.createdAt = System.currentTimeMillis();
    }
    public String getFormattedTime() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new java.util.Date(this.createdAt));
    }
}