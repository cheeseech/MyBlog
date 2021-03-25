<!--
 * @FileDescription: 后台管理专栏管理组件
 * @Author: 徐茂华
 * @Date: 2020-09-11 21:34:02
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:26:33
 * @FilePath: \src\views\admin\AdminCategory.vue
-->
<template>
  <div id="category">
    <el-table
      :data="
        categoriesInfo.filter(
          data =>
            !search ||
            data.cateName.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      class="m-table"
    >
      <!-- 序号 -->
      <el-table-column label="序号" type="index"> </el-table-column>

      <!-- 专栏名称 -->
      <el-table-column
        label="专栏名"
        prop="cateName"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 专栏概述 -->
      <el-table-column
        label="专栏概述"
        prop="cateSummary"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column label="创建时间" sortable prop="createTime">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column>
        <template slot="header" slot-scope="scope">
          <el-input
            class="m-search"
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
            @onConfirm="deleteCategory(scope.row)"
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
      title="编辑专栏"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <!-- 表单 -->
      <el-form
        :model="form"
        :rules="rules"
        ref="ruleForm"
        label-position="right"
      >
        <!-- 专栏名 -->
        <el-form-item
          label="专栏名:"
          prop="cateName"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="form.cateName"
            autocomplete="off"
            maxlength="10"
            show-word-limit
          ></el-input>
        </el-form-item>
        <!-- 专栏概述 -->
        <el-form-item
          label="专栏概述:"
          prop="cateSummary"
          :label-width="formLabelWidth"
        >
          <el-input
            type="textarea"
            :autosize="{ minRows: 3, maxRows: 9 }"
            v-model="form.cateSummary"
            @keyup.enter.native="onSubmit"
            autocomplete="off"
          ></el-input>
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
        :total="totalCategoriesLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getCategoriesByCurrentPage"
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
  putRequest,
  deleteRequest,
  postRequest
} from "@/../untils/axiosApi";

export default {
  name: "category",
  data() {
    return {
      search: "", // 专栏名搜索关键字
      pageSize: 10, // 分页长度
      totalPages: 0, // 总页数
      currentPage: 1, // 当前页码
      categoriesInfo: [], // 专栏信息
      totalCategoriesLen: 0, // 总专栏数
      formLabelWidth: "100px", // label长度
      centerDialogVisible: false, // dialog是否开启
      // 专栏表单
      form: {
        cateId: "",
        cateName: "",
        cateSummary: ""
      },
      // 验证规则
      rules: {
        cateName: [
          { required: true, message: "请输入专栏名称", trigger: "blur" }
        ],
        cateSummary: [
          { required: true, message: "请选择专栏概述", trigger: "blur" }
        ]
      }
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    // 分页对象
    var page = { pageNum: 1, pageSize: 10 };

    axios.all([postRequest("/admin/category/page/", page)]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    /**
     * @description: 处理专栏相关数据
     * @return {void}
     */
    setData(response) {
      if (response.status == 200) {
        // 专栏数据
        this.categoriesInfo = response.data.content;
        // 当前页码
        this.currentPage = response.data.pageNum;
        // 总专栏数
        this.totalCategoriesLen = response.data.totalSize;
        // 总页数
        this.totalPages = response.data.totalPages;
      }
    },

    /**
     * @description: 进行提交专栏信息操作
     * @return {void}
     */
    onSubmit() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          //关闭Dialog
          this.centerDialogVisible = false;
          //cateId为空时表示添加专栏
          if (this.form.cateId == "") {
            this.insertCategory();
            //否则表示更新专栏
          } else {
            this.updateCategory();
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
      // 清除规则
      this.$refs["ruleForm"].resetFields();
      //关闭Dialog
      this.centerDialogVisible = false;
    },

    /**
     * @description: 点击新建或编辑按钮时
     * @param {Map} row
     * @return {void}
     */
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

    /**
     * @description: 根据页码获取专栏数据
     * @param {Number} currentPage
     * @return {void}
     */
    getCategoriesByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: this.pageSize };

      postRequest("/admin/category/page/", page).then(response => {
        this.setData(response);
      });
    },

    /**
     * @description: 进行添加专栏操作
     * @return {void}
     */
    insertCategory() {
      putRequest("/admin/category/", this.form).then(response => {
        var successMessage = "专栏添加成功！";
        var errorMessage = "专栏添加失败！已存在相同专栏名或请请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 201) {
          if (this.totalCategoriesLen % this.pageSize == 0) {
            this.getCategoriesByCurrentPage(this.totalPages + 1);
          } else {
            this.getCategoriesByCurrentPage(this.totalPages);
          }
        }
      });
    },

    /**
     * @description: 进行更新专栏操作
     * @return {void}
     */
    updateCategory() {
      postRequest("/admin/category/", this.form).then(response => {
        var successMessage = "专栏更新成功！";
        var errorMessage = "专栏更新失败！已存在相同专栏名或请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 回到最后一页
        this.getCategoriesByCurrentPage(this.currentPage);
      });
    },

    /**
     * @description: 进行删除专栏操作
     * @param {Map} row
     * @return {void}
     */
    deleteCategory(row) {
      deleteRequest("/admin/category/" + row.cateId).then(response => {
        var successMessage = "专栏删除成功！";
        var errorMessage = "专栏删除失败！请稍后再试。";
        // 显示消息提示
        this.common.deleteMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 204) {
          if ((this.totalCategoriesLen - 1) % this.pageSize == 0) {
            var pageNum = this.currentPage - 1;
            if (pageNum < 0) {
              pageNum = 0;
            }
            this.getCategoriesByCurrentPage(pageNum);
          } else {
            this.getCategoriesByCurrentPage(this.currentPage);
          }
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
