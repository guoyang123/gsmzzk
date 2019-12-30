import babelpolyfill from 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import routes from './routes'
// import Mock from './mock'
// Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'

Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)


//import  axios from 'axios'

//需要将axios注册成为全局变量

// var service=axios.create({
//
//   baseURL:"http://localhost:8080/",
//   // 请求预处理函数 可以把你传的param进行处理
//   withCredentials: true, // 允许携带cookie
//   transformRequest: [
//     data => {
//       // data 就是你post请求传的值
//       // 一下主要是吧数据拼接成 类似get格式
//       let params = ''
//       for (var index in data) {
//         params += index + '=' + data[index] + '&'
//       }
//       return params
//     }
//   ]
// })

//Vue.prototype.service=service




const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  //NProgress.start();
  if (to.path == '/login') {
    sessionStorage.removeItem('user');
  }
  let user = JSON.parse(sessionStorage.getItem('user'));
  if (!user && to.path != '/login') {
    next({ path: '/login' })
  } else {
    next()
  }
})

//router.afterEach(transition => {
//NProgress.done();
//});

new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

