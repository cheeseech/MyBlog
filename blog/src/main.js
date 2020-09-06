// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router'
import '@/assets/icon/iconfont.js'
import '@/assets/css/me.css'

Vue.config.productionTip = false
Vue.use(ElementUI);

//使用eventBus架起兄弟之间通讯的桥梁
export const eventBus=new Vue();

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
