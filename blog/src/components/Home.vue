<template>
  <div id="Home">
    <el-container>
      <el-header>
        <!--导航栏-->
        <blogNav></blogNav>
      </el-header>

      <el-main>
        <div class="m-container-small">
          <!--路由渲染页面-->
          <!--          <transition>-->
          <router-view></router-view>
          <!--          </transition>-->
        </div>
      </el-main>

      <el-footer>
        <!--底部footer-->
        <blogFooter></blogFooter>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
const blogNav = () =>import(/* webpackChunkName: "blog_home" */ "@/components/BlogNav");
const blogFooter = () =>import(/* webpackChunkName:"blog_home" */ "@/components/BlogFooter");

export default {
  name: "Home",
  components: {
    blogNav: blogNav,
    blogFooter: blogFooter
  }
};
</script>

<style>
.el-header {
  padding: 0;
  background-color: #336699;
}
.el-footer {
  background-color: #303133;
  height: auto !important;
  margin-top: 1.5em;
}
.el-main {
  margin-top: 1.5em;
}
.el-col {
  border-radius: 4px;
}
.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}
.clearfix:after {
  clear: both;
}
.item {
  margin-bottom: 18px;
}
a {
  text-decoration: none;
}
li {
  list-style-type: none;
}
</style>
