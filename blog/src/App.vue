<template>
  <div id="app">
    <vue-progress-bar></vue-progress-bar>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "App",
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

      // 移除加载动画
      var loading = document.getElementById("test");
      if (loading != null) {
        document.body.removeChild(loading);
      }
    });
  }
};
</script>

<style>
#app {
  font-family: "Microsoft YaHei", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  background-color: #f4f4f4;
  text-decoration: none;
}

/* 设置滚动条样式 */
::-webkit-scrollbar-track {
  background: rgba(0, 0, 0, 0.1);
  border-radius: 0;
}
::-webkit-scrollbar {
  -webkit-appearance: none;
  width: 12px;
  height: 10px;
}
::-webkit-scrollbar-thumb {
  cursor: pointer;
  border-radius: 5px;
  /*background: #fd7286;*/
  transition: color 0.2s ease;
  background-image: linear-gradient(#ff9900);
}
</style>
