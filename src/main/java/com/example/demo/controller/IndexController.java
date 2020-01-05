package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.service.QusetionService;

@Controller
public class IndexController {

	@Autowired
	private QusetionService questionService;

	@GetMapping("/")
	public String index(HttpServletRequest request,
			Model model,
			@RequestParam(name = "page" ,defaultValue = "1")Integer page,
			@RequestParam(name = "size" ,defaultValue = "5")Integer size) {
		/*Cookie[] cookies = request.getCookies();
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
		}*/
		PaginationDTO pagination=questionService.list(page,size);
		model.addAttribute("pagination", pagination);
		return "index";
	}
}
