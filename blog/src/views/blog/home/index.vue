<!--
 * @FileDescription: 博客主组件
 * @Author: 徐茂华
 * @Date: 2020-08-02 15:47:54
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-11 12:00:40
 * @FilePath: \src\views\blog\home\index.vue
-->
<template>
  <div id="index">
    <el-container>
      <el-header>
        <!--导航栏-->
        <transition name="el-fade-in-linear">
          <homeNav class="navBarWrap"></homeNav>
        </transition>
      </el-header>

      <el-main>
        <div class="m-container-small">
          <!--路由渲染页面-->
          <transition name="el-fade-in-linear">
            <router-view></router-view>
          </transition>
        </div>

        <!-- btnFlag 控制图片显示隐藏 -->
        <!-- backTop 回顶部的方法 -->
        <transition name="el-fade-in-linear">
          <svg
            v-if="btnFlag"
            class="icon back-top-position"
            aria-hidden="true"
            @click="backTop"
            style="cursor:hand"
          >
            <use xlink:href="#icon-huojian"></use>
          </svg>
        </transition>
      </el-main>

      <el-footer>
        <!--底部footer-->
        <homeFooter></homeFooter>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
import homeNav from "@/views/blog/home/components/HomeNav";
import homeFooter from "@/views/blog/home/components/HomeFooter";

export default {
  name: "index",
  components: { homeNav, homeFooter },
  data() {
    return {
      scrollTop: 0, //距离顶部的高度
      btnFlag: false // 控制图片显示隐藏
    };
  },
  mounted() {
    // window对象，所有浏览器都支持window对象。它表示浏览器窗口，监听滚动事件
    window.addEventListener("scroll", this.scrollToTop);
  },
  destroyed() {
    window.removeEventListener("scroll", this.scrollToTop);
  },
  methods: {
    /**
     * @description: 点击图片回到顶部方法，加计时器是为了过渡顺滑
     * @return void
     */
    backTop() {
      const _this = this;
      let timer = setInterval(() => {
        let ispeed = Math.floor(-_this.scrollTop / 5);
        document.documentElement.scrollTop = document.body.scrollTop =
          _this.scrollTop + ispeed;
        //距离顶部为零时清除计时器
        if (_this.scrollTop === 0) {
          clearInterval(timer);
        }
      }, 16);
    },

    /**
     * @description: 计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
     * @return void
     */
    scrollToTop() {
      const _this = this;
      let scrollTop =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
      _this.scrollTop = scrollTop;

      if (_this.scrollTop > 60) {
        _this.btnFlag = true;
      } else {
        _this.btnFlag = false;
      }
    }
  }
};
</script>
<style>
/* 固定导航栏位置在顶部 */
.navBarWrap {
  position: fixed;
  top: 0;
  z-index: 999;
  width: 100%;
}

/* 导航栏色彩 */
.el-header {
  padding: 0;
  background-color: #336699;
}
/* 底部色彩及距离 */
.el-footer {
  background-color: #303133;
  height: auto !important;
  margin-top: 1.5em;
}
/* 中间组件距离标题栏距离 */
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

/* 去除a标签下划线，li圆点 */
a {
  text-decoration: none;
}
li {
  list-style-type: none;
}
/* 回到顶部图标位置大小 */
.back-top-position {
  font-size: 70px;
  position: fixed;
  bottom: 5%;
  right: 5%;
}
</style>
