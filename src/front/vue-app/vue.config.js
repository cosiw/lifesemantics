const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
const target = 'http://localhost:8082'
module.exports = {
  devServer: {
      port:8082,
      proxy: {
          '/api':{
              target,
              changeOrigin: true
          }
      }
  }
}