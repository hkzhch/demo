package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PaginationDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;

@Service
public class QuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private UserMapper userMapper;

	public PaginationDTO list(Integer page, Integer size) {

		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.count();
		paginationDTO.setPagination(totalCount, page, size);

		Integer offset = size * (paginationDTO.getPage() - 1);

		List<Question> questions = questionMapper.list(offset, size);
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		for (Question question : questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		paginationDTO.setQuestions(questionDTOList);
		return paginationDTO;
	}

	public PaginationDTO list(Integer userId, Integer page, Integer size) {

		PaginationDTO paginationDTO = new PaginationDTO();
		Integer totalCount = questionMapper.countOwn(userId);
		paginationDTO.setPagination(totalCount, page, size);

		Integer offset = size * (paginationDTO.getPage() - 1);

		List<Question> questions = questionMapper.listOwn(userId ,offset, size);
		List<QuestionDTO> questionDTOList = new ArrayList<>();
		for (Question question : questions) {
			User user = userMapper.findById(question.getCreator());
			QuestionDTO questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOList.add(questionDTO);
		}
		paginationDTO.setQuestions(questionDTOList);
		return paginationDTO;

	}

	public QuestionDTO getById(Integer id) {
		QuestionDTO questionDTO = new QuestionDTO();
		Question question = questionMapper.getById(id);
		BeanUtils.copyProperties(question, questionDTO);
		User user = userMapper.findById(question.getCreator());
		questionDTO.setUser(user);
		return questionDTO;
	}

	public void createOrUpdate(Question question) {
		if(question.getId()==null) {
			//create
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			questionMapper.create(question);
		}else {
			//update
			question.setGmtModified(System.currentTimeMillis());
			questionMapper.update(question);
		}
	}

}
