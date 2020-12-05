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
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index" min-width="60">
      </el-table-column>

      <el-table-column label="头像" min-width="85">
        <template slot-scope="scope">
          <el-avatar :size="50" :src="scope.row.userFace"></el-avatar>
        </template>
      </el-table-column>

      <el-table-column
        label="昵称"
        prop="nickName"
        min-width="110"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column
        label="用户名"
        prop="username"
        min-width="110"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column
        label="密码"
        prop="password"
        min-width="140"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column
        label="邮箱"
        prop="email"
        min-width="180"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column label="状态" min-width="80">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.enabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            height="30"
            @change="changeUserState(scope.row.userId, scope.row.enabled)"
          >
          </el-switch>
        </template>
      </el-table-column>

      <el-table-column label="创建时间" sortable min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <el-table-column label="更新时间" sortable min-width="180">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <el-table-column min-width="270">
        <template slot="header" slot-scope="scope">
          <el-input
            style="width:50%"
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
            @onConfirm="handleDelete(scope.row.userId)"
          >
            <el-button slot="reference" size="mini" type="danger"
              >删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="编辑用户"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <el-form
        :model="form"
        status-icon
        :rules="rules"
        ref="from"
        label-position="right"
        class="demo-ruleForm"
      >
        <el-form-item>
          <el-avatar
            :size="60"
            :src="form.userFace"
            style="margin-left: 43%;"
          ></el-avatar>
        </el-form-item>

        <el-form-item label="昵称:" :label-width="formLabelWidth">
          <el-input v-model="form.nickName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户名:" :label-width="formLabelWidth" required>
          <el-input v-model="form.userName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="密码:"
          prop="password"
          :label-width="formLabelWidth"
          required
        >
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item
          label="确认密码:"
          prop="checkPassword"
          :label-width="formLabelWidth"
          required
        >
          <el-input v-model="form.checkPassword" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮箱:" :label-width="formLabelWidth" required>
          <el-input v-model="form.email"></el-input>
        </el-form-item>

        <el-form-item label="状态:" :label-width="formLabelWidth" required>
          <el-switch
            style="display: block"
            active-color="#13ce66"
            inactive-color="#ff4949"
            height="30"
          >
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="centerDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
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
          this.$refs.rules.validateField("checkPassword");
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
      search: "",
      userInfo: [],
      formLabelWidth: "90px",
      centerDialogVisible: false,
      form: {
        userId: "",
        userFace: "",
        nickName: "",
        userName: "",
        password: "",
        checkPassword: "",
        email: "",
        enabled: false
      },
      rules: {
        password: [{ validator: validatePass, trigger: "blur" }],
        checkPassword: [{ validator: validateCheckPass, trigger: "blur" }]
      }
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    axios.all([getRequest("/admin/users/")]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    //数据处理
    setData(response) {
      if (response.status == 200) {
        this.userInfo = response.data;
      }
    },
    //提交用户信息
    onSubmit() {
      //关闭Dialog
      this.centerDialogVisible = false;
      //用户ID为空则表示新增用户，否则更新用户
      if (this.form.userId == "") {
        this.insertUser();
      } else {
        this.updateUser();
      }
    },
    //根据表单更新用户
    updateUser() {
      postRequest("/admin/users/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          //重新获取用户数据
          this.getUsers();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //根据表单添加用户
    insertUser() {
      this.form.userFace =
        "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80";
      putRequest("/admin/users/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          //重新获取用户数据
          this.getUsers();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //弹出Dialog并传递row数据
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
          enabled: false
        };
      }
    },
    //执行删除用户
    handleDelete(userId) {
      deleteRequest("/admin/users/" + userId).then(response => {
        if (response.status == 204) {
          Message.success(response.msg);
          this.getUsers();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //更新用户状态
    changeUserState(userId, state) {
      putRequest("/admin/users/" + userId + "/" + state).then(response => {
        if (response.status == 201) {
          Message.success("更新用户状态成功！");
        } else {
          Message.error("更新用户状态失败！");
        }
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
</style>
