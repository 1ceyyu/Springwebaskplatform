package com.example.springwebaskplatform.service;


import com.example.springwebaskplatform.model.Question;
import com.example.springwebaskplatform.model.Reply;
import com.example.springwebaskplatform.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    // 获取所有问题列表
    public List<Question> listAll() {
        return questionRepository.findAll();
    }

    // 根据 ID 获取单个问题
    public Question getById(int id) {
        return questionRepository.findById(id);
    }

    // 添加新问题
    public void addQuestion(String title, String content, String author) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("标题不能为空");
        }
        Question q = new Question(title, content, author);
        questionRepository.save(q);
    }

   // 添加回复到指定问题
    public void addReply(int questionId, String content, String author) {
        Question q = questionRepository.findById(questionId);
        if (q == null) {
            throw new IllegalArgumentException("问题不存在");
        }
        q.addReply(new Reply(content, author));
    }
}