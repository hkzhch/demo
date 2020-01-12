package com.example.demo.controller;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.service.UserService;

@Controller
public class AuthorizeController {
	@Autowired
	private GithubProvider githubProvider;

	@Value("${git.client.id}")
	private String Client_id;
	@Value("${git.client.secret}")
	private String Client_secret;
	@Value("${git.redirect.uri}")
	private String Redirect_uri;

	@Autowired
	private UserService userService;

	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state,
			HttpServletResponse response) {

		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setClient_id(Client_id);
		accessTokenDTO.setClient_secret(Client_secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setState(state);
		accessTokenDTO.setRedirect_uri(Redirect_uri);
		String accessToken = githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);
		if (githubUser != null && githubUser.getId() != null) {
			//ログイン成功
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setAvatarUrl(githubUser.getAvatarUrl());
			userService.createOrUpdate(user);
			response.addCookie(new Cookie("token", token));
			return "redirect:/";
		} else {
			//ログイン失敗
			return "redirect:/";
		}

	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) {

		request.getSession().removeAttribute("user");
		Cookie cookie = new Cookie("token", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
}
