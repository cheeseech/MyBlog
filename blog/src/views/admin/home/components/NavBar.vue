<template>
  <div id="navBar">
    <el-menu class="el-menu-demo" mode="horizontal" active-text-color="#fff">
      <!-- 切换侧边栏状态 -->
      <el-menu-item index="1" class="sideBar_item">
        <el-button @click="sendCollapse()">
          <svg class="icon m-font-size-big" aria-hidden="true">
            <use xlink:href="#icon-jia1"></use>
          </svg>
        </el-button>
      </el-menu-item>

      <!-- 面包屑 -->
      <el-menu-item>
        <el-breadcrumb
          separator-class="el-icon-arrow-right"
          style="line-height: 60px;"
        >
          <el-breadcrumb-item :to="{ path: '/admin/welcome' }"
            >欢迎页</el-breadcrumb-item
          >
          <el-breadcrumb-item
            v-if="breadList"
            v-for="item in breadList"
            :key="item"
            >{{ item }}</el-breadcrumb-item
          >
        </el-breadcrumb>
      </el-menu-item>

      <!-- 头像 -->
      <el-submenu index="3" style="float: right;">
        <template slot="title">
          <el-avatar shape="square" :size="45" :src="squareUrl"> </el-avatar>
        </template>
        <el-menu-item index="1-4-1">注销</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import { eventBus } from "@/main";

export default {
  name: "NavBar",
  data() {
    return {
      squareUrl:
        "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80",
      collapse: true,
      breadList: null
    };
  },
  watch: {
    //监听路由变化更新面包屑
    $route(to, from) {
      this.getBreadList(to);
    }
  },
  mounted() {
    //设置当前面包屑
    this.getBreadList(this.$route);
  },
  methods: {
    //切换侧边栏状态
    sendCollapse() {
      this.collapse = !this.collapse;
      eventBus.$emit("collapse", this.collapse);
    },
    //根据路由设置面包屑
    getBreadList(to) {
      var name = to.name;
      if (name !== "欢迎页") {
        if (name === "管理文章" || name === "发布文章" || name === "回收站") {
          this.breadList = ["文章管理", name, "文章详情"];
        } else {
          var subName = name.substring(0, 2);
          this.breadList = [name, subName + "详情"];
        }
      } else {
        this.breadList = null;
      }
    }
  }
};
</script>

<style>
#navBar {
  height: 60px;
  background-color: #fff;
}
.sideBar_item {
  padding: 0;
}
.sideBar_item button {
  height: 100%;
  border: 0;
}
</style>
