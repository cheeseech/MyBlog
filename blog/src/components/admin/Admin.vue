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

        <el-main :style="height">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
const sideBar = () =>import(/* webpackChunkName:"admin_home" */ "@/components/admin/SideBar");
const navBar = () =>import(/* webpackChunkName:"admin_home" */ "@/components/admin/NavBar");
const userManager = () =>import(/* webpackChunkName:"admin_user" */ "@/components/admin/UserManager");

export default {
  name: "Admin",
  data() {
    return {
      height: {
        height: window.innerHeight - 60 + "px"
      }
    };
  },
  components: {
    sideBar: sideBar,
    navBar: navBar,
    userManager: userManager
  }
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
