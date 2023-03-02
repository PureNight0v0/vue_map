const ak = 'HmzC8By96LGSIrYc3uydiev7eajXfCph' // 百度的地图密钥
/**
 * 异步加载百度地图
 * @returns {Promise}
 * @constructor
 */
function loadBaiDuMap () {
  return new Promise(function (resolve, reject) {
    try {
      console.log('BMap is defined:', BMapGL === undefined || BMapGL)
      resolve(BMapGL)
    } catch (err) {
      window.init = function () {
        resolve(BMapGL)
      }
      const script = document.createElement('script')
      script.type = 'text/javascript'
      script.src = `http://api.map.baidu.com/api?v=1.0&type=webgl&ak=${ak}&callback=init`
      script.onerror = reject
      document.body.appendChild(script)
    }
  })
}
export { loadBaiDuMap }
/**
 * 异步加载百度地图,以及绘制工具
 * @returns {Promise}
 * @constructor
 */
function loadBaiDuDrawMap () {
  return loadBaiDuMap().then(BMapGL => {
    let loaded = false
    try {
      loaded = (BMapGLLib && BMapGLLib.DrawingManager)
    } catch (err) {
      loaded = false
    }
    if (!loaded) {
      console.log('BMapLib.RichMarker loading!')
      const script = document.createElement('script')
      script.type = 'text/javascript'
      script.src = 'http://mapopen.bj.bcebos.com/github/BMapGLLib/RichMarker/src/RichMarker.min.js'
      document.body.appendChild(script)
      const link = document.createElement('link')
      link.rel = 'stylesheet'
      link.href = 'http://mapopen.bj.bcebos.com/github/BMapGLLib/RichMarker/src/RichMarker.min.js'
      document.body.appendChild(link)
    } else {
      console.log('BMapLib.RichMarker is loaded!')
    }
    return BMapGL
  })
}
export { loadBaiDuDrawMap }
