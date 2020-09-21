<template>
  <!-- <nav class="m-container"> -->
  <el-menu
    :default-active="activeIndex"
    router
    class="el-menu-demo"
    mode="horizontal"
    background-color="#336699"
    text-color="white"
    active-text-color="#ffd04b"
  >
    <!--logo-->
    <el-menu-item>
      <el-image :src="logoUrl" fit="fill" class="m-logo-size"></el-image>
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
    <el-menu-item style="float: right; width: 17%; margin-right: 40px">
      <el-input
        placeholder="请输入内容"
        v-model="input"
        class="input-with-select m-search-input m-search-btn"
      >
        <el-button slot="append" icon="el-icon-search"></el-button>
      </el-input>
    </el-menu-item>
  </el-menu>
  <!-- </nav> -->
</template>

<script>
import { eventBus } from "@/main";

export default {
  name: "NavMenu",
  data() {
    return {
      activeIndex: "/index",
      input: "",
      logoUrl: require("./../assets/images/logo.png"),
    };
  },
  watch: {
    //监听路由变化切换导航
    $route(to, from) {
      this.activeIndex = to.path;
    },
  },
  methods: {
    //发送搜索关键字到BlogArticle
    sendInput() {
      eventBus.$emit("title", this.input);
    },
  },
};
</script>
<style>
/*logo尺寸*/
.m-logo-size {
  width: 50px;
  height: 50px;
}
/* 导航栏搜索框 */
.m-search {
  float: right;
  width: 17%;
  margin-right: 40px;
}
</style>
