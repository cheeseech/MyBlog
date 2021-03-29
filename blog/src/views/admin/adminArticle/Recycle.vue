<!--
 * @FileDescription: 后台文章回收站组件
 * @Author: 徐茂华
 * @Date: 2020-11-07 16:26:54
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:16:09
 * @FilePath: \src\views\admin\adminArticle\Recycle.vue
-->
<template>
  <div id="recycle">
    <el-table
      :data="
        articleInfo.filter(
          data =>
            !search || data.title.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      class="m-table"
    >
      <!-- 序号 -->
      <el-table-column label="序号" type="index" min-width="50" align="center">
      </el-table-column>

      <!-- 文章标题 -->
      <el-table-column
        label="文章标题"
        prop="title"
        min-width="180"
        sortable
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 文章类型 -->
      <el-table-column label="类型" min-width="80" align="center">
        <template slot-scope="scope">
          <el-tag
            size="medium"
            type="danger"
            effect="dark"
            v-if="scope.row.typeId == 1"
            >{{ scope.row.typeName }}</el-tag
          >
          <el-tag
            size="medium"
            type="warning"
            effect="dark"
            v-if="scope.row.typeId == 2"
            >{{ scope.row.typeName }}</el-tag
          >
          <el-tag
            size="medium"
            type="success"
            effect="dark"
            v-if="scope.row.typeId == 3"
            >{{ scope.row.typeName }}</el-tag
          >
        </template>
      </el-table-column>

      <!-- 文章专栏 -->
      <el-table-column
        label="专栏"
        prop="cateName"
        min-width="80"
        align="center"
      >
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column
        label="创建时间"
        sortable
        prop="createTime"
        min-width="170"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 删除时间 -->
      <el-table-column
        label="删除时间"
        sortable
        prop="updateTime"
        min-width="170"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column align="center" min-width="250">
        <template slot="header" slot-scope="scope">
          <el-input
            class="article-search"
            v-model="search"
            size="mini"
            placeholder="输入标题检索..."
          />
        </template>
        <template slot-scope="scope">
          <!-- 编辑文章 -->
          <el-button
            size="small"
            @click="goPublishArticle(scope.row.articleId)"
            type="primary"
            >编辑</el-button
          >
          <!-- 更改文章状态 -->
          <el-popconfirm
            confirmButtonText="确定还原"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定还原这一篇文章嘛？"
            @onConfirm="updateArticleState(scope.row.articleId)"
          >
            <el-button slot="reference" size="small" type="success"
              >还原为草稿</el-button
            >
          </el-popconfirm>
          <!-- 删除文章 -->
          <el-popconfirm
            confirmButtonText="残忍删除"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定删除这一篇文章嘛？"
            @onConfirm="deleteArticle(scope.row.articleId)"
          >
            <el-button slot="reference" size="small" type="danger"
              >彻底删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="m-paging">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalArticlesLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getArticlesByCurrentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { getRequest, deleteRequest, postRequest } from "@/../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "recycle",
  data() {
    return {
      pageSize: 9, // 分页长度
      currentPage: 1, // 当前页码
      totalPages: 0, // 总页数
      totalArticlesLen: 0, // 总文章数
      search: "", // 搜索文章关键字
      articleInfo: [] // 文章数据集合
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    // 分页对象
    var page = { pageNum: 1, pageSize: 9 };

    //获取已被删除状态的文章
    axios.all([postRequest("/admin/article/delete/", page)]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    /**
     * @description: 处理文章相关数据
     * @param {Map} response
     * @return {void}
     */
    setData(response) {
      if (response.status == 200) {
        // 文章数据集合
        this.articleInfo = response.data.content;
        // 当前页码
        this.currentPage = response.data.pageNum;
        // 总文章长度
        this.totalArticlesLen = response.data.totalSize;
        // 总页数
        this.totalPages = response.data.totalPages;
      }
    },

    /**
     * @description: 根据页码获取文章数据集合
     * @param {Number} currentPage
     * @return {void}
     */
    getArticlesByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: this.pageSize };

      postRequest("/admin/article/delete", page).then(response => {
        this.setData(response);
      });
    },

    /**
     * @description: 还原文章为草稿状态，(将文章状态设为0)
     * @param {Number} articleId
     * @return {void}
     */
    updateArticleState(articleId) {
      postRequest("/admin/article/" + articleId + "/" + 0).then(response => {
        var successMessage = "文章还原成功！";
        var errorMessage = "文章还原失败！请稍后再试！";
        // 显示消息提示
        this.common.insertUpdateMessage(successMessage, errorMessage, response);
        // 刷新当前页
        if (response.status == 201) {
          if ((this.totalArticlesLen - 1) % this.pageSize == 0) {
            var pageNum = this.currentPage - 1;
            if (pageNum < 0) {
              pageNum = 0;
            }
            this.getArticlesByCurrentPage(pageNum);
          } else {
            this.getArticlesByCurrentPage(this.currentPage);
          }
        }
      });
    },

    /**
     * @description: 执行彻底删除文章操作
     * @param {Number} articleId
     * @return {void}
     */
    deleteArticle(articleId) {
      deleteRequest("/admin/article/" + articleId).then(response => {
        var successMessage = "文章删除成功！";
        var errorMessage = "文章删除失败！请稍后再试！";
        // 显示消息提示
        this.common.deleteMessage(successMessage, errorMessage, response);
        // 刷新当前页
        if (response.status == 204) {
          if ((this.totalArticlesLen - 1) % this.pageSize == 0) {
            this.getArticlesByCurrentPage(this.currentPage - 1);
          } else {
            this.getArticlesByCurrentPage(this.currentPage);
          }
        }
      });
    },

    /**
     * @description: 跳转到发布文章页并传递文章ID
     * @param {Number} value
     * @return {void}
     */
    goPublishArticle(value) {
      this.$router.push({
        name: "发布文章",
        params: { value }
      });
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
</style>
