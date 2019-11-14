package com.example.demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;

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
	private UserMapper userMapper;
	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state,
			HttpServletRequest request) {

		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(Client_id);
		accessTokenDTO.setClient_secret(Client_secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setState(state);
		accessTokenDTO.setRedirect_uri(Redirect_uri);
		String accessToken=githubProvider.getAccessToken(accessTokenDTO);
		GithubUser githubUser = githubProvider.getUser(accessToken);
		if(githubUser != null) {
			User user=new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(githubUser.getName());
			user.setAccountId(String.valueOf(githubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);

			//ログイン成功

			request.getSession().setAttribute("user", githubUser);
			return "redirect:/";
		}else {
			//ログイン失敗
			return "redirect:/";
		}


	}
}
