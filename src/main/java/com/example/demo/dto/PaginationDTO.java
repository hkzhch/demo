package com.example.demo.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PaginationDTO {
	private List<QuestionDTO> questions;
	private boolean showPrevious;
	private boolean showFirstpage;
	private boolean showNext;
	private boolean showEndpage;
	private Integer page;
	private List<Integer> pages = new ArrayList<Integer>();
	private Integer totalPage;

	public void setPagination(Integer totalCount, Integer page, Integer size) {

		if (totalCount % size == 0) {
			totalPage = totalCount / size;
		} else {
			totalPage = totalCount / size + 1;
		}

		if (page < 1) {
			page = 1;
		} else if (page > totalPage) {
			page = totalPage;
		}

		this.page = page;

		//生成页数列表
		pages.add(page);
		for (int i = 1; i <= 3; i++) {
			if (page - i > 0) {
				pages.add(0, page - i);
			}
			if (page + i <= totalPage) {
				pages.add(page + i);
			}
		}

		//是否展示上一页
		if (page == 1) {
			showPrevious = false;
		} else {
			showPrevious = true;
		}
		//是否展示下一页
		if (page == totalPage) {
			showNext = false;
		} else {
			showNext = true;
		}
		//是否展示首页
		if (pages.contains(1)) {
			showFirstpage = false;
		} else {
			showFirstpage = true;
		}
		//是否展示末页
		if (pages.contains(totalPage)) {
			showEndpage = false;
		} else {
			showEndpage = true;
		}

	}
}
