package com.zhaowanchen.springboot_map.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhaowanchen.springboot_map.common.Result;
import com.zhaowanchen.springboot_map.entity.Permission;
import com.zhaowanchen.springboot_map.entity.RolePermission;
import com.zhaowanchen.springboot_map.entity.User;
import com.zhaowanchen.springboot_map.mapper.PermissionMapper;
import com.zhaowanchen.springboot_map.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;
import com.zhaowanchen.springboot_map.utils.TokenUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserMapper userMapper;
    @Resource
    PermissionMapper permissionMapper;


    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername,user.getUsername())
                .eq(User::getPassword,user.getPassword()));
//        System.out.println(res);
        if (res == null){
            return Result.error("-1","用户名或密码错误");
        }
        HashSet<Permission> permissionSet = new HashSet<>();
        // 2.根据roleId从role_permission表查询出所有的permissionId
        List<RolePermission> rolePermissions = permissionMapper.getRolePermissionByRoleId(res.getRoleId());
        for (RolePermission rolePermission : rolePermissions) {
            Integer permissionId = rolePermission.getPermissionId();
            // 3. 根据permissionId查询permission信息
            Permission permission = permissionMapper.selectById(permissionId);
            permissionSet.add(permission);
        }
        // 对资源根据id进行排序
        LinkedHashSet<Permission> sortedSet = permissionSet.stream().sorted(Comparator.comparing(Permission::getId)).collect(Collectors.toCollection(LinkedHashSet::new));

        //设置当前用户的资源信息
        HashSet<Permission> childrenSet = new HashSet<>();
        HashSet<Permission> historySet = new HashSet<>();
        HashSet<Permission> pSet = new HashSet<>();
        for (Permission Set : sortedSet) {
            if(Set!=null) {
                Integer pId = Set.getPid();
                Integer Id = Set.getId();
                Permission permission = permissionMapper.selectById(Id);
                Permission fpermission = permissionMapper.selectById(pId);
                // 根据permissionId查询permission信息
                if (pId != -1) childrenSet.add(permission); else pSet.add(permission);
            }
        }
        // 对资源根据id进行排序
        //LinkedHashSet<Permission> sortedSet = pSet.stream().sorted(Comparator.comparing(Permission::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
        //System.out.println("s"+sortedSet);
//        res.setPermissions(pSet);
        //System.out.println(res);
        // 生成token
        String token = TokenUtils.genToken(res);
        res.setToken(token);
        return Result.success(res);
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody User user){
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername,user.getUsername()));
        if (res != null) return Result.error("-1","用户名已存在");
        if (user.getPassword()==null) user.setPassword("123456");
        userMapper.insert(user);
        return Result.success();
    }
    //获取用户列表
    @GetMapping("/userlist")
    public List<User> showUserList(@RequestParam("id") int user){
        //超级管理员身份
        if (user==1){
            return userMapper.selectAll();
        }
        //普通管理员身份
        return userMapper.selectUser();
    }
    //新增用户
    @PostMapping("/addUser")
    public Result<?> addUser(@RequestBody User user){
        User res=userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername,user.getUsername()));
        if (res != null) return Result.error("-1","用户名已存在");
        if (user.getPassword()==null) user.setPassword("123456");
        userMapper.insert(user);
        return Result.success();
    }
    //删除用户
    @DeleteMapping("/deleteUser")
    public int deleteUser(@RequestParam("id") int id){
        return userMapper.deleteById(id);
    }
    //修改用户信息


}
