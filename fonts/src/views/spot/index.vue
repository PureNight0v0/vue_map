<template>
<div class="root">
  <div class="map" id="container"></div>
</div>
</template>

<script>
import { loadBaiDuDrawMap } from '@/bmpgl_lib.js'

export default {
  data () {
    return {
      map: null,
      center: { lng: 116.404, lat: 39.915 },
      zoom: 15,
      bound: [],
      spotList: []
    }
  },
  methods: {
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
      loadBaiDuDrawMap().then((BMapGL) => {
        this.map = new BMapGL.Map('container')
        this.map.centerAndZoom(new BMapGL.Point(this.center.lng, this.center.lat), this.zoom)
        this.map.enableScrollWheelZoom(true)
        this.NavigationControl()

        this.map.addEventListener('dragstart', () => { // 拖动开始事件
          this.clearMapMarker(this.map)
        })
        this.map.addEventListener('dragend', () => { // 拖动结束事件
          this.showInfo(this.map)
        })
        this.map.addEventListener('zoomstart', () => { // 缩放开始事件
          this.clearMapMarker(this.map)
        })
        this.map.addEventListener('zoomend', () => { // 缩放结束事件
          this.showInfo(this.map)
        })
        // 初始显示数据
        this.showInfo(this.map)
      })
    },
    showInfo () {
      this.bound = this.map.getBounds() // 可视范围矩形坐标，其中sw表示矩形区域的西南角，参数ne表示矩形区域的东北角
      this.zoom = this.map.getZoom() // 缩放级别
      const q = {
        maxLongitude: this.bound.ne.lng,
        minLongitude: this.bound.sw.lng,
        maxLatitude: this.bound.ne.lat,
        minLatitude: this.bound.sw.lat,
        zoom: this.zoom
      }
      const axios = require('axios')
      axios.get('/spot/search', {
        params: {
          maxLongitude: q.maxLongitude,
          minLongitude: q.minLongitude,
          maxLatitude: q.maxLatitude,
          minLatitude: q.minLatitude,
          zoom: q.zoom
        }
      }).then(res => {
        this.showMapMarker(res.data, this.map)
      }).catch(res => {
        console.error(res)
      })
      // 测试效果：
      // const data = [{ name: '徐汇', price: '1028.43', total: '6584', longitude: 121.43676, latitude: 31.18831 }, { name: '黄浦', price: '1016.19', total: '7374', longitude: 121.49295, latitude: 31.22337 }, { name: '长宁', price: '1008.34', total: '4380', longitude: 121.42462, latitude: 31.22036 }, { name: '静安', price: '1005.34', total: '8077', longitude: 121.4444, latitude: 31.22884 }, { name: '普陀', price: '1026.14', total: '5176', longitude: 121.39703, latitude: 31.24951 }, { name: '金山', price: '1099.67', total: '6', longitude: 121.34164, latitude: 30.74163 }, { name: '松江', price: '1017.71', total: '14', longitude: 121.22879, latitude: 31.03222 }, { name: '青浦', price: '1038.11', total: '751', longitude: 121.12417, latitude: 31.14974 }, { name: '奉贤', price: '1108.63', total: '35', longitude: 121.47412, latitude: 30.9179 }, { name: '浦东', price: '1030.22', total: '8294', longitude: 121.5447, latitude: 31.22249 }, { name: '嘉定', price: '1041.45', total: '1620', longitude: 121.2655, latitude: 31.37473 }, { name: '宝山', price: '1050.65', total: '102', longitude: 121.4891, latitude: 31.4045 }, { name: '闵行', price: '1027.15', total: '941', longitude: 121.38162, latitude: 31.11246 }, { name: '杨浦', price: '1007.78', total: '2747', longitude: 121.526, latitude: 31.2595 }, { name: '虹口', price: '1025.81', total: '4187', longitude: 121.48162, latitude: 31.27788 }]
      this.showMapMarker(this.spotList, this.map)
    },
    // 显示覆盖物
    showMapMarker (data, map) {
      for (const vo of data) {
        const html = '<div class="district">' + vo.spotName + '<span>热度' + vo.hot + '</span><i>' + vo.total + '个</i></div>'
        const marker = new BMapGLLib.RichMarker(html, new BMapGL.Point(vo.lng, vo.lat))
        map.addOverlay(marker)
      }
    },
    // 清除覆盖物
    clearMapMarker (map) {
      const markers = map.getOverlays() // 获取到地图上所有的覆盖物
      for (const marker of markers) { // 循环将其删除
        map.removeOverlay(marker)
      }
    }

  },
  mounted () {
    this.initMap()
  }
}
</script>

<style lang="less" scoped>
.root {
  margin: 0;
  width: 100%;
  height: 100%;
}
.map {
  width: 100%;
  height: 100%;
}
</style>
