package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.demo.model.User;

@Mapper
public interface UserMapper {
	@Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
	public void insert(User user);

	@Select("select * from user where token=#{token}")
	public User findByToken(@Param("token") String token);

	@Select("select * from user where id=#{id}")
	public User findById(@Param("id") Integer id);

	@Update("update user set token=#{token}, name=#{name}, avatar_url=#{avatarUrl}, gmt_modified=#{gmtModified} where id=#{id}")
	public void update(User user);

	@Select("select * from user where account_id=#{accountId}")
	public User findByAccountId(@Param("accountId") String accountId);

}
