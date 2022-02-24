module.exports = {
  transpileDependencies: ["vuetify"],  // Vue CLI 不会自动带来 IE11 兼容性
  devServer: {
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8080",  // API服务器地址
        changeOrigin: true, // 虚拟的站点需要更管origin
        pathRewrite: {  // 重写路径
          "^/api": ""
        },
        logLevel : "debug"
      }
    },
    disableHostCheck: true
  },
  productionSourceMap: false,
  css: {
    extract: true,
    sourceMap: false
  }
};
