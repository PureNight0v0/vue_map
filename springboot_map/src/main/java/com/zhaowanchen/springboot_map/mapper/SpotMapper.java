package com.zhaowanchen.springboot_map.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaowanchen.springboot_map.entity.Spot;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SpotMapper extends BaseMapper<Spot> {
    @Select("select c.c_name AS spotName,round(AVG(hot),2) AS hot,COUNT(s.c_code) AS total,c.c_lng AS lng,c.c_lat AS lat \n"
            +"from spot s left join city c on s.c_code = c.c_code \n"
            +"WHERE c.c_lng BETWEEN #{minLongitude} AND #{maxLongitude} AND c.c_lat BETWEEN #{minLatitude} AND #{maxLatitude} \n"
            +"GROUP BY c.c_code;")
    List<Spot> searchByCity(Double maxLongitude, Double minLongitude, Double maxLatitude, Double minLatitude);

    @Select("select t.s_name AS spotName,round(AVG(hot),2) AS hot,COUNT(s.s_code) AS total,t.s_lng AS lng,t.s_lat AS lat \n"
            +"from spot s left join county t on s.s_code = t.s_code \n"
            +"WHERE t.s_lng BETWEEN #{minLongitude} AND #{maxLongitude} AND t.s_lat BETWEEN #{minLatitude} AND #{maxLatitude} \n"
            +"GROUP BY t.s_code;")
    List<Spot> searchByCounty(Double maxLongitude, Double minLongitude, Double maxLatitude, Double minLatitude);

    @Select("select d.d_name AS spotName,round(AVG(hot),2) AS hot,COUNT(s.d_code) AS total,d.d_lng AS lng,d.d_lat AS lat \n"
            +"from spot s left join district d on s.d_code = d.d_code \n"
            +"WHERE d.d_lng BETWEEN #{minLongitude} AND #{maxLongitude} AND d.d_lat BETWEEN #{minLatitude} AND #{maxLatitude} \n"
            +"GROUP BY d.d_code;")
    List<Spot> searchByDistrict(Double maxLongitude, Double minLongitude, Double maxLatitude, Double minLatitude);

    @Select("select * ,COUNT(*) as total FROM spot s\n" +
            "WHERE lng BETWEEN 115 AND 120 AND lat BETWEEN 35 AND 40\n" +
            "GROUP BY spot_name")
    List<Spot> searchBySpot(Double maxLongitude, Double minLongitude, Double maxLatitude, Double minLatitude);
}
