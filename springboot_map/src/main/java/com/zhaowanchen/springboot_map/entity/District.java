package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 行政区
 */
@TableName("district")
@Data
public class District {
    private Integer dCode; //主键id
    private String dName; //名称
    //Location
    private double dLng; //编号
    private double dLat; //lng:经度 lat:纬度

}
