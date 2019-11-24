package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;


@Service
public class QusetionService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;

	public List<QuestionDTO> list() {
		List<Question> questions=questionMapper.List();
		List<QuestionDTO> questionDTOList=new ArrayList<>();
		for (Question question : questions) {
			User user=userMapper.findById(question.getCreator());
			QuestionDTO questionDTO=new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}

		return questionDTOList;
	}

}
