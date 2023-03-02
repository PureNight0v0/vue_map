package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("foot")
@Data
public class Foot {
    @TableId(type = IdType.AUTO)
    private int footId;
    private String footTitle;
    private double footLng;
    private double footLat;
    private String footAddress;
    private String footPic;
    private String createTime;
    private int createUser;
}
