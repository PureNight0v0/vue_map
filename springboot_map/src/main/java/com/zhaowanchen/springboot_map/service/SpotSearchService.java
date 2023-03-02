package com.zhaowanchen.springboot_map.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaowanchen.springboot_map.common.Result;
import com.zhaowanchen.springboot_map.entity.Route;
import com.zhaowanchen.springboot_map.entity.Spot;
import com.zhaowanchen.springboot_map.entity.User;
import com.zhaowanchen.springboot_map.mapper.RouteMapper;
import com.zhaowanchen.springboot_map.mapper.SpotMapper;
//import com.zhaowanchen.springboot_map.vo.SpotResultVo;
//import org.springframework.data.geo.Box;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SpotSearchService {

    @Autowired
    private SpotMapper spotMapper;

    /**
     * 地图找房搜索服务
     *
     * @param maxLongitude 最大经度
     * @param minLongitude 最小经度
     * @param maxLatitude  最大纬度
     * @param minLatitude  最小纬度
     * @param zoom         地图缩放比例值
     * @return
     */
    public List<Spot> search(Double maxLongitude, Double minLongitude,
                             Double maxLatitude, Double minLatitude,
                             Double zoom) {
        //收集聚合查询条件
        List<Spot> spotList = new ArrayList<>();
        QueryWrapper<Spot> queryWrapper = new QueryWrapper<>();
        //在可视范围内搜索
        //根据地图的缩放比例进行分组
        if (zoom < 5.5) { //200公里
            //按照行政区分组
            spotList = spotMapper.searchByDistrict(maxLongitude,minLongitude, maxLatitude,minLatitude);
        } else if (zoom < 9.5) { //25公里
            //按照市级分组
           spotList = spotMapper.searchByCity(maxLongitude,minLongitude, maxLatitude,minLatitude);
        } else if(zoom < 12.5) { //5公里
            //按照区县分组
            spotList = spotMapper.searchByCounty(maxLongitude,minLongitude, maxLatitude,minLatitude);
        } else{
            // 按照景点分组
            spotList = spotMapper.searchBySpot(maxLongitude,minLongitude, maxLatitude,minLatitude);
        }
//        spotList.forEach(System.out::println);
        return spotList;
    }



}
