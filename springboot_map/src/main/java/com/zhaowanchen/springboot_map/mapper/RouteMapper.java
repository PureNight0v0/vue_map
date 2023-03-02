package com.zhaowanchen.springboot_map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowanchen.springboot_map.entity.Route;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteMapper extends BaseMapper<Route> {
    @Select("select * from route")
    List<Route> selectAll(int create_user);

    @Delete("delete from route where route_id=#{routeId}")
    int DeleteByRouteID(int routeId);
}
