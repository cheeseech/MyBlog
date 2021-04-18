<!--
 * @FileDescription: 博客标题栏组件
 * @Author: 徐茂华
 * @Date: 2020-08-06 09:54:27
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-03 10:57:05
 * @FilePath: \src\views\blog\home\components\HomeNav.vue
-->
<template>
  <el-menu
    :default-active="activeIndex"
    router
    class="el-menu-demo m-menu"
    mode="horizontal"
    background-color="#3f72af"
    text-color="white"
    active-text-color="#ffd04b"
  >
    <!--logo-->
    <el-menu-item index="/" style="border-bottom-color:#3f72af">
      <el-image :src="logoUrl" fit="fill" class="m-logo-size"></el-image>
      <!-- 动态文字-->
      <div class="logo-text">
        <svg viewBox="0 0 1300 200">
          <symbol id="line-text">
            <text text-anchor="middle" x="33%" y="50%" dy=".4em">CHEESE</text>
          </symbol>

          <use xlink:href="#line-text" class="text"></use>
          <use xlink:href="#line-text" class="text"></use>
          <use xlink:href="#line-text" class="text"></use>
          <use xlink:href="#line-text" class="text"></use>
        </svg>
      </div>
    </el-menu-item>

    <el-menu-item index="/index">
      <svg class="icon m-svg-size" aria-hidden="true">
        <use xlink:href="#icon-shouye"></use>
      </svg>
      首页
    </el-menu-item>

    <el-menu-item index="/category">
      <svg class="icon m-svg-size" aria-hidden="true">
        <use xlink:href="#icon-icon-"></use>
      </svg>
      专栏
    </el-menu-item>

    <el-menu-item index="/tags">
      <svg class="icon m-svg-size" aria-hidden="true">
        <use xlink:href="#icon-icon"></use>
      </svg>
      标签
    </el-menu-item>

    <el-menu-item index="/archives">
      <svg class="icon m-svg-size" aria-hidden="true">
        <use xlink:href="#icon-shijian01"></use>
      </svg>
      归档
    </el-menu-item>

    <el-menu-item index="/about">
      <svg class="icon m-svg-size" aria-hidden="true">
        <use xlink:href="#icon-guanyuwo"></use>
      </svg>
      关于我
    </el-menu-item>

    <!--搜索框-->
    <el-menu-item
      style="float: right; width: 17%; margin-right: 3%; border-bottom-color:#3f72af"
    >
      <el-input
        placeholder="输入标题检索"
        v-model="input"
        class="input-with-select m-search-input m-search-btn"
        @keyup.enter.native="sendInput"
      >
        <el-button slot="append" @click="sendInput"
          ><svg class="icon m-svg-size" aria-hidden="true">
            <use xlink:href="#icon-gosearch"></use>
          </svg>
        </el-button>
      </el-input>
    </el-menu-item>
  </el-menu>
</template>

<script>
import { eventBus } from "@/main";

export default {
  name: "NavMenu",
  data() {
    return {
      input: "", // 搜索关键字
      activeIndex: "/index", // 默认选中导航
      logoUrl: "http://images.cheese.host/logo.png" // 导航栏图标
    };
  },
  watch: {
    //监听路由变化切换导航
    $route(to, from) {
      this.activeIndex = to.path;
    }
  },
  created() {
    //兄弟组件传值：清空输入框
    eventBus.$on("cleared", data => {
      this.input = "";
    });
  },
  mounted() {
    let vm = this;
    this.init();
  },
  methods: {
    /**
     * @description: 根据当前路由切换选中导航
     * @return void
     */
    init() {
      let vm = this;
      var path = vm.$route.path;
      if (path !== "/") {
        vm.activeIndex = path;
      }
    },

    /**
     * @description: 发送搜索关键字到当前页面
     * @return void
     */
    sendInput() {
      eventBus.$emit("title", this.input);
    }
  }
};
</script>
<style>
/*logo尺寸*/
.m-logo-size {
  width: 50px;
  height: 50px;
  margin-top: 7px;
  float: left;
}
/* 导航栏搜索框 */
.m-nav-search {
  float: right;
  width: 17%;
  margin-right: 40px;
}
/* 去掉搜索框及图标下划线 */
.remove-underscore {
  border-bottom-color: #3f72af;
}
</style>
