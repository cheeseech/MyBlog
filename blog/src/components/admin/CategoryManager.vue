<template>
    <div id="category">
        <el-table
      :data="
        tags.filter(
          data =>
            !search ||
            data.cateName.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      @selection-change="handleSelectionChange()"
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index"> </el-table-column>

      <el-table-column
        label="专栏名"
        prop="cateName"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        label="专栏概述"
        prop="cateSummary"
        show-overflow-tooltip
      >
      </el-table-column>

      <el-table-column label="创建时间">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateFormat }}</span>
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
          <el-button type="primary" size="mini" @click="handleCurrentChange()">新增专栏</el-button>
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
            <el-button
              slot="reference"
              size="mini"
              type="danger"
              >删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="编辑标签"
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
import { getRequest } from "../../../untils/axiosApi";
import{putRequest} from "../../../untils/axiosApi";
import{deleteRequest} from "../../../untils/axiosApi"
import{postRequest} from "../../../untils/axiosApi"
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "category",
  data() {
    return {
      tags: [],
      search:"",
      formLabelWidth:"100px",
      centerDialogVisible:false,
      form:{
          cateId:"",
          cateName: "",
          cateSummary:""
      }
    };
  },
  beforeRouteEnter(to, from, next){
    axios.all([getRequest("/category/")]).then(
        axios.spread(response => {
        next(vm=>{
            vm.setData(response)
        })
    }))
  },
  methods: {
      querySearch(queryString,cb){
          var tags = this.tags;
          var results =queryString?tags.filter(this.createFilter(queryString)):tags;
          console.log(results)
              cb(results)
          //调用callback返回建议列表的数据
      },
      createFilter(queryString) {
        return (restaurant) => {
          return (restaurant.cateName.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
        };
      },
      onSubmit() {
          this.centerDialogVisible = false;
          if(this.form.cateId==""){
            this.insertCategory();
          }else{
              this.updateCategory();
          }
      },
    setData(response) {
        if (response.status == 200) {
          this.tags = response.data;
        }
    },
    insertCategory(){
        putRequest("/admin/category/",this.form).then(response=>{
            if(response.status == 201){
                Message.success(response.msg);
                this.geCategory();
            }else{
                Message.error(response.msg);
            }
        })
    },
    updateCategory(){
        postRequest("/admin/category/",this.form).then(response=>{
            if(response.status == 201){
                Message.success(response.msg);
                this.geCategory();
            }else{
                Message.error(response.msg);
            }
        })
    },
    handleCurrentChange(row) {
        this.centerDialogVisible=true;
        if(row!=undefined){
            this.form={
                cateId:row.cateId,
                cateName:row.cateName,
                cateSummary: row.cateSummary
            }
        }else{
            this.form={
                cateId: "",
                cateName: "",
                cateSummary: ""
            }
        }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    handleDelete(row){
        deleteRequest("/admin/category/"+row.cateId).then(response=>{
            if(response.status == 204){
                Message.success(response.msg);
                this.geCategory();
            }else{
                Message.error(response.msg);
            }
        })
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
