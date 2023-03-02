<template>
<div>
  <el-row>
    <el-input v-model="input" style="width:200px;" placeholder="请输入内容"></el-input>
    <el-button type="primary" icon="el-icon-search">搜索</el-button>
    <el-button type="danger" icon="el-icon-plus">新增</el-button>
  </el-row>
  <el-row>
    <el-col :span="10">
  <el-table
    :data="roleData"
    border
    style="width: 100%;">
    <el-table-column
      fixed
      prop="id"
      label="ID"
      width="80">
    </el-table-column>
    <el-table-column
      prop="name"
      label="角色名"
      width="100">
    </el-table-column>
    <el-table-column
      prop="comment"
      label="备注"
      width="150">
    </el-table-column>
    <el-table-column
      label="操作"
      width="100">
      <template>
        <el-button type="text" icon="el-icon-edit"></el-button>
        <el-button type="text" icon="el-icon-delete"></el-button>
      </template>
    </el-table-column>
  </el-table>
  </el-col>
  <el-col :span="14">
  <el-table
    :data="prData"
    border
    style="width: 100%;">
    <el-table-column
      fixed
      prop="id"
      label="ID"
      width="80">
    </el-table-column>
    <el-table-column
      prop="name"
      label="权限名称"
      width="100">
    </el-table-column>
    <el-table-column
      prop="icon"
      label="图标"
      width="150">
    </el-table-column>
    <el-table-column
      prop="comment"
      label="备注"
      width="150">
    </el-table-column>
    <el-table-column
      label="操作"
      width="80">
      <template>
        <el-button type="text" icon="el-icon-edit"></el-button>
        <el-button type="text" icon="el-icon-delete"></el-button>
      </template>
    </el-table-column>
  </el-table>
  </el-col>
  </el-row>
</div>
</template>

<script>
export default {
  methods: {
  },
  data () {
    return {
      roleData: [{
        id: '1',
        name: 'admin',
        comment: '123'
      }],
      prData: [{
        id: '1',
        name: 'admin',
        icon: 'el-icon-location',
        comment: '123'
      }]
    }
  }
}
</script>
<style>
.el-row{
  margin-bottom: 10px;
}
.el-input{
  margin:5px 5px 0 5px;
}
</style>
