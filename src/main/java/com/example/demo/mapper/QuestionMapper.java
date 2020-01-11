package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.model.Question;

@Mapper
public interface QuestionMapper {
	@Insert("insert into Question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
	 void create(Question question);

	@Select("select * from Question limit #{offset},#{size}")
	 List<Question> list(@Param(value="offset")Integer offset, @Param(value="size")Integer size);

	@Select("select count(1) from Question")
	 Integer count();

	@Select("select * from Question where creator = #{userId} limit #{offset},#{size}")
	List<Question> listOwn(@Param(value="userId")Integer userId,@Param(value="offset")Integer offset, @Param(value="size")Integer size);

	@Select("select count(1) from Question where creator = #{userId}")
	Integer countOwn(@Param(value="userId")Integer userId);

	@Select("select * from Question where id = #{id}")
	Question getById(@Param(value="id")Integer id);
}
