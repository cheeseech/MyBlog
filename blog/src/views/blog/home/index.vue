<template>
  <div id="index">
    <el-container>
      <el-header>
        <!--导航栏-->
        <homeNav class="navBarWrap"></homeNav>
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
            class="icon go-top"
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
      scrollTop: 0,
      btnFlag: false
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
    // 点击图片回到顶部方法，加计时器是为了过渡顺滑
    backTop() {
      const _this = this;
      let timer = setInterval(() => {
        let ispeed = Math.floor(-_this.scrollTop / 5);
        document.documentElement.scrollTop = document.body.scrollTop =
          _this.scrollTop + ispeed;
        if (_this.scrollTop === 0) {
          clearInterval(timer);
        }
      }, 16);
    },
    // 为了计算距离顶部的高度，当高度大于60显示回顶部图标，小于60则隐藏
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
.navBarWrap {
  position: fixed;
  top: 0;
  z-index: 999;
  width: 100%;
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
.go-top {
  font-size: 70px;
  position: fixed;
  bottom: 5%;
  right: 5%;
}
</style>
