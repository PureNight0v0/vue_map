package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("spot")
@Data
public class Spot {
    private String spotName; //房源标题
    private Integer hot; //热度
    private String spotAddress; //创建时间
    private Integer total;
    //Location
    private double lng; //编号
    private double lat; //lng:经度 lat:纬度

    private Integer dCode; //所属行政区
    private Integer cCode; //所属城市
    private Integer sCode; //区县
}
