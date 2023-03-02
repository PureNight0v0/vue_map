package com.zhaowanchen.springboot_map.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaowanchen.springboot_map.entity.Foot;
import com.zhaowanchen.springboot_map.entity.Route;
import com.zhaowanchen.springboot_map.entity.User;
import com.zhaowanchen.springboot_map.mapper.FootMapper;
import com.zhaowanchen.springboot_map.mapper.RouteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class RouteService {

    @Resource
    private RouteMapper routeMapper;
    @Resource
    private FootMapper footMapper;

    public int insertPoints(Route route) {
        if(route.getCreateTime()==null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String format = simpleDateFormat.format(date);
            route.setCreateTime(format);
        }
//       此处直接存，从后端返回前端时再处理经纬度geojson -- https://www.cnblogs.com/onecyl/p/15214000.html
//        String[] location = route.getRouteLocation().split("[|]");
//        for(int i = 0;i < location.length; i++){
//            System.out.println(location[i]);
//            String[] point = location[i].split(",");
//            JSONObject result = new JSONObject();
//            if (i==0) result.put("lng",point[0]); else result.put("lat",point[1]);
//            System.out.println(point[i]);
//        }
        return routeMapper.insert(route);
    }

    public List<Route> showRouteList(int create_user) {
        if (create_user == 1) {
            return routeMapper.selectAll(create_user);
        }else{
            return routeMapper.selectList(Wrappers.<Route>lambdaQuery()
                    .eq(Route::getCreateUser,create_user));
        }
    }

    public List<Foot> showFootList(int create_user) {
        return footMapper.selectList(Wrappers.<Foot>lambdaQuery()
                .eq(Foot::getCreateUser,create_user));

    }

    public int updateRoute(Route route) {
        String[] date=route.getCreateTime().split("T");
        route.setCreateTime(date[0]);
        return routeMapper.updateById(route);
    }

    public int updateFoot(Foot foot) {
        String[] date=foot.getCreateTime().split("T");
        foot.setCreateTime(date[0]);
        System.out.println(foot.getFootPic());
       return footMapper.updateById(foot);
    }

    public int deleteRoute(Route route) {
        int routeId=route.getRouteId();
        return routeMapper.DeleteByRouteID(routeId);
    }

    public int deleteFoot(Foot foot) {
        int footId = foot.getFootId();
        return footMapper.DeleteByFootID(footId);
    }

    public int insertFoot(Foot foot) {
        return 0;
    }

    public List<Foot> searchFoot(String input) {
        return footMapper.selectList(Wrappers.<Foot>lambdaQuery()
        .like(Foot::getFootTitle,input));
    }

    public List<Route> searchRoute(String input) {
        return routeMapper.selectList(Wrappers.<Route>lambdaQuery()
                .like(Route::getRouteTitle,input));
    }
}
