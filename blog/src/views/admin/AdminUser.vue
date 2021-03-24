<!--
 * @FileDescription: 后台管理用户管理组件
 * @Author: 徐茂华
 * @Date: 2020-12-03 09:04:23
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-18 17:14:37
 * @FilePath: \src\views\admin\AdminUser.vue
-->
<template>
  <div id="userManager">
    <el-table
      :data="
        userInfo.filter(
          data =>
            !search ||
            data.username.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      class="m-table"
    >
      <!-- 序号 -->
      <el-table-column label="序号" type="index"> </el-table-column>

      <!-- 头像 -->
      <el-table-column label="头像" min-width="70">
        <template slot-scope="scope">
          <el-avatar :size="45" :src="scope.row.userFace"></el-avatar>
        </template>
      </el-table-column>

      <!-- 昵称 -->
      <el-table-column
        label="昵称"
        prop="nickName"
        min-width="100"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 用户名 -->
      <el-table-column
        label="用户名"
        prop="username"
        min-width="100"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 密码 -->
      <el-table-column label="密码" min-width="110">
        <template slot-scope="scope">
          <el-tooltip placement="top" effect="light">
            <div slot="content">{{ scope.row.password }}</div>
            <el-button>查看密码</el-button>
          </el-tooltip>
        </template>
      </el-table-column>

      <!-- 邮箱 -->
      <el-table-column
        label="邮箱"
        prop="email"
        min-width="180"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 状态 -->
      <el-table-column label="状态" min-width="60">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            height="30"
            @change="updateUserState(scope.row.userId, scope.row.enabled)"
          >
          </el-switch>
        </template>
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column
        label="创建时间"
        sortable
        min-width="160"
        prop="createTime"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 更新时间 -->
      <el-table-column
        label="更新时间"
        sortable
        prop="updateTime"
        min-width="160"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column min-width="250">
        <template slot="header" slot-scope="scope">
          <el-input
            class="m-search"
            v-model="search"
            size="mini"
            placeholder="输入用户名搜索"
          />
          &emsp;
          <el-button type="primary" size="mini" @click="handleCurrentChange()"
            >新增用户</el-button
          >
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleCurrentChange(scope.row)"
            >编辑</el-button
          >
          &emsp;
          <el-popconfirm
            confirmButtonText="残忍删除"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定删除这一条信息吗？"
            @onConfirm="deleteUser(scope.row.userId)"
          >
            <el-button slot="reference" size="mini" type="danger"
              >删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- Dialog -->
    <el-dialog
      center
      top="7vh"
      width="30%"
      title="编辑用户"
      :visible.sync="centerDialogVisible"
    >
      <el-form
        :model="form"
        :rules="rules"
        ref="form"
        label-position="right"
        :label-width="formLabelWidth"
        class="demo-ruleForm"
      >
        <!-- 头像 -->
        <el-form-item>
          <el-avatar
            :label-width="formLabelWidth"
            :size="60"
            :src="form.userFace"
            class="dialog-img"
          ></el-avatar>
        </el-form-item>

        <!-- 昵称 -->
        <el-form-item label="昵称:" prop="nickName">
          <el-input
            type="text"
            v-model="form.nickName"
            maxlength="10"
            show-word-limit
            autocomplete="off"
          >
          </el-input>
        </el-form-item>

        <!-- 用户名 -->
        <el-form-item label="用户名:" prop="userName">
          <el-input
            v-model="form.userName"
            autocomplete="off"
            maxlength="10"
            show-word-limit
          ></el-input>
        </el-form-item>

        <!-- 密码 -->
        <el-form-item label="密码:" prop="password">
          <el-input
            type="password"
            v-model="form.password"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <!-- 确认密码 -->
        <el-form-item label="确认密码:" prop="checkPassword">
          <el-input
            type="password"
            v-model="form.checkPassword"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <!-- 邮箱 -->
        <el-form-item label="邮箱:" prop="email">
          <el-input v-model="form.email"></el-input>
        </el-form-item>

        <!-- 状态 -->
        <el-form-item label="状态:">
          <el-switch
            class="dialog-switch"
            active-color="#13ce66"
            inactive-color="#ff4949"
            height="30"
            v-model="form.enabled"
          >
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 分页 -->
    <div class="m-paging">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalUsersLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getUsersByCurrentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { Message } from "element-ui";
import {
  getRequest,
  postRequest,
  putRequest,
  deleteRequest
} from "@/../untils/axiosApi";

export default {
  name: "UserManager",
  data() {
    //密码非空校验
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码！"));
      } else {
        if (this.form.checkPassword !== "") {
          this.$refs.form.validateField("checkPassword");
        }
        callback();
      }
    };
    //两次密码输入校验
    var validateCheckPass = (rule, value, callback) => {
      if (value === "") {
        callback("请再次输入密码！");
      } else if (value !== this.form.password) {
        callback("两次输入密码不一致！");
      } else {
        callback();
      }
    };
    return {
      search: "", // 用户名搜索关键字
      userInfo: [], // 用户信息
      pageSize: 7, // 分页长度
      currentPage: 1, // 当前页码
      totalPages: 0, // 总页数
      totalUsersLen: 0, // 总用户长度
      switch: "true", // 用户状态
      formLabelWidth: "100px", // label长度
      centerDialogVisible: false, // dialog是否开启
      // 用户表单
      form: {
        userId: "",
        userFace: "",
        nickName: "",
        userName: "",
        password: "",
        checkPassword: "",
        email: "",
        enabled: true
      },
      // 表单验证规则
      rules: {
        password: [
          { required: true, validator: validatePass, trigger: "blur" }
        ],
        checkPassword: [
          { required: true, validator: validateCheckPass, trigger: "blur" }
        ],
        nickName: [{ required: true, message: "请输入昵称", trigger: "blur" }],
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ]
      }
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    // 分页对象
    var page = { pageNum: 1, pageSize: 7 };

    axios.all([postRequest("/admin/users/page/", page)]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    /**
     * @description: 处理用户相关数据
     * @param {Map} response
     * @return {void}
     */
    setData(response) {
      if (response.status == 200) {
        // 用户信息
        this.userInfo = response.data.content;
        // 当前页码
        this.currentPage = response.data.pageNum;
        // 总用户条数
        this.totalUsersLen = response.data.totalSize;
        // 总页数
        this.totalPages = response.data.totalPages;
      }
    },

    /**
     * @description: 根据页码获取用户信息
     * @param {Number} currentPage
     * @return {void}
     */
    getUsersByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: this.pageSize };

      postRequest("/admin/users/page/", page).then(response => {
        this.setData(response);
      });
    },

    /**
     * @description: 提交用户信息
     * @return {void}
     */
    onSubmit() {
      // 验证表单
      this.$refs["form"].validate(valid => {
        if (valid) {
          //关闭Dialog
          this.centerDialogVisible = false;

          //用户ID为空则表示新增用户，否则更新用户
          if (this.form.userId == "") {
            this.insertUser();
          } else {
            this.updateUser();
          }
        } else {
          return false;
        }
      });
    },

    /**
     * @description: 关闭Dialog，清除表单
     * @return {void}
     */
    resetForm() {
      // 清空表单验证
      this.$refs["form"].resetFields();
      //关闭Dialog
      this.centerDialogVisible = false;
    },

    /**
     * @description: 进行更新用户操作
     * @return {void}
     */
    updateUser() {
      postRequest("/admin/users/", this.form).then(response => {
        var successMessage = "用户更新成功！";
        var errorMessage = "用户更新失败！已存在相同用户名或请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 回到最后一页
        this.getUsersByCurrentPage(this.totalPages);
      });
    },

    /**
     * @description: 进行添加用户操作
     * @return {void}
     */
    insertUser() {
      this.form.userFace =
        "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80";

      putRequest("/admin/users/", this.form).then(response => {
        var successMessage = "用户添加成功！";
        var errorMessage = "用户添加失败！已存在相同用户名！或请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 201) {
          if (this.totalUsersLen % this.pageSize == 0) {
            this.getUsersByCurrentPage(this.totalPages + 1);
          } else {
            this.getUsersByCurrentPage(this.totalPages);
          }
        }
      });
    },

    /**
     * @description: 弹出Dialog并传递row数据
     * @param {Map} row
     * @return {void}
     */
    handleCurrentChange(row) {
      //显示Dialog
      this.centerDialogVisible = true;

      //当row不为undefined时表示编辑用户
      if (row != undefined) {
        //将行数据赋值给表单
        this.form = {
          userId: row.userId,
          userFace: row.userFace,
          nickName: row.nickName,
          userName: row.username,
          password: row.password,
          checkPassword: row.password,
          email: row.email,
          enabled: row.enabled
        };
        //当row为undefined时表示添加用户
      } else {
        //表单内容置为空
        this.form = {
          userId: "",
          userFace: "",
          nickName: "",
          userName: "",
          password: "",
          checkPassword: "",
          email: "",
          enabled: true
        };
      }
    },

    /**
     * @description: 进行删除用户操作
     * @param {Number} userId
     * @return {void}
     */
    deleteUser(userId) {
      deleteRequest("/admin/users/" + userId).then(response => {
        var successMessage = "用户删除成功！";
        var errorMessage = "用户删除失败！请稍后再试。";
        // 显示消息提示
        this.common.deleteMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 204) {
          if ((this.totalUsersLen - 1) % this.pageSize == 0) {
            this.getUsersByCurrentPage(this.totalPages - 1);
          } else {
            this.getUsersByCurrentPage(this.totalPages);
          }
        }
      });
    },

    /**
     * @description: 进行更新用户状态操作
     * @param {Number} userId
     * @param {Number} state
     * @return {void}
     */
    updateUserState(userId, state) {
      putRequest("/admin/users/" + userId + "/" + state).then(response => {
        var successMessage = "状态更新成功！";
        var errorMessage = "状态更新失败！请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
      });
    }
  }
};
</script>

<style>
.el-table td,
.el-table th {
  text-align: center;
}
.el-main {
  padding: 0;
}
.el-switch {
  height: auto;
}
.dialog-img {
  margin-left: 43%;
}
.dialog-switch {
  margin-left: 25%;
}
</style>
