<template>
<div>
  <el-row>
    <el-input v-model="input" style="width:200px;" placeholder="请输入内容"></el-input>
    <el-button type="primary" icon="el-icon-search">搜索</el-button>
    <el-button type="danger" icon="el-icon-plus">新增</el-button>
  </el-row>
  <el-table
    :data="tableData"
    :key="id"
    highlight-current-row
    @current-change="handleCurrentChange"
    border
    style="width: 100%;margin-left:10px">
    <el-table-column
      fixed
      prop="id"
      label="ID"
      width="80">
    </el-table-column>
    <el-table-column
      prop="username"
      label="用户名"
      width="150">
    </el-table-column>
    <el-table-column
      prop="password"
      label="密码"
      width="160">
    </el-table-column>
    <el-table-column
      prop="phone"
      label="手机号"
      width="180">
    </el-table-column>
    <el-table-column
      prop="email"
      label="邮箱"
      width="200">
    </el-table-column>
    <el-table-column
      prop="roleComment"
      label="角色"
      width="150">
    </el-table-column>
    <el-table-column
      label="操作"
      width="120">
      <template>
        <el-button type="text" icon="el-icon-edit"></el-button>
        <el-button type="text" icon="el-icon-delete"></el-button>
      </template>
    </el-table-column>
  </el-table>
</div>
</template>

<script>
export default {
  methods: {
  },
  data () {
    return {
      tableData: [{
        id: '1',
        username: 'admin',
        password: '123456',
        phone: '17856783647',
        email: '123@abc.com',
        roleComment: '超级管理员'
      }],
      currentRow: null
    }
  },
  methods: {
    handleCurrentChange (val) {
      this.currentRow = val
    },
    showUserList () {
      const that = this
      const userStr = localStorage.getItem('vuex') || '{}'
      const userId = JSON.parse(userStr).userInfo.id
      const axios = require('axios')
      axios.get('/user/userlist', { params: { id: userId } })
        .then(function (res) {
          console.log(res.data)
          that.tableData = res.data
        })
    }
  },
  created () {
    this.showUserList()
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
