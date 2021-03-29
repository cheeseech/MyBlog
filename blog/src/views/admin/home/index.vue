<!--
 * @FileDescription: 后台管理主组件
 * @Author: 徐茂华
 * @Date: 2020-08-27 20:36:17
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-18 15:52:36
 * @FilePath: \src\views\admin\home\index.vue
-->
<template>
  <div id="admin">
    <el-container>
      <!-- 侧边栏 -->
      <el-aside style="width:auto;">
        <sideBar></sideBar>
      </el-aside>

      <el-container>
        <!-- 导航栏 -->
        <el-header>
          <navBar></navBar>
        </el-header>

        <!-- 路由组件 -->
        <el-main :style="height">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
const sideBar = () =>
  import(
    /* webpackChunkName:"admin_home" */ "@/views/admin/home/components/SideBar"
  );
const navBar = () =>
  import(
    /* webpackChunkName:"admin_home" */ "@/views/admin/home/components/NavBar"
  );

export default {
  name: "Admin",
  data() {
    return {
      height: {
        height: window.innerHeight - 60 + "px" // 获取当前窗口 - 60px长度
      }
    };
  },
  components: { sideBar, navBar }
};
</script>

<style>
.el-header {
  padding: 0;
}
.el-main {
  margin: 0;
}
</style>
