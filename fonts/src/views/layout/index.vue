<template>
  <el-container class="main-container">
    <!-- 头部区域 -->
    <el-header>
      <!-- 左侧的 logo -->
        <img src="../../assets/images/logo.png" style="width:120px" alt="" />
       <div class="eave"> <span style="color:black;line-height:60px;float:left" >Eaves</span></div>
      <!-- 右侧的菜单 -->
      <el-menu
        class="el-menu-top"
        mode="horizontal"
        background-color="#ffffff"
        text-color="#000"
        active-text-color="#409EFF"
      >
        <el-menu-item  index="1">
            <!-- 头像 -->
            <div class="user-box" style="width:150px;">
              <img src="../../assets/images/1.jpeg" alt=""  />
              <span>欢迎,{{ username }}</span>
             </div>
          </el-menu-item>
          <el-submenu index="2" style="width:100px">
            <template slot="title">
            <span>个人中心</span>
          </template>
          <el-menu-item index="2-1"><i class="el-icon-s-operation"></i>基本资料</el-menu-item>
          <el-menu-item index="2-2"><i class="el-icon-camera"></i>更换头像</el-menu-item>
          <el-menu-item index="2-3"><i class="el-icon-key"></i>重置密码</el-menu-item>
        </el-submenu>
        <el-menu-item index="3" style="width:100px"><i class="el-icon-switch-button" @click="quitFn"></i>退出</el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <!-- 侧边栏区域 -->
      <!--左侧导航菜单 -->
      <el-aside width="200px">
          <!-- :default-active="$route.path" -->
        <el-menu
          default-active="/map"
          class="el-menu-vertical-demo"
          background-color="#fff"
          text-color="#000"
          active-text-color="#409EFF"
          unique-opened
          router
        >
          <el-menu-item index="/map">
            <i class="el-icon-s-promotion"></i>地图检索
          </el-menu-item>
          <el-submenu index="/hot">
            <template slot="title">
              <i class="el-icon-s-data"></i>
              <span>热门路线</span>
            </template>
              <el-menu-item index="/spot"><i class="el-icon-location"></i>景点集结</el-menu-item>
              <el-menu-item index="/data" v-if="roleId==1 || roleId == 2"><i class="el-icon-data-analysis"></i>数据统计</el-menu-item>
          </el-submenu>
          <el-submenu index="/path">
            <template slot="title">
              <i class="el-icon-map-location"></i>
              <span>路线管理</span>
            </template>
              <el-menu-item index="/foot"><i class="el-icon-s-flag"></i>循迹脚印</el-menu-item>
              <el-menu-item index="/way"><i class="el-icon-position"></i>循迹路线</el-menu-item>
          </el-submenu>
          <el-submenu index="/system" v-if="roleId==1 || roleId == 2">
            <template slot="title">
              <i class="el-icon-s-tools"></i>
              <span>系统管理</span>
            </template>
              <el-menu-item index="/user"><i class="el-icon-user-solid"></i>用户管理</el-menu-item>
              <el-menu-item index="/role"  v-if="roleId==1"><i class="el-icon-s-operation"></i>权限管理</el-menu-item>
          </el-submenu>
        </el-menu></el-aside>
      <el-container>
        <!-- 页面主体区域 -->
        <el-main>
           <router-view></router-view>
        </el-main>
        <!-- 底部 footer 区域 -->
        <el-footer style="height: 1px;"></el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  name: 'my-layout',
  data () {
    return {
      roleId: 3
    }
  },
  methods: {
    handleSelect (key, keyPath) {
      this.$router.push(key)
    },
    quitFn () {
      // 询问用户是否退出登录
      this.$confirm('您确认退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行退出登录的操作
        // 1. 清空 token
        this.$store.commit('updateToken', '')
        // 2. 跳转到登录页面
        this.$router.push('/login')
      }).catch((err) => err)
    },
    loadRoleId () {
      const userStr = localStorage.getItem('vuex') || '{}'
      this.roleId = JSON.parse(userStr).userInfo.roleId
    }
  },
  computed: {
    ...mapGetters(['username'])
  },
  created () {
    this.loadRoleId()
  }
}
</script>

<style lang="less" scoped>
.main-container {
  height: 100%;
  .el-header,
  .el-aside {
    background-color: #ffffff;
    .el-submenu,
    .el-menu-item {
      width: 200px;
      user-select: none;
   }
  }
  .el-header {
    padding: 0;
    display: flex;
    justify-content: space-between;
  }
  .eave {
    display: flex;
    width: 50%;
  }
  .el-main {
    overflow-y: scroll;
    height: 0;
    padding: 5px;
    background-color: #F2F2F2;
  }
  .el-footer {
    background-color: #eee;
    font-size: 10px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
.avatar {
  border-radius: 50%;
  width: 35px;
  height: 35px;
  background-color: #fff;
  margin-right: 10px;
  object-fit: cover;
}
.user-box {
  height: 60px;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: none;
  img {
    width: 35px;
    height: 33px;
    border-radius: 50%;
    background-color: #fff;
    margin-right: 15px;
    object-fit: cover;
  }
  span {
    color: black;
    font-size: 12px;
  }
}
</style>
