<template>
    <div id="tags">
        <el-table
      :data="
        tags.filter(
          data =>
            !search ||
            data.tagName.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      @selection-change="handleSelectionChange()"
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index"> </el-table-column>

      <el-table-column
        label="标签名"
        prop="tagName"
        show-overflow-tooltip
      >
      </el-table-column>
      <el-table-column
        label="标签类型"
        show-overflow-tooltip
      >
      <template slot-scope="scope">        
            <el-tag size="medium" :type="scope.row.tagType" effect="dark">{{ scope.row.tagType }}</el-tag>
      </template>
      </el-table-column>
      
      <el-table-column>
        <template slot="header" slot-scope="scope">
          <el-input
            style="width:50%"
            v-model="search"
            size="mini"
            placeholder="输入标签名搜索"
          />
          &emsp;
          <el-button type="primary" size="mini" @click="handleCurrentChange()">新增标签</el-button>
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
        <el-form-item label="标签名:" :label-width="formLabelWidth">
          <el-input v-model="form.tagName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="标签类型:" :label-width="formLabelWidth">
            <el-select v-model="form.tagType" placeholder="请选择标签颜色：">
                <el-option label="绿色" value="success"></el-option>
                <el-option label="蓝色" value="primary"></el-option>
                <el-option label="黄色" value="warning"></el-option>
                <el-option label="红色" value="danger"></el-option>
                <el-option label="灰色" value="info"></el-option>
              </el-select>
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
import { postRequest } from "../../../untils/axiosApi";
import { putRequest} from "../../../untils/axiosApi"
import { deleteRequest } from "../../../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "tags",
  data() {
    return {
      tags: [],
      search:"",
      formLabelWidth:"100px",
      centerDialogVisible:false,
      form:{
        tagName: "",
        tagType: ""
      }
    };
  },
  beforeRouteEnter(to, from, next){
    axios.all([getRequest("/tags/")]).then(
        axios.spread(response => {
        next(vm=>{
            vm.setData(response)
        })
    }))
  },
  methods: {
      onSubmit(){
        this.centerDialogVisible = false;
        if(this.form.tagId == ''){
            this.insertTag();
        }else{
            this.updateTag();
        }
      },
      updateTag(){
        postRequest("/admin/tags/",this.form).then(response=>{
            if(response.status == 201){
                Message.success(response.msg);
                this.getTags();
            }else{
                Message.error(response.msg);
            }
        })
      },
      insertTag(){
          putRequest("/admin/tags/",this.form).then(response => {
              if(response.status == 201){
                  Message.success(response.msg);
                  this.getTags();
              }else{
                  Message.error(response.msg);
              }
          })
      },
    setData(response) {
        if (response.status == 200) {
          this.tags = response.data;
        }
    },
    handleCurrentChange(row) {
        this.centerDialogVisible=true;
        if(row !=undefined) {
            this.form={
                tagId:row.tagId,
                tagName:row.tagName,
                tagType:row.tagType
            }
        }else{
            this.form={
                tagId:"",
                tagName:"",
                tagType:""
            }
        }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
    },
    handleDelete(index, row) {
      deleteRequest("/admin/tags/" + row.tagId).then(response => {
        if (response.status == 204) {
          Message.success(response.msg);
          this.getTags();
        } else {
          Message.error(response.msg);
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
