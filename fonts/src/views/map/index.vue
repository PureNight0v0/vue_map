<template>
<div class="root">
  <div class="map" id="container" @click="sendData"></div>
  <div class="drawer" >
    <!-- <map-drawer v-on:titleChanged="searchLocation" v-bind:locations="locations"></map-drawer> -->
    <div class="Anchor_fixmain">
    <span v-if="isOpen" class="btnlr_box" @click="openfn($event)"><i class="el-icon-arrow-right"></i></span>
    <span v-if="!isOpen" class="btnlr_box" @click="openfn($event)"><i class="el-icon-arrow-left"></i></span>
</div>
<!-- 左侧树抽屉 -->
<el-drawer  draggable
    title=""
    :visible.sync="isOpen"
    :mask-closable="false"
    :closable="true"
    direction="rtl"
    width="340"
    style="opacity: 0.9;"
    :mask="false"
    :append-to-body="true"
    :modal-append-to-body="false"
    placement="left">
    <el-row type="flex" justify="center">
      <el-input id="cityName" v-model='address' placeholder="请输入内容" style="width:300px" clearable></el-input>
      <el-button type="primary" @click="searchLocation" round >搜索</el-button>
    </el-row>
    <el-row>
      <el-col :span="4" style="margin-left: 45px;"><span>经度：</span></el-col>
      <el-col :span="4" style="margin-left: 20px;"><input type="text" id="lat" :value="location.lat" /></el-col>
    </el-row>
    <el-row>
      <el-col :span="4" style="margin-left: 45px;"><span>纬度：</span></el-col>
      <el-col :span="4" style="margin-left: 20px;"><input type="text" id="lng" :value="location.lng"/></el-col>
    </el-row>
    <el-row>
      <el-button type="primary" @click="submit" round style="margin-right:30px;float:right;">提交</el-button>
    </el-row>
    <el-timeline>
    <el-timeline-item
      v-for="(activity, index) in points"
      :key="index"
      :timestamp="activity.timestamp"
      :color="activity.color">
      {{activity.ptitle}}
    </el-timeline-item>
  </el-timeline>
  <el-row>
    <el-button type="primary" @click="updown" round style="margin-right:10px;float:right;">显示</el-button>
    <el-button type="primary" @click="clrpoint" round style="margin-right:30px;float:right;">清除</el-button>
     <el-button type="primary" @click="innerDrawer = true" round style="margin-right:30px;float:right;">上传</el-button>
      <el-drawer
     title=""
     :append-to-body="true"
     :visible.sync="innerDrawer">
     <div style="margin: 20px;"></div>
      <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign" ref="mapRef">
        <el-form-item label="路径标题">
          <el-input v-model="formLabelAlign.routeTitle"></el-input>
        </el-form-item>
        <!-- <el-form-item label="时间">
          <el-date-picker v-model="formLabelAlign.time" type="date" placeholder="请选择日期">
          </el-date-picker>
        </el-form-item> -->
        <el-form-item label="备注">
          <el-input v-model="formLabelAlign.comment"></el-input>
        </el-form-item>
      </el-form>
     <el-button type="primary" @click="upoint" style="margin-right:20px;float:right;">提交</el-button>
   </el-drawer>
  </el-row>
</el-drawer>
  </div>
</div>
</template>
<script>
// import BaiduMap from 'vue-baidu-map'
import { lazyBMapApiLoaderInstance } from 'vue-bmap-gl'
export default {
  data () {
    return {
      map: null,
      center: { lng: 116.404, lat: 39.915 },
      zoom: 15,
      location: { ptitle: '', lng: null, lat: null, color: '' }, // 当前坐标的经纬度
      Ponits: [], // 当前路径（地名，经纬度，颜色）,后续自行添加color
      isOpen: false,
      address: '', // 当前搜索的地名
      b: '',
      c: '',
      pcolors: ['#409EFF', '#67C23A', '#FFA500', '#FBBEBC', '#DBF3F4', '#FEF3D2'],
      points: [], // 当前路径添加随机颜色后的数组
      activities: [],
      route: {},
      innerDrawer: false,
      labelPosition: 'right',
      formLabelAlign: {
        routeTitle: '',
        // time: '',
        comment: ''
      }
    }
  },
  methods: {
    openfn () {
      this.isOpen = !this.isOpen
    },
    initPoint () {
      this.points = this.Ponits.map((value, index, array) => ({ ptitle: array[index].ptitle, timestamp: array[index].lng + ',' + array[index].lat, color: this.pcolors[index] }))
      return this.points
    },
    clrpoint () {
      this.points = []
      this.Ponits = []
      this.map.clearOverlays()
    },
    NavigationControl () {
      // 添加控件
      const scaleCtrl = new BMapGL.ScaleControl() // 添加比例尺控件
      this.map.addControl(scaleCtrl)
      const zoomCtrl = new BMapGL.ZoomControl() // 添加缩放控件
      this.map.addControl(zoomCtrl)
      const cityCtrl = new BMapGL.CityListControl() // 添加城市列表控件
      this.map.addControl(cityCtrl)
      this.map.clearOverlays()
    },
    initMap () {
      lazyBMapApiLoaderInstance.load().then(() => {
        const GL = BMapGL
        this.map = new GL.Map('container')
        this.map.centerAndZoom(new GL.Point(this.center.lng, this.center.lat), this.zoom) // 设置中心点和缩放级别
        this.map.enableScrollWheelZoom(true) // 允许鼠标滚轮缩放
        this.NavigationControl() // 添加控件函数
      })
    },
    // 发起检索
    searchLocation () {
      this.address = document.getElementById('cityName').value
      if (this.address !== '') {
        const local = new BMapGL.LocalSearch(this.map, {
          renderOptions: { map: this.map }
        })
        local.search(this.address)
      } else {
        alert('搜索失败，检索地点不能为空')
      }
    },
    sendData () {
      const that = this
      // 为地图增加点击事件，为input赋值
      this.map.addEventListener('click', function (e) {
        console.log(e)
        document.getElementById('lat').value = e.latlng.lat
        document.getElementById('lng').value = e.latlng.lng
        that.b = document.getElementById('lat').value
        that.c = document.getElementById('lng').value
        that.location.lat = that.b
        that.location.lng = that.c
      })
    },
    submit () {
      const that = this
      const obj = {
        ptitle: this.address,
        lng: (this.c * 1).toFixed(6), // string->double(保留6位小数)
        lat: (this.b * 1).toFixed(6)
      }
      that.Ponits.push(obj)
      that.initPoint()
      // 清除input中的值
      document.getElementById('lat').value = null
      document.getElementById('lng').value = null
      that.map.clearOverlays() // 上传位置后取消上一次搜索的标记
    },
    updown () {
      const that = this
      let PonitData = []
      PonitData = this.Ponits
      // 两个点连线的集合，每次连完线之后，留下后一个
      let point = []
      // 考虑极端情况：如果传入为空或者只有一个点
      if (PonitData.length <= 1) {
        alert('数量不足无法路线规划，请先搜索')
      } else {
        // 在地图上显示每一个标记点
        for (const key in PonitData) {
          // 输出每一个点经纬度的集合
          const everyPoint = new BMapGL.Point(PonitData[key].lng, PonitData[key].lat)
          const marker = new BMapGL.Marker(everyPoint)
          this.map.addOverlay(marker)
          let label
          // 起点
          if (key == 0) {
            label = new BMapGL.Label('起点', { position: everyPoint })
            this.map.addOverlay(label)
            continue
          }
          // 终点
          if (key == PonitData.length - 1) {
            label = new BMapGL.Label('终点', { position: everyPoint })
            this.map.addOverlay(label)
            continue
          }
          // 途径点
          label = new BMapGL.Label('途径点' + key, { position: everyPoint })
          this.map.addOverlay(label)
        }
        // 规划路线
        let driving = new BMapGL.DrivingRoute(this.map, { renderOptions: { map: this.map, autoViewport: true } })
        if (PonitData.length === 2) {
          const start = new BMapGL.Point(PonitData[0].lng, PonitData[0].lat)
          const end = new BMapGL.Point(PonitData[1].lng, PonitData[1].lat)
          driving.search(start, end)
        } else {
          driving = new BMapGL.DrivingRoute(this.map)
          // const LastIndex = PonitData.length - 1
          const start = new BMapGL.Point(PonitData[0].lng, PonitData[0].lat)
          // const end = new BMapGL.Point(PonitData[LastIndex][0], PonitData[LastIndex][1])
          for (let i = 1; i <= PonitData.length - 1; i++) {
            const pass = new BMapGL.Point(PonitData[i].lng, PonitData[i].lat)
            if (i === 1) {
              driving.search(start, pass)
              point = []
              point.push(pass)
              continue
            }
            driving.search(point[0], pass)
            point = []
            point.push(pass)
          }
          driving.setSearchCompleteCallback(function () {
            const pts = driving.getResults().getPlan(0).getRoute(0).getPath() // 通过驾车实例，获得一系列点的数组
            const polyline = new BMapGL.Polyline(pts, {
              strokeColor: '#409EFF',
              strokeWeight: 4,
              strokeOpacity: 1
            })
            that.map.addOverlay(polyline)
          })
        }
      }
    },
    getRouteData (mapform, userId, routeList) {
      let path = '' // 路线路径
      let place = '' // 路线各点经纬度
      routeList.map((item, index) => {
        if (index == routeList.length - 1) { path = path + item.ptitle } else { path = path + item.ptitle + '=>' }
        if (index == routeList.length - 1) { place = place + item.timestamp } else { place = place + item.timestamp + '|' }
      })
      this.route.routePath = path
      // routeData.append('routePath', path)
      this.route.routeLocation = place
      // routeData.append('routeLocation', place)
      Object.keys(mapform).forEach((item, index) => {
        if (index == 0) {
          // routeData.append('routeTitle', mapform[item])
          this.route.routeTitle = mapform[item]
        } else {
          // routeData.append('routeComment', mapform[item])
          this.route.routeComment = mapform[item]
        }
      })
      // this.route.push(routeList)
      this.route.createUser = userId
      // routeData.append('createUser', userId)
      return this.route
    },
    async upoint () {
      this.$refs.mapRef.validate(async valid => {
        if (!valid) return
        const userStr = localStorage.getItem('vuex') || '{}'
        const userId = JSON.parse(userStr).userInfo.id
        this.getRouteData(this.formLabelAlign, userId, this.points)
        // const { data: res } = await routeAPI(this.points, userId, this.formLabelAlign)
        const axios = require('axios')
        axios.post('/map/route',
          {
            routePath: this.route.routePath,
            routeLocation: this.route.routeLocation,
            routeTitle: this.route.routeTitle,
            createUser: this.route.createUser,
            routeComment: this.route.routeComment
          }
        )
          .then(res => {
            if (res.data == 1) alert('添加成功')
          }).catch(res => {
            alert('添加失败')
          })
      })
    }
  },
  mounted () {
    this.initMap()
  },
  watch () {
    this.sendData()
  }
}
</script>
<style lang="less" scoped>
.root {
  display:flex;
  height: 100%;
}
.map {
  width: 100%;
  height: 100%;
  flex: 499;
  float: left;
  z-index: 1;
}

.drawer {
  width: 10px;
  height: 100%;
  flex: 1;
  z-index: 101;
  float: left;
}
input{
  border: 0; // 去除未选中状态边框
  outline: none; // 去除选中状态边框
  background-color: rgba(0, 0, 0, 0);// 透明背景
}
.el-row{
  line-height: 20px;
  margin-bottom: 20px;
}
.el-col{
  text-align: center;
}
.el-timeline{
  margin-left: 70px;
}
.Anchor_fixmain{
    position:absolute;
    top: 60px;
    right: 0;
    z-index: 101;
    border-right: none;
    width: 5px;
    height: 10%;
  //  -webkit-box-sizing: border-box;
  //   -moz-box-sizing: border-box;
  //    box-sizing: border-box;
    // background:red;
}
.btnlr_box{
    position: absolute;
    top: 50%;
    left: -10px;
    z-index: 101;
    margin-top: 200px;
    background-color: rgba(0,0,0,0.5);
    -webkit-border-radius: 3px 0 0 3px;
    -moz-border-radius:  3px 0 0 3px;
    border-radius:  3px 0 0 3px;
    width: 15px;
    height: 80px;
    font-size: 12px;
    color: #FFFFFF;
    line-height: 80px;
}
</style>
