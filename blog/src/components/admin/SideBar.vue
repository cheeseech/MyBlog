<template>
  <div id="sideBar">
    <!-- <el-radio-group v-model="isCollapse" style="margin-bottom: 20px;">
      <el-radio-button :label="false">展开</el-radio-button>
      <el-radio-button :label="true">收起</el-radio-button>
    </el-radio-group>-->
    <el-menu
      default-active="1-4-1"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409eff"
      router 
    >
      <el-menu-item index="/admin">
        <i class="el-icon-menu"></i>
        <span slot="title">引导页</span>
      </el-menu-item>
      <el-menu-item index="/admin/user">
        <i class="el-icon-menu"></i>
        <span slot="title">用户管理</span>
      </el-menu-item>
      <el-menu-item index="/admin/tags">
        <i class="el-icon-menu"></i>
        <span slot="title">标签管理</span>
      </el-menu-item>
      <el-menu-item index="/admin/category">
        <i class="el-icon-document"></i>
        <span slot="title">专栏管理</span>
      </el-menu-item>
      <el-menu-item index="5">
        <i class="el-icon-setting"></i>
        <span slot="title">数据统计</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import { eventBus } from "../../main";

export default {
  name: "SideBar",
  data() {
    return {
      isCollapse: true
    };
  },
  created() {
    eventBus.$on("collapse", message => {
      this.isCollapse = message;
    });
  },
  monted() {},
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    }
  }
};
</script>

<style scoped>
#sideBar {
  height: 100%;
  background-color: #304156;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
.el-menu {
  border-right: 0;
}
</style>
