package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 城市
 */
@TableName("city")
@Data
public class City {
    private Integer id; //主键id
    private String cName; //名称
    private Integer cCode; //编号

    //Location
    private double cLng; //编号
    private double cLat; //lng:经度 lat:纬度
    private Integer dCode; //所属区

}
