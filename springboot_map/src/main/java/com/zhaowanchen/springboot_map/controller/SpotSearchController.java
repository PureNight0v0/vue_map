package com.zhaowanchen.springboot_map.controller;

import com.zhaowanchen.springboot_map.common.Result;
import com.zhaowanchen.springboot_map.entity.Route;
import com.zhaowanchen.springboot_map.entity.Spot;
import com.zhaowanchen.springboot_map.service.SpotSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/spot")
@RestController
public class SpotSearchController {

    @Autowired
    private SpotSearchService spotSearchService;

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
    @GetMapping("/search")
    public List<Spot> search(@RequestParam("maxLongitude") Double maxLongitude,
                                     @RequestParam("minLongitude") Double minLongitude,
                                     @RequestParam("maxLatitude") Double maxLatitude,
                                     @RequestParam("minLatitude") Double minLatitude,
                                     @RequestParam("zoom") Double zoom) {
        return spotSearchService.search(maxLongitude, minLongitude, maxLatitude, minLatitude, zoom);
    }
//    @PostMapping("/route")
//    public int insertPoints(
//           @RequestBody Route route){
////                            @RequestParam("routePath") String routePath,
////                            @RequestParam("routeLocation") String routeLocation,
////                            @RequestParam("routeTitle") String routeTitle,
////                            @RequestParam("createUser") int createUser
//        //1.1标题-默认：时间+创建者+随机数
//        //1.2path： >
//        //1.3comment：每个点的经纬度变成geolocation保存
//        //2.1用户id
////        System.out.println("controller1"+routePath);
//        return r.insertPoints(route);
//
//    }

}
