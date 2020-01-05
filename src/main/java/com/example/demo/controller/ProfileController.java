package com.example.demo.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.QusetionService;

@Controller
public class ProfileController {

	@Autowired
	UserMapper userMapper;

	@Autowired
	private QusetionService questionService;

	@GetMapping("/profile/{action}")
	private String profile(@PathVariable(name = "action") String action,
			Model model,
			HttpServletRequest request,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {
		User user = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equalsIgnoreCase("token")) {
					String token = cookie.getValue();
					user = userMapper.findByToken(token);
					if (user != null) {
						request.getSession().setAttribute("user", user);
					}
					break;
				}
			}
		}

		if (user == null) {
			return "redirect:/";
		}

		if ("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的问题");
		} else if ("replies".equals(action)) {
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "最新回复");
		}

		PaginationDTO pagination = questionService.list(user.getId(), page, size);
		model.addAttribute("pagination", pagination);

		return "profile";
	}

}
