import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import '@/assets/global.less' // 全局初始化样式
import '@/elementUI' // 注册elementUI组件,分散管理, 便于查找
// import BaiduMap from 'vue-baidu-map'
import VueBMap from 'vue-bmap-gl'
import 'vue-bmap-gl/dist/style.css'

Vue.use(VueBMap)
VueBMap.initBMapApiLoader({
  // 百度的key
  ak: 'HmzC8By96LGSIrYc3uydiev7eajXfCph',
  // 百度 sdk 版本，默认为 1.0
  v: '1.0'
})
Vue.prototype.$axios = axios
axios.defaults.baseURL = '/api'
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
