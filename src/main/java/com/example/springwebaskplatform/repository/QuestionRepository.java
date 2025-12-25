package com.example.springwebaskplatform.repository;

import com.example.springwebaskplatform.model.Question;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class QuestionRepository {
    // 线程安全的内存问题列表
    private final List<Question> questions =
            Collections.synchronizedList(new ArrayList<>());
    // 问题 ID 自增器
    private final AtomicInteger idSeq = new AtomicInteger(0);
     //查询所有问题
    public List<Question> findAll() {
        return questions;
    }
     //根据 ID 查询问题
    public Question findById(int id) {
        synchronized (questions) {
            return questions.stream()
                    .filter(q -> q.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
    }
    //保存新问题
    public Question save(Question question) {
        question.setId(idSeq.incrementAndGet());
        question.setCreatedAt(System.currentTimeMillis());
        questions.add(question);
        return question;
    }
}