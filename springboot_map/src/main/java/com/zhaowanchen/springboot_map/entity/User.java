package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sun.corba.se.spi.ior.ObjectId;
import lombok.Data;

import java.util.Set;

@TableName("user")
@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Integer id; //主键id
    private String username;
    private String password;
    private String email;
    private String phone;
    private Integer roleId;

    @TableField(exist = false)
    private String roleComment;

    @TableField(exist = false)
    private String token;
    @TableField(exist = false)
    private Set<Permission> permissions;
}
