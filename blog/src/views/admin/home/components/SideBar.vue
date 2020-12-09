<template>
  <div id="sideBar">
    <el-menu
      :default-active="activePath"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409eff"
      router
    >
      <!--欢迎页  -->
      <el-menu-item index="/admin/welcome">
        <i class="el-icon-menu"></i>
        <span slot="title">欢迎页</span>
      </el-menu-item>
      <!-- 用户管理 -->
      <el-menu-item index="/admin/user">
        <i class="el-icon-menu"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <!-- 标签管理 -->
      <el-menu-item index="/admin/tags">
        <i class="el-icon-menu"></i>
        <span slot="title">标签管理</span>
      </el-menu-item>
      <!-- 专栏管理 -->
      <el-menu-item index="/admin/category">
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-book-2"></use>
        </svg>
        <span slot="title">专栏管理</span>
      </el-menu-item>
      <!-- 文章管理 -->
      <el-submenu index="1">
        <template slot="title">
          <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
            <use xlink:href="#icon-shuqian"></use>
          </svg>
          <span>文章管理</span>
        </template>
        <el-menu-item index="/admin/publishArticle">发布文章</el-menu-item>
        <el-menu-item index="/admin/article">管理文章</el-menu-item>
        <el-menu-item index="/admin/recycle">回收站</el-menu-item>
      </el-submenu>
      <!-- 数据统计 -->
      <el-menu-item index="/admin/adminData">
        <i class="el-icon-setting"></i>
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
      activePath: "/admin/welcome",
      isCollapse: true
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
    //   接受并设置侧边栏状态
    eventBus.$on("collapse", message => {
      this.isCollapse = message;
    });
  }
};
</script>

<style scoped>
#sideBar {
  height: 100%;
  background-color: #304156;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
.el-menu {
  border-right: 0;
}
</style>
