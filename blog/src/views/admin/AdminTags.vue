<!--
 * @FileDescription: 后台管理标签管理组件
 * @Author: 徐茂华
 * @Date: 2020-09-11 21:33:39
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:27:06
 * @FilePath: \src\views\admin\AdminTags.vue
-->
<template>
  <div id="tags">
    <el-table
      :data="
        tagsInfo.filter(
          data =>
            !search || data.tagName.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      class="m-table"
    >
      <!-- 序号 -->
      <el-table-column label="序号" type="index"> </el-table-column>

      <!-- 标签名称 -->
      <el-table-column
        label="标签名"
        prop="tagName"
        sortable
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 标签类型 -->
      <el-table-column label="标签类型" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag size="medium" :type="scope.row.tagType" effect="dark">{{
            scope.row.tagType
          }}</el-tag>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column>
        <template slot="header" slot-scope="scope">
          <el-input
            class="m-search"
            v-model="search"
            size="mini"
            placeholder="输入标签名搜索"
          />
          &emsp;
          <el-button type="primary" size="mini" @click="handleCurrentChange()"
            >新增标签</el-button
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
            @onConfirm="deleteTag(scope.$index, scope.row)"
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
      title="编辑标签"
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
        <!-- 标签名 -->
        <el-form-item
          label="标签名:"
          prop="tagName"
          :label-width="formLabelWidth"
        >
          <el-input
            v-model="form.tagName"
            autocomplete="off"
            maxlength="10"
            show-word-limit
          ></el-input>
        </el-form-item>
        <!-- 标签类型 -->
        <el-form-item
          label="标签类型:"
          prop="tagType"
          :label-width="formLabelWidth"
        >
          <el-select v-model="form.tagType" placeholder="请选择标签：">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.value"
              :value="item.value"
            >
              <span class="tag-value">{{ item.value }}</span>
              <span class="tag-label">{{ item.label }}</span>
            </el-option>
          </el-select>
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
        :total="totalTagsLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getTagsByCurrentPage"
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
  name: "tags",
  data() {
    return {
      search: "", // 标签名搜索关键字
      tagsInfo: [], // 标签信息
      pageSize: 10, // 分页长度
      currentPage: 1, // 当前页码
      totalPages: 0, // 总页数
      totalTagsLen: 0, // 总标签个数
      formLabelWidth: "100px", // label长度
      centerDialogVisible: false, // dialog是否开启
      // 标签表单
      form: {
        tagName: "",
        tagType: ""
      },
      // 验证规则
      rules: {
        tagName: [
          { required: true, message: "请输入标签名称", trigger: "blur" }
        ],
        tagType: [
          { required: true, message: "请选择标签类型", trigger: "blur" }
        ]
      },
      // 标签颜色
      options: [
        {
          value: "success",
          label: "绿色"
        },
        {
          value: "primary",
          label: "蓝色"
        },
        {
          value: "warning",
          label: "黄色"
        },
        {
          value: "danger",
          label: "红色"
        },
        {
          value: "info",
          label: "灰色"
        }
      ]
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    // 分页对象
    var page = { pageNum: 1, pageSize: 10 };

    axios.all([postRequest("/admin/tags/page/", page)]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    /**
     * @description: 处理标签相关数据
     * @param {Map} response
     * @return {void}
     */
    setData(response) {
      if (response.status == 200) {
        // 标签信息
        this.tagsInfo = response.data.content;
        // 当前页码
        this.currentPage = response.data.pageNum;
        // 总标签数
        this.totalTagsLen = response.data.totalSize;
        // 总页数
        this.totalPages = response.data.totalPages;
      }
    },

    /**
     * @description: 进行提交标签信息操作
     * @return {void}
     */
    onSubmit() {
      // 验证表单
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          //关闭Dialog
          this.centerDialogVisible = false;
          //tagId为空则添加标签
          if (this.form.tagId === "") {
            this.insertTag();
            //tagId不为空则更新标签
          } else {
            this.updateTag();
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
      this.$refs["ruleForm"].resetFields();
      //关闭Dialog
      this.centerDialogVisible = false;
    },

    /**
     * @description: 根据页码获取标签信息
     * @param {Number} currentPage
     * @return {void}
     */
    getTagsByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: this.pageSize };

      postRequest("/admin/tags/page/", page).then(response => {
        this.setData(response);
      });
    },

    /**
     * @description: 进行更新标签操作
     * @return {void}
     */
    updateTag() {
      postRequest("/admin/tags/", this.form).then(response => {
        var successMessage = "标签更新成功！";
        var errorMessage = "标签更新失败！已存在相同标签名或请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 201) {
          this.getTagsByCurrentPage(this.currentPage);
        }
      });
    },

    /**
     * @description: 进行添加标签操作
     * @return {void}
     */
    insertTag() {
      putRequest("/admin/tags/", this.form).then(response => {
        var successMessage = "标签添加成功！";
        var errorMessage = "标签添加失败！已存在相同标签名或请稍后再试。";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 201) {
          if (this.totalTagsLen % this.pageSize == 0) {
            this.getTagsByCurrentPage(this.totalPages + 1);
          } else {
            this.getTagsByCurrentPage(this.totalPages);
          }
        }
      });
    },

    /**
     * @description: 点击按钮弹出Dialog并赋值表单
     * @param {Map} row
     * @return {void}
     */
    handleCurrentChange(row) {
      //弹出Dialog
      this.centerDialogVisible = true;
      //若row不为undefined时表示编辑标签，将行数据赋值给表单
      if (row != undefined) {
        this.form = {
          tagId: row.tagId,
          tagName: row.tagName,
          tagType: row.tagType
        };
        //若row为undefined时表示添加标签，将表单内容置为空
      } else {
        this.form = {
          tagId: "",
          tagName: "",
          tagType: ""
        };
      }
    },

    /**
     * @description: 进行删除标签操作
     * @param {Number} index
     * @param {Map} row
     * @return {void}
     */
    deleteTag(index, row) {
      deleteRequest("/admin/tags/" + row.tagId).then(response => {
        var successMessage = "标签删除成功！";
        var errorMessage = "标签删除失败！请稍后再试。";
        // 显示消息提示
        this.common.deleteMessage(successMessage, errorMessage, response);
        // 回到最后一页
        if (response.status == 204) {
          if ((this.totalSize - 1) % this.pageSize == 0) {
            this.getTagsByCurrentPage(this.currentPage - 1);
          } else {
            this.getTagsByCurrentPage(this.currentPage);
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
.tag-value {
  float: left;
}
.tag-label {
  float: right;
  color: #8492a6;
  font-size: 13px;
}
</style>
