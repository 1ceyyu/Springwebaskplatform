package com.example.springwebaskplatform.controller;

import com.example.springwebaskplatform.model.Question;
import com.example.springwebaskplatform.service.QuestionService;
import com.example.springwebaskplatform.model.Reply;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 问题列表页
     * GET /questions
     */
    @GetMapping
    public String list(Model model) {
        List<Question> questions = questionService.listAll();
        model.addAttribute("questions", questions);
        return "question_list"; // templates/question_list.html
    }

    /**
     * 问题详情页
     * GET /questions/{id}
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable int id, Model model) {
        Question question = questionService.getById(id);
        if (question == null) {
            return "redirect:/questions";
        }
        model.addAttribute("question", question);
        return "question_detail";
    }

    /**
     * 跳转到发布问题页面
     * GET /questions/add
     */
    @GetMapping("/add")
    public String addPage(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        return "add_question";
    }

    /**
     * 提交新问题
     * POST /questions/add
     */
    @PostMapping("/add")
    public String addQuestion(
            @RequestParam String title,
            @RequestParam String content,
            HttpSession session) {

        String author = (String) session.getAttribute("user");
        if (author == null) {
            return "redirect:/login";
        }

        questionService.addQuestion(title, content, author);
        return "redirect:/questions";
    }

    /**
     * 提交回复
     * POST /questions/reply
     */
    @PostMapping("/reply")
    public String addReply(
            @RequestParam int questionId,
            @RequestParam String content,
            HttpSession session) {

        String author = (String) session.getAttribute("user");
        if (author == null) {
            return "redirect:/login";
        }

        questionService.addReply(questionId, content, author);
        return "redirect:/questions/" + questionId;
    }
    @GetMapping("/{id}/replies")
    @ResponseBody
    public List<Reply> getReplies(@PathVariable int id) {
        Question question = questionService.getById(id);
        if (question == null) {
            return List.of();
        }
        return question.getReplies();
    }
    @GetMapping("/updates")
    @ResponseBody
    public List<Question> getAllQuestionsJson() {
        return questionService.listAll();
    }
    @GetMapping("/{id}/replies/data") // 路径加个/data区分页面和数据
    @ResponseBody // 极其重要：这保证返回的是 JSON 数组，而不是去寻找 HTML 页面
    public List<Reply> getRepliesData(@PathVariable int id) {
        Question q = questionService.getById(id);
        if (q != null) {
            return q.getReplies(); // 返回回复列表对象，Spring会自动转为JSON
        }
        return new ArrayList<>();
    }
}

