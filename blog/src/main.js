// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import VueProgressBar from 'vue-progressbar'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import '@/assets/icon/iconfont.js'
import '@/assets/css/me.css'

Vue.config.productionTip = false
Vue.use(ElementUI);

//使用eventBus架起兄弟之间通讯的桥梁
export const eventBus=new Vue();

const options = {
  color: '#bffaf3',
  failedColor: '#874b4b',
  thickness: '5px',
  transition: {
    speed: '0.2s',
    opacity: '0.6s',
    termination: 300
  },
  autoRevert: true,
  location: 'left',
  inverse: false
}

Vue.use(VueProgressBar, options)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

router.beforeEach((to,from,next)=>{
  //如果路由 meta中loginRequest为true，进行拦截
  if(to.meta.loginRequest){
    if(localStorage.getItem('user')){
      next()
    }else{
      next({
        path:'/doLogin',
        query: {
          redirect:to.fullPath
        }
      })
    }
  }else{
    //直接进入页面
    next()
  }
})
