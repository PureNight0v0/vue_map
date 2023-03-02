import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('@/views/layout'),
    redirect: '/map',
    children: [{
      path: 'map',
      component: () => import('@/views/map')
    },
    {
      path: 'spot',
      component: () => import('@/views/spot')
    },
    {
      path: 'data',
      component: () => import('@/views/data')
    },
    {
      path: 'foot',
      component: () => import('@/views/foot')
    },
    {
      path: 'way',
      component: () => import('@/views/way')
    },
    {
      path: 'user',
      component: () => import('@/views/user')
    },
    {
      path: 'role',
      component: () => import('@/views/role')
    }
    ]
  },
  {
    path: '/reg',
    component: () => import('@/views/register')
    // webpack提供import函数来路由懒加载导入组件
    // 路由懒加载：页面路由路径切换到/reg才加载对应组件代码（加载文件体积更小，打开更快）
  },
  {
    path: '/login',
    component: () => import('@/views/login')
  }
]

const router = new VueRouter({
  routes
})

// 全局前置路由守卫
// 1.浏览器首次打开项目会触发全局前置守卫函数
// 2.判断登录与否：有token就证明登录
// 3.next()强制切换路由地址，会走路由守卫
//  在主页删除本地的vuex数据, 刷新页面让vuex取出来空的token, 但是发现递归了
//  原因: 因为强制跳转到登录页也会让路由守卫再次触发, 又判断无token, 再次跳转登录页, 就递归了
//  解决: 登录页面应该是无需判断token的, 还有注册页面, 所以设置白名单, 无token要去这2个页面直接放行
// 无需验证token的页面
const whiteList = ['/login', '/reg']

router.beforeEach((to, from, next) => {
  const token = store.state.token
  if (token) {
    // 如果有token, 证明已登录
    if (!store.state.userInfo.username) {
      // 有token但是没有用户信息, 才去请求用户信息保存到vuex里
      // 调用actions里方法请求数据
      store.dispatch('initUserInfo')
      // 下次切换页面vuex里有用户信息数据就不会重复请求用户信息
    }
    next() // 路由放行
  } else {
    // includes 数组的方法
    if (whiteList.includes(to.path)) {
      next()
    } else {
      // 如果其他页面请强制拦截并跳转到登录页面
      next('/login')
    }
  }
})

export default router
