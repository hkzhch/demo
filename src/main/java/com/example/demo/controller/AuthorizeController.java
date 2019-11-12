package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;

@Controller
public class AuthorizeController {


	@Autowired
	private GithubProvider githubProvider;

		@Value("${github.client.id}")
		private String Client_id;
		@Value("${github.client.secret}")
		private String Client_secret;
		@Value("${github.redirect.uri}")
		private String Redirect_uri;

//	private String Client_id="8f839873645001067bbc";
//	private String Client_secret="00173774aae69cb04341510a87cf67ef07ae9448";
//	private String Redirect_uri="http://localhost:8080/callback";

	@GetMapping("/callback")
	public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state) {
		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(Client_id);
		accessTokenDTO.setClient_secret(Client_secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setState(state);
		accessTokenDTO.setRedirect_uri(Redirect_uri);
		String accessToken=githubProvider.getAccessToken(accessTokenDTO);
		GithubUser user=githubProvider.getUser(accessToken);
		System.out.println(user.getName());
		System.out.println(user.getId());
		System.out.println(user.getBio());
		return "index";
	}
}
