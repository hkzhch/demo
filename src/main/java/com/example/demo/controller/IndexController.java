package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.QusetionService;

@Controller
public class IndexController {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private QusetionService questionService;

	@GetMapping("/")
	public String index(HttpServletRequest request,
			Model model) {
		Cookie[] cookies = request.getCookies();
		if(cookies !=null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("token")) {
					String token = cookie.getValue();
					User user = userMapper.findByToken(token);
					if (user != null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}
		List<QuestionDTO> questionList=questionService.list();
		model.addAttribute("questions", questionList);
		return "index";
	}
}
