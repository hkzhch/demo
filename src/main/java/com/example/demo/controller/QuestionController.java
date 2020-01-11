package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.service.QuestionService;

@Controller
public class QuestionController {

	@Autowired
	private QuestionService questionService;

	@GetMapping("/question/{id}")
	private String question(@PathVariable(name = "id") Integer id,
			Model model) {

		QuestionDTO questionDto=questionService.getById(id);
		model.addAttribute("question", questionDto);
		return "question";
	}
}
