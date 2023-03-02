package com.zhaowanchen.springboot_map.controller;

import com.zhaowanchen.springboot_map.entity.Foot;
import com.zhaowanchen.springboot_map.entity.Route;
import com.zhaowanchen.springboot_map.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/map")
@RestController
public class RouteController {
    @Autowired
    private RouteService routeService;

    @PostMapping("/route")
    public int insertPoints(@RequestBody Route route){
//                            @RequestParam("routePath") String routePath,
//                            @RequestParam("routeLocation") String routeLocation,
//                            @RequestParam("routeTitle") String routeTitle,
//                            @RequestParam("createUser") int createUser
        //1.1标题-默认：时间+创建者+随机数
        //1.2path： >
        //1.3comment：每个点的经纬度变成geolocation保存
        //2.1用户id
//        System.out.println("controller1"+routePath);
        return routeService.insertPoints(route);

    }
    //显示路线列表
    @GetMapping("/routelist")
    public List<Route> showRouteList(@RequestParam("id") int create_user){
        return routeService.showRouteList(create_user);
    }
    //显示脚印列表
    @GetMapping("/footlist")
    public List<Foot> showFootList(@RequestParam("id") int create_user){
        return routeService.showFootList(create_user);
    }
    //更改某一条路线
    @PutMapping("/updateRoute")
    public int updateRoute(@RequestBody Route route){
        return routeService.updateRoute(route);
    }
    //更改某一条路线
    @PutMapping("/updateFoot")
    public int updateFoot(@RequestBody Foot foot){
        return routeService.updateFoot(foot);
    }
    //删除某一条线路
    @DeleteMapping("/deleteRoute")
    public int deleteRoute(@RequestBody Route route){
        return routeService.deleteRoute(route);
    }
    //删除某一个脚印
    @DeleteMapping("/deleteFoot")
    public int deleteFoot(@RequestBody Foot foot){ return routeService.deleteFoot(foot); }
    //新增一个脚印
    @PostMapping("/foot")
    public int insertFoot(@RequestBody Foot foot) {
      return routeService.insertFoot(foot);
    }
    //搜索脚印信息
    @GetMapping("/searchFoot")
    public List<Foot> searchFoot(@RequestParam("input") String input){
        return routeService.searchFoot(input);
    }
    //搜索路线信息
    @GetMapping("/searchRoute")
    public List<Route> searchRoute(@RequestParam("input") String input){
        return routeService.searchRoute(input);
    }
}
