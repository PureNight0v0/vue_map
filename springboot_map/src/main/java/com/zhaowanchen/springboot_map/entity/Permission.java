package com.zhaowanchen.springboot_map.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@TableName("permission")
@Data
public class Permission {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer pid;
    private String name;
    private String path;
    private String comment;
    private String icon;
    @TableField(exist = false)
    private Set<Permission> cildren;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return path.equals(that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
