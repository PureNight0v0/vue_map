package com.zhaowanchen.springboot_map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowanchen.springboot_map.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

    @Select("select u.id,u.email,u.username,u.password,u.phone,r.comment as roleComment from user u left join role r\n" +
            "on u.role_id=r.id")
    List<User> selectAll();

    @Select("select u.id,u.email,u.username,u.password,u.phone,r.comment as roleComment from user u left join role r\n" +
            "on u.role_id=r.id\n" +
            "WHERE u.id >= 2 ")
    List<User> selectUser();
}
