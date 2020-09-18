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
      @current-change="handleCurrentChange()"
      @selection-change="handleSelectionChange()"
      style="width: 100%;"
    >
      <el-table-column type="selection" width="55"> </el-table-column>
      <el-table-column label="序号" type="index" width="50"> </el-table-column>

      <el-table-column label="头像" width="90">
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
        width="190"
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
      <el-table-column label="创建时间" sortable width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" sortable width="200">
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
            placeholder="输入关键字搜索"
          />
          &emsp;
          <el-button type="primary" size="mini">新增用户</el-button>
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="centerDialogVisible = true"
            >编辑</el-button
          >

          <el-popconfirm
            confirmButtonText="好的"
            cancelButtonText="不用了"
            icon="el-icon-info"
            iconColor="red"
            title="这是一段内容确定删除吗？"
          >
            <el-button
              slot="reference"
              size="mini"
              type="danger"
              @click="handleDelete(scope.$index, scope.row)"
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
      <el-form :model="form" label-position="right">
        <el-form-item label="昵称:" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="用户名:" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码:" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱:" :label-width="formLabelWidth">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态:" :label-width="formLabelWidth">
          <el-switch
            style="display: block"
            active-color="#13ce66"
            inactive-color="#ff4949"
            height="30"
          >
          </el-switch>
        </el-form-item>
        <el-form-item>
          <el-button @click="centerDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="centerDialogVisible = false"
            >确 定</el-button
          >
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { getRequest } from "../../../untils/axiosApi";

export default {
  name: "UserManager",
  data() {
    return {
      visible: false,
      formLabelWidth: "100px",
      centerDialogVisible: false,
      userInfo: [],
      search: "",
      form: {
        name: ""
      }
    };
  },
  created() {
    this.getUsers();
  },
  methods: {
    getUsers() {
      var _this = this;
      getRequest("/admin/users/").then(response => {
        if (response.status == 200) {
          _this.userInfo = response.data;
          console.log(_this.userInfo);
        }
      });
    },
    onSubmit() {
      console.log("submit!");
    },
    handleCurrentChange(val) {
      this.currentRow = val;
      console.log(this.currentRow);
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
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
