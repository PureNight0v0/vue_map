package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 小区
 */
@TableName("county")
@Data
public class County {

    private Integer sId; //主键id
    private String name; //名称

    //Location
    private double sLng; //编号
    private double sLat; //lng:经度 lat:纬度

    private Integer dCode; //所属区
    private Integer cCode; //商圈


}
