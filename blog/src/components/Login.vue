<!--
 * @FileDescription: 登录组件
 * @Author: 徐茂华
 * @Date: 2020-08-29 14:26:00
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-15 17:07:15
 * @FilePath: \src\components\Login.vue
-->
<template>
  <div id="login">
    <el-form class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">欢迎登录博客后台管理系统</h3>
      <!-- 用户名 -->
      <el-form-item>
        <el-input
          id="userName"
          v-focus
          type="text"
          v-model="loginForm.username"
          placeholder="账号"
        ></el-input>
      </el-form-item>
      
      <!-- 密码 -->
      <el-form-item>
        <el-input
          type="password"
          v-model="loginForm.password"
          placeholder="密码"
          @keyup.enter.native="login"
        ></el-input>
      </el-form-item>
      
      <!-- 登录 -->
      <el-form-item style="width: 100%">
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
import { Message } from "element-ui";
import { postRequest } from "../../untils/axiosApi";

export default {
  name: "Login",
  data() {
    return {
      loginForm: {
        username: "", // 用户名
        password: "" // 密码
      }
    };
  },
  methods: {
    //用户登录
    login() {
      postRequest("/login", {
        username: this.loginForm.username,
        password: this.loginForm.password
      }).then(response => {
        if (response != undefined && response.status == 200) {
          // 登录成功消息提示
          Message({
            type: "success",
            dangerouslyUseHTMLString: true,
            message: "<strong>登录成功！</strong>"
          });
          //跳转页面
          this.$router.push({ path: "/admin/welcome" });
        } else {
          if (response != undefined) {
            // 登录失败消息提示
            Message({
              type: "error",
              dangerouslyUseHTMLString: true,
              message: "<strong>" + response.msg + "</strong>"
            });
          }
        }
      });
    }
  },
  directives: {
    focus: {
      inserted: function(el) {
        //聚焦输入框
        var element = document.getElementById("userName");
        element.focus();
      }
    }
  }
};
</script>

<style>
#login {
  background: url("http://qr3f6y9b1.hb-bkt.clouddn.com/content.jpg") no-repeat;
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
