<template>
  <div id="sideBar">
    <el-menu
      default-active="1-4-1"
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
        <i class="el-icon-document"></i>
        <span slot="title">专栏管理</span>
      </el-menu-item>
      <!-- 文章管理 -->
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
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
      isCollapse: true
    };
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
