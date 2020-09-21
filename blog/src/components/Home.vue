<template>
  <div id="Home">
    <el-container>
      <el-header>
        <!--导航栏-->
        <blogNav class="navBarWrap"></blogNav>
      </el-header>

      <el-main>
        <vue-progress-bar></vue-progress-bar>
        <div class="m-container-small">
          <!--路由渲染页面-->
          <transition name="el-fade-in-linear">
            <router-view></router-view>
          </transition>
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
const blogNav = () =>
  import(/* webpackChunkName: "blog_home" */ "@/components/BlogNav");
const blogFooter = () =>
  import(/* webpackChunkName:"blog_home" */ "@/components/BlogFooter");

export default {
  name: "Home",
  components: {
    blogNav: blogNav,
    blogFooter: blogFooter,
  },
  mounted() {
    //  [App.vue specific] 当App.vue加载完成，结束进度条
    this.$Progress.finish();
  },
  created() {
    //  [App.vue specific] 当App.vue创建时，启动进度条
    this.$Progress.start();
    //  当路由变化时，挂起进度条
    this.$router.beforeEach((to, from, next) => {
      //  判断页面是否存在 meta.progress
      if (to.meta.progress !== undefined) {
        let meta = to.meta.progress;
        // parse meta tags
        this.$Progress.parseMeta(meta);
      }
      // 启动进度条
      this.$Progress.start();
      //  continue to next page
      next();
    });
    //  路由完成时，完成进度条
    this.$router.afterEach((to, from) => {
      //  finish the progress bar
      this.$Progress.finish();
    });
  }
};
</script>

<style>
.navBarWrap {
    position:fixed;
    top:0;
    z-index:999;
    width:100%;
  }

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
