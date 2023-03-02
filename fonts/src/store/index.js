import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'
// import { getUserInfoAPI } from '@/api'
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 1. 用来存储登录成功之后，得到的 token
    token: '',
    userInfo: {} // 定义用户信息
  },
  getters: {
    username: state => state.userInfo.username // 昵称
  },
  mutations: {
    // 2. 更新 token 的 mutation 函数
    updateToken (state, newToken) {
      state.token = newToken
    },
    // 更新用户的信息
    updateUserInfo (state, info) {
      state.userInfo = info
    }
  },
  actions: {
    // 定义初始化用户基本信息的 action 函数
    // async initUserInfo (store) {
    //   const { data: res } = await getUserInfoAPI()
    //   if (res.code === 0) {
    //     store.commit('updateUserInfo', res.data)
    //   }
    // }
  },
  modules: {
  },
  // 配置为 vuex 的插件,本地持久化存储，否则刷新会将空值覆盖原先的值
  plugins: [createPersistedState()]
})
