<!--
 * @FileDescription: 后台侧边栏组件
 * @Author: 徐茂华
 * @Date: 2020-08-10 20:20:17
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:23:48
 * @FilePath: \src\views\admin\home\components\SideBar.vue
-->
<template>
  <div id="sideBar">
    <el-menu
      :default-active="activePath"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      background-color="#112d4e"
      text-color="#bfcbd9"
      active-text-color="#409eff"
      router
    >
      <!--欢迎页  -->
      <el-menu-item index="/admin/welcome">
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-shouye"></use>
        </svg>
        <span slot="title">欢迎页</span>
      </el-menu-item>

      <!-- 用户管理 -->
      <el-menu-item index="/admin/user">
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-ai-user"></use>
        </svg>
        <span slot="title">用户管理</span>
      </el-menu-item>

      <!-- 标签管理 -->
      <el-menu-item index="/admin/tags">
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-icon"></use>
        </svg>
        <span slot="title">标签管理</span>
      </el-menu-item>

      <!-- 专栏管理 -->
      <el-menu-item index="/admin/category">
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-icon-"></use>
        </svg>
        <span slot="title">专栏管理</span>
      </el-menu-item>

      <!-- 文章管理 -->
      <el-submenu index="1">
        <template slot="title">
          <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
            <use xlink:href="#icon-shu"></use>
          </svg>
          <span>文章管理</span>
        </template>
        <el-menu-item index="/admin/publishArticle">
          <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
            <use xlink:href="#icon-xie"></use>
          </svg>
          发布文章</el-menu-item
        >
        <el-menu-item index="/admin/article">
          <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
            <use xlink:href="#icon-085shucezhongxin"></use>
          </svg>
          管理文章</el-menu-item
        >
        <el-menu-item index="/admin/recycle">
          <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
            <use xlink:href="#icon-huishouzhanlajixiang"></use>
          </svg>
          回收站</el-menu-item
        >
      </el-submenu>

      <!-- 数据统计 -->
      <el-menu-item index="/admin/adminData">
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-shuju"></use>
        </svg>
        <span slot="title">数据统计</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { eventBus } from "@/main";

export default {
  name: "SideBar",
  data() {
    return {
      activePath: "/admin/welcome", // 默认选中路由
      isCollapse: true // 侧边栏是否开启
    };
  },
  watch: {
    //监听路由变化切换导航
    $route(to, from) {
      this.activePath = to.path;
    }
  },
  mounted() {
    //选中当前路由
    var path = this.$route.path;
    this.activePath = path;
  },
  created() {
    // 接受并设置侧边栏状态
    eventBus.$on("collapse", message => {
      this.isCollapse = message;
    });
  }
};
</script>

<style scoped>
#sideBar {
  height: 100%;
  background-color: #112d4e;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
.el-menu {
  border-right: 0;
}
</style>
