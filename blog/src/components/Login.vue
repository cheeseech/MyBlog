<template>
  <div id="login">
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">系统登录</h3>
      <el-form-item>
        <el-input
          type="text"
          v-model="loginForm.username"
          placeholder="账号"
        ></el-input>
      </el-form-item>

      <el-form-item>
        <el-input
          type="password"
          v-model="loginForm.password"
          placeholder="密码"
        ></el-input>
      </el-form-item>

      <el-form-item style="width: 100%">
        记住我注册
        <el-button
          type="primary"
          style="width: 100%;background: #505458;border: none"
          @click.native.prevent="login"
          >登录</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { postRequest } from "../../untils/axiosApi";
import { Message } from "element-ui";

export default {
  name: "Login",
  data() {
    return {
      bgImg: require("../assets/images/content.jpg"),
      loginForm: {
        username: "",
        password: ""
      },
      responseResult: []
    };
  },
  methods: {
    login() {
      var _this = this;
      postRequest("/login", {
        username: _this.loginForm.username,
        password: _this.loginForm.password
      }).then(response => {
        if (response != undefined && response.status == 200) {
          Message.success(response.msg);
          _this.$router.push({ path: "/admin/welcome" });
        } else {
          if (response != undefined) {
            Message.error(response.msg);
          }
        }
      });
    }
  }
};
</script>

<style>
#login {
  background: url("../assets/images/content.jpg") no-repeat;
  background-position: center;
  height: 100%;
  width: 100%;
  background-size: cover;
  position: fixed;
}

.login-container {
  border-radius: 15px;
  background-clip: padding-box;
  margin: 150px auto;
  width: 350px;
  padding: 35px 35px 15px 35px;
  background: #fff;
  border: 1px solid #eaeaea;
  box-shadow: 0 0 5px #cac6c6;
}

.login_title {
  margin: 0px auto 40px auto;
  text-align: center;
  color: #505458;
}
</style>
