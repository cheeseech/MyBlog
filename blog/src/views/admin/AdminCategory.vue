<template>
  <div id="category">
    <el-table
      :data="
        category.filter(
          data =>
            !search ||
            data.cateName.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index"> </el-table-column>

      <el-table-column label="专栏名" prop="cateName" show-overflow-tooltip>
      </el-table-column>
      <el-table-column
        label="专栏概述"
        prop="cateSummary"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <el-table-column>
        <template slot="header" slot-scope="scope">
          <el-input
            style="width:50%"
            v-model="search"
            size="mini"
            placeholder="输入专栏名搜索"
          />
          &emsp;
          <el-button type="primary" size="mini" @click="handleCurrentChange()"
            >新增专栏</el-button
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
            @onConfirm="handleDelete(scope.row)"
          >
            <el-button slot="reference" size="mini" type="danger"
              >删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="编辑专栏"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <el-form :model="form" label-position="right">
        <el-form-item label="专栏名:" :label-width="formLabelWidth">
          <el-input v-model="form.cateName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="专栏概述:" :label-width="formLabelWidth">
          <el-input v-model="form.cateSummary" autocomplete="off"></el-input>
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
  putRequest,
  deleteRequest,
  postRequest
} from "@/../untils/axiosApi";

export default {
  name: "category",
  data() {
    return {
      category: [],
      search: "",
      formLabelWidth: "100px",
      centerDialogVisible: false,
      form: {
        cateId: "",
        cateName: "",
        cateSummary: ""
      }
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    axios.all([getRequest("/category/")]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    //点击提交专栏信息
    onSubmit() {
        //关闭Dialog
      this.centerDialogVisible = false;
      //cateId为空时表示添加专栏
      if (this.form.cateId == "") {
        this.insertCategory();
        //否则表示更新专栏
      } else {
        this.updateCategory();
      }
    },
    //数据处理
    setData(response) {
      if (response.status == 200) {
        this.category = response.data;
      }
    },
    //根据表单内容添加专栏
    insertCategory() {
      putRequest("/admin/category/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          //重新获取专栏数据
          this.getCategory();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //根据表单内容更新专栏
    updateCategory() {
      postRequest("/admin/category/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          //重新获取专栏数据
          this.getCategory();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //点击新建或编辑按钮时
    handleCurrentChange(row) {
        //弹出Dialog
      this.centerDialogVisible = true;
      //若row不为undefined则表示更新专栏，将row数据赋值给表单
      if (row != undefined) {
        this.form = {
          cateId: row.cateId,
          cateName: row.cateName,
          cateSummary: row.cateSummary
        };
        //否则表示添加专栏，将表单内容置为空
      } else {
        this.form = {
          cateId: "",
          cateName: "",
          cateSummary: ""
        };
      }
    },
    //执行删除专栏
    handleDelete(row) {
      deleteRequest("/admin/category/" + row.cateId).then(response => {
        if (response.status == 204) {
          Message.success(response.msg);
          //重新获取专栏数据
          this.getCategory();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //获取专栏数据
    getCategory(){
        getRequest("/category/").then(response => {
            if(response.status == 200){
                this.category = response.data;
                Message.success("专栏数据获取成功！")
            }
        })
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
