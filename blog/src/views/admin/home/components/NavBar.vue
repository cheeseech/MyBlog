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
      
      <!-- 头像 -->
      <el-submenu index="2" style="float: right;">
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
      collapse: true
    };
  },
  methods: {
    //   切换侧边栏状态
    sendCollapse() {
      this.collapse = !this.collapse;
      eventBus.$emit("collapse", this.collapse);
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
