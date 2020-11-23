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
      @selection-change="handleSelectionChange()"
      style="width: 100%;"
    >
      <el-table-column type="selection" width="45"> </el-table-column>
      <el-table-column label="序号" type="index" width="50"> </el-table-column>

      <el-table-column label="头像" width="75">
        <template slot-scope="scope">
          <el-avatar :size="50" :src="scope.row.userFace"></el-avatar>
        </template>
      </el-table-column>
      <el-table-column
        label="昵称"
        prop="nickName"
        width="100"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        label="用户名"
        prop="username"
        width="100"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        label="密码"
        prop="password"
        width="100"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        label="邮箱"
        prop="email"
        width="170"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column label="状态" width="70">
        <template slot-scope="scope">
          <el-switch
            style="display: block"
            v-model="scope.row.enabled"
            active-color="#13ce66"
            inactive-color="#ff4949"
            height="30"
          >
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" sortable width="170">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" sortable width="170">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateFormat }}</span>
        </template>
      </el-table-column>

      <el-table-column>
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
            @onConfirm="handleDelete(scope.$index, scope.row)"
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
      <el-form :model="form" status-icon :rules="rules" ref="from" label-position="right" class="demo-ruleForm">
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
        <el-form-item label="密码:" prop="password" :label-width="formLabelWidth" required>
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码:" prop="checkPassword" :label-width="formLabelWidth" required>
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
import { getRequest } from "../../../untils/axiosApi";
import { deleteRequest } from "../../../untils/axiosApi";
import { postRequest } from "../../../untils/axiosApi";
import { putRequest } from "../../../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";


export default {
  name: "UserManager",
  data() {
      var validatePass=(rule,value,callback)=>{
          console.log(value)
        if(value===''){
            callback(new Error("请输入密码！"));
        }else{
            if(this.form.checkPassword !==''){
                this.$refs.rules.validateField('checkPassword')
            }
            callback();
        }
      };
      var validateCheckPass=(rule,value,callback)=>{
          if(value ===''){
              callback("请再次输入密码！");
          }else if(value!==this.form.password){
            callback("两次输入密码不一致！");
          }else{
              callback();
          }
      };
    return {
      visible: false,
      formLabelWidth: "90px",
      centerDialogVisible: false,
      userInfo: [],
      search: "",
      form: {
        userId: "",
        userFace: "",
        nickName: "",
        userName: "",
        password: "",
        checkPassword:"",
        email: "",
        enabled: false
      },
      rules: {
        password:[{validator:validatePass,trigger:'blur'}],
          checkPassword:[{validator:validateCheckPass,trigger:'blur'}]
      }
    };
  },
  beforeRouteEnter(to, from, next){
    axios.all([getRequest("/admin/users/")]).then(
        axios.spread(response => {
        next(vm=>{
            vm.setData(response)
        })
    }))
  },
  methods: {
    setData(response) {
        if (response.status == 200) {
          this.userInfo = response.data;
        }
    },
    onSubmit() {
      this.centerDialogVisible = false;
      if (this.form.userId == "") {
        this.insertUser();
      } else {
        this.updateUser();
      }
    },
    updateUser() {
      postRequest("/admin/users/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          this.getUsers();
        } else {
          Message.error(response.msg);
        }
      });
    },
    insertUser() {
        this.form.userFace="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/80/h/80";
        putRequest("/admin/users/",this.form).then(response =>{
            if(response.status == 201){
                Message.success(response.msg);
                this.getUsers();
            }else{
                Message.error(response.msg);
            }
        });
    },
    handleCurrentChange(row) {
      this.centerDialogVisible = true;
      if (row != undefined) {
        this.form = {
          userId: row.userId,
          userFace: row.userFace,
          nickName: row.nickName,
          userName: row.username,
          password: row.password,
          checkPassword:row.password,
          email: row.email,
          enabled: row.enabled
        };
      } else {
        this.form = {
          userId: "",
          userFace: "",
          nickName: "",
          userName: "",
          password: "",
          checkPassword:"",
          email: "",
          enabled: false
        };
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    handleDelete(index, row) {
      deleteRequest("/admin/users/" + row.userId).then(response => {
        if (response.status == 204) {
          Message.success(response.msg);
          this.getUsers();
        } else {
          Message.error(response.msg);
        }
      });
    }
  },
  filters: {
    dateFormat: function(value) {
      var date = new Date(value);

      var years = date.getFullYear();
      var months = (date.getMonth() + 1).toString().padStart(2, "0");
      var days = date
        .getDate()
        .toString()
        .padStart(2, "0");
      var hours = date
        .getHours()
        .toString()
        .padStart(2, "0");
      var minutes = date
        .getMinutes()
        .toString()
        .padStart(2, "0");
      var seconds = date
        .getSeconds()
        .toString()
        .padStart(2, "0");

      return `${years}-${months}-${days} ${hours}:${minutes}:${seconds}`;
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
