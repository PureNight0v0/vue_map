// 封装具体的接口请求方法
// 每个方法只负责请求一个url地址
import request from '@/utils/request'

/**
 * 注册接口
 * @param {*} param0 { username: 用户名, password: 密码 }
 * @returns Promise对象
 */
export const registerAPI = ({ username, password, repassword, roleId }) => {
  // 这里先用这个接口测试下, 如果url以http开头会忽略baseURL, axios直接请求此地址
  return request({
    url: '/user/register',
    method: 'POST',
    // params拼接到url?后面，data拼接到body请求体里
    data: {
      username,
      password,
      repassword,
      roleId
    }
  })
}
/**
 * 登录接口
 * @param {*} param0 { username: 用户名, password: 密码 }
 * @returns Promise对象
 */
export const loginAPI = ({ username, password }) => {
  return request({
    url: '/user/login',
    method: 'post',
    data: {
      username,
      password
    }
    // headers: { 'Content-Type': 'x-www-form-urlencoded' }
  })
}
/**
 * 地图搜索接口
 * @param {*} param0 { bound：地图搜索范围, zoom：地图缩放尺寸 }
 * @returns Promise对象
 */
export const searchAPI = ({ q }) => {
  return request({
    url: '/spot/search',
    method: 'get',
    params: {
      maxLongitude: q.maxLongitude,
      minLongitude: q.minLongitude,
      maxLatitude: q.maxLatitude,
      minLatitude: q.minLatitude,
      zoom: q.zoom
    }
  })
}
/**
 * 路径上传接口
 * @param {*} param0 { points: ；路径列表, userId: 用户名ID, mapform:其他路径信息 }
 * @returns Promise对象
 */
export const routeAPI = ({ points, userId, mapform }) => {
  // 这里先用这个接口测试下, 如果url以http开头会忽略baseURL, axios直接请求此地址
  return request({
    url: '/spot/route',
    method: 'POST',
    data: {
      points,
      userId,
      mapform
    }
  })
}
