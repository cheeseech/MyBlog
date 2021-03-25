// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import VueProgressBar from "vue-progressbar";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import router from "./router";
import "@/assets/icon/iconfont.js";
import "@/assets/css/me.css";
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
import echarts from 'echarts'
import * as filters from '@/common/filters' // global filters
import common from '@/common/common' // global function
// import highlight from 'highlight'

// register global utility filters.
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

Vue.prototype.common=common;

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.$echarts=echarts;

//使用eventBus架起兄弟之间通讯的桥梁
export const eventBus = new Vue();

const options = {
  color: "#ffd04b",
  failedColor: "#874b4b",
  thickness: "4px",
  transition: {
    speed: "0.3s",
    opacity: "0.7s",
    termination: 300
  },
  autoRevert: true,
  location: "top",
  inverse: false
};

Vue.use(mavonEditor);
Vue.use(VueProgressBar, options);

/* eslint-disable no-new */
new Vue({
  el: "#app",
  router,
  components: { App },
  template: "<App/>"
});

//在main.js定义自定义指令 
Vue.directive('highlight',function (el) {
  let blocks = el.querySelectorAll('pre code');
  blocks.forEach((block)=>{
    highlight.highlightBlock(block)
  })
})
