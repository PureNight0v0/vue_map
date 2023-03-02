package com.zhaowanchen.springboot_map.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowanchen.springboot_map.entity.Foot;
import com.zhaowanchen.springboot_map.entity.Route;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

public interface FootMapper extends BaseMapper<Foot> {
    @Delete("delete from foot where foot_id=#{footId}")
    int DeleteByFootID(int footId);
}
