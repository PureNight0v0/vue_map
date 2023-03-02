const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭eslint校验
  lintOnSave: false,
  devServer: {
    port: 8080,
    host: 'localhost',
    https: false,
    proxy: {
      '/api': {
        target: 'http://localhost:8899', // 后端接口
        ws: true,
        changeOrigin: true, // 是否跨域
        pathRewrite: {
          '^/api': ''
        }
      }
    }
  }
})
