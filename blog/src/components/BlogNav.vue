<template>
  <nav class="m-container">
    <el-menu
      :default-active="activeIndex"
      router
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#336699"
      text-color="white"
      active-text-color="#ffd04b"
    >
      <!--logo-->
      <el-menu-item>
        <el-image :src="logoUrl" fit="fill" class="m-logo-size"></el-image>
      </el-menu-item>

      <el-menu-item index="/index">
        <svg class="icon m-svg-size" aria-hidden="true">
          <use xlink:href="#icon-shouye"></use>
        </svg>
        首页
      </el-menu-item>

      <el-menu-item index="/category">
        <svg class="icon m-svg-size" aria-hidden="true">
          <use xlink:href="#icon-icon-"></use>
        </svg>
        专栏
      </el-menu-item>

      <el-menu-item index="/tags">
        <svg class="icon m-svg-size" aria-hidden="true">
          <use xlink:href="#icon-icon"></use>
        </svg>
        标签
      </el-menu-item>

      <el-menu-item index="/archives">
        <svg class="icon m-svg-size" aria-hidden="true">
          <use xlink:href="#icon-shijian01"></use>
        </svg>
        归档
      </el-menu-item>

      <el-menu-item index="/about">
        <svg class="icon m-svg-size" aria-hidden="true">
          <use xlink:href="#icon-guanyuwo"></use>
        </svg>
        关于我
      </el-menu-item>

      <!--登录按钮 or 头像-->
      <el-menu-item style="float: right">
        <el-button type="primary" @click="dialogFormVisible = true"
          >登录</el-button
        >
      </el-menu-item>

      <!--登录页面-->
      <el-dialog title="登录页面" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="邮箱：" :label-width="formLabelWidth">
            <el-input
              v-model="form.name"
              autocomplete="off"
              class="m-input"
            ></el-input>
          </el-form-item>
          <el-form-item label="密码：" :label-width="formLabelWidth">
            <el-input
              v-model="form.name"
              autocomplete="off"
              class="m-input"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-link type="danger" :underline="false">还未注册？去注册</el-link>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false"
            >确 定</el-button
          >
        </div>
      </el-dialog>

      <!--搜索框-->
      <el-menu-item style="float: right">
        <el-input
          placeholder="请输入内容"
          v-model="input"
          class="input-with-select"
        >
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="sendInput"
          ></el-button>
        </el-input>
      </el-menu-item>
    </el-menu>
  </nav>
</template>

<script>
import { eventBus } from "@/main";

export default {
  name: "NavMenu",
  data() {
    return {
      activeIndex: "/index",
      input: "",
      dialogFormVisible: false,
      logoUrl: require("./../assets/images/logo.png"),
      form: {
        name: "",
        region: "",
        date1: "",
        date2: "",
        delivery: false,
        type: [],
        resource: "",
        desc: ""
      },
      formLabelWidth: "80px"
    };
  },
  watch: {
    //监听路由变化切换导航
    $route(to, from) {
      this.activeIndex = to.path;
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      // console.log(key, keyPath);
    },
    sendInput() {
      eventBus.$emit("title", this.input);
    }
  }
};
</script>

<style scoped>
/*logo尺寸*/
.m-logo-size {
  width: 40px;
  height: 40px;
}

/*自定义dialog样式*/
.el-dialog {
  margin: 0 !important;
  display: flex;
  flex-direction: column;
  max-height: calc(100% - 30px);
  max-width: calc(100% - 30px);
  border-radius: 20px;
  background-color: #f4f4f4;
  font-weight: bold;
  width: 23%;
}
.el-dialog__body {
  transform: translate3d(0, 0, 0);
  overflow: auto;
}
.el-dialog__header {
  padding: 20px 20px 20px 20px !important;
  border-bottom: 1px solid #f4f4f4;
}
.el-dialog__footer {
  text-align: center !important;
  padding: 12px 20px 12px 20px !important;
  border-top: 1px solid #f4f4f4;
}
.el-dialog__headerbtn {
  position: absolute;
  top: 20px;
  right: 20px;
  padding: 0;
  background: 0 0;
  border: none;
  outline: 0;
  cursor: pointer;
  font-size: 24px !important;
}
.el-dialog__wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}
.m-input .el-input__inner {
  border-radius: 10px;
  width: auto;
}
</style>
