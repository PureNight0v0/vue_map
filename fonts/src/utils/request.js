import axios from 'axios'
import store from '@/store'
import router from '@/router'
// 创建一个自定的axios方法(比原axios多了个基地址)
// axios函数请求的url地址前面会被拼接基地址, 然后axios请求baseURL+url后台完整地址
const myAxios = axios.create({
  baseURL: '/api',
  timeout: 5000
})
// 定义请求拦截器
myAxios.interceptors.request.use(function (config) {
  // 为请求头挂载 Authorization 字段
  if (store.state.token)config.headers.Authorization = store.state.token
  // config.headers['Content-Type'] = 'text/plain'
  return config
}, function (error) {
  return Promise.reject(error)
})
// 定义响应拦截器
myAxios.interceptors.response.use(function (response) {
  // 响应状态码为 2xx 时触发成功的回调，形参中的 response 是“成功的结果”
  return response
}, function (error) {
  // 响应状态码不是 2xx 时触发失败的回调，形参中的 error 是“失败的结果”
  if (error.response.status === 401) {
    // 无效的 token
    // 把 Vuex 中的 token 重置为空，并跳转到登录页面
    store.commit('updateToken', '')
    router.push('/login')
  }
  return Promise.reject(error)
})
// 导出自定义的axios方法, 供外面调用传参发请求
export default myAxios
