<template>
  <div id="navBar">
    <el-menu class="el-menu-demo" mode="horizontal" active-text-color="#fff">
      <el-menu-item index="1" style="padding:0">
        <el-button
          @click="sendCollapse()"
          icon="el-icon-delete"
          style="height:100%;border: 0;"
        ></el-button>
      </el-menu-item>
      <el-menu-item index="2">
        <el-breadcrumb
          separator-class="el-icon-arrow-right"
          style="line-height: 60px;"
        >
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>活动管理</el-breadcrumb-item>
          <el-breadcrumb-item>活动列表</el-breadcrumb-item>
          <el-breadcrumb-item>活动详情</el-breadcrumb-item>
        </el-breadcrumb>
      </el-menu-item>
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
import { eventBus } from "../../main";

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
</style>
