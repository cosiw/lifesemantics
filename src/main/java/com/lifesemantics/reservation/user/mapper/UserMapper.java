package com.lifesemantics.reservation.user.mapper;

import com.lifesemantics.reservation.user.dto.LoginRequestDTO;
import com.lifesemantics.reservation.user.dto.UserRequestDTO;
import com.lifesemantics.reservation.user.dto.UserResponseDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select user_idx, user_id, user_name, user_pwd from tuser WHERE user_id = #{userId}")
    UserResponseDTO login(LoginRequestDTO req);

    @Insert("Insert into tuser (user_id, user_name, user_pwd) value (#{userId}, #{userName}, #{userPwd})")
    int join(UserRequestDTO req);

    @Select("select count(*) from tuser where user_id = #{userId}")
    int findByUserId(String userId);

    @Select("select * from tuser WHERE user_idx= #{userIdx}")
    UserResponseDTO findByUserIdx(@Param("userIdx") int userIdx);
}
