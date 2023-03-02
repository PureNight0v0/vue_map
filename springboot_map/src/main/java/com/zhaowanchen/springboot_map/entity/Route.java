package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@TableName("route")
@Data
public class Route {
    @TableId(type = IdType.AUTO)
    private int routeId;
    private String routeTitle;
    private String routePath;
    private String routeComment;
    private String routePic;
    private String routeLocation;
//    @JsonIgnore
//    private GeoJsonPoint location;

    private String createTime;
    private int createUser;
}
