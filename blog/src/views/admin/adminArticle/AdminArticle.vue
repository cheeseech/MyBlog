<!--
 * @FileDescription: 后台文章管理组件
 * @Author: 徐茂华
 * @Date: 2020-11-06 15:05:01
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:15:37
 * @FilePath: \src\views\admin\adminArticle\AdminArticle.vue
-->
<template>
  <div id="articleManager">
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
        min-width="200"
        sortable
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 简介 -->
      <el-table-column label="简介" min-width="150" align="center">
        <template slot-scope="scope">
          <el-tooltip placement="top" effect="light">
            <div slot="content" class="autoEnter">{{ scope.row.summary }}</div>
            <el-button>查看详情</el-button>
          </el-tooltip>
        </template>
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

      <!-- 文章标签列表 -->
      <el-table-column label="标签" min-width="70" header-align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <el-button
              type="primary"
              round
              class="btnPadded"
              v-for="tag in scope.row.tags"
              :key="tag.tagId"
              :type="tag.tagType"
              effect="dark"
            >
              {{ tag.tagName }}
            </el-button>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium" type="primary" effect="dark">详情</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <!-- 文章专栏 -->
      <el-table-column
        label="专栏"
        prop="cateName"
        min-width="100"
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 文章状态 -->
      <el-table-column label="状态" min-width="90" align="center">
        <template slot-scope="scope">
          <el-tag
            size="medium"
            type="success"
            v-if="scope.row.articleState === 1"
            effect="dark"
            >已发布</el-tag
          >
          <el-tag
            size="medium"
            type="primary"
            v-if="scope.row.articleState === 0"
            effect="dark"
            >草稿箱</el-tag
          >
        </template>
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

      <!-- 更新时间 -->
      <el-table-column
        label="更新时间"
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
      <el-table-column align="center" min-width="200">
        <template slot="header" slot-scope="scope">
          <el-input
            class="article-search"
            v-model="search"
            size="mini"
            placeholder="输入标题检索..."
          />
        </template>
        <template slot-scope="scope">
          <el-button-group>
            <!-- 编辑 -->
            <el-button
              type="primary"
              size="small"
              @click="goPublishArticle(scope.row.articleId)"
              >编辑</el-button
            >
            <!-- 查看 -->
            <el-button
              type="success"
              size="small"
              @click="goArticle(scope.row.articleId)"
              >查看</el-button
            >
            <!-- 删除 -->
            <el-popconfirm
              confirmButtonText="残忍删除"
              cancelButtonText="我再想想"
              icon="el-icon-info"
              iconColor="red"
              title="确定删除这一条信息吗？"
              @onConfirm="deleteArticle(scope.$index, scope.row)"
            >
              <el-button slot="reference" size="small" type="danger"
                >删除</el-button
              >
            </el-popconfirm>
          </el-button-group>
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
import axios from "axios";
import { Message } from "element-ui";
import { getRequest, postRequest } from "@/../untils/axiosApi";

export default {
  name: "articleManager",
  data() {
    return {
      pageSize: 8, // 分页长度
      currentPage: 1, // 当前页码
      totalPages: 0, // 总页数
      totalArticlesLen: 0, // 总文章长度
      search: "", // 文章标题搜索关键字
      articleInfo: [] // 文章信息集合
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    // 分页对象
    var page = { pageNum: 1, pageSize: 8 };

    //获取未被标记删除的文章
    axios.all([postRequest("/admin/article/not-delete/", page)]).then(
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
        // 文章信息集合
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
     * @description: 根据页码获取文章信息
     * @param {Number} currentPage
     * @return {void}
     */
    getArticlesByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: this.pageSize };

      postRequest("/admin/article/not-delete/", page).then(response => {
        this.setData(response);
      });
    },

    /**
     * @description: 进行更新文章操作，-1标记为已删除状态
     * @param {Number} index
     * @param {Map} row
     * @return {void}
     */
    deleteArticle(index, row) {
      postRequest("/admin/article/" + row.articleId + "/" + -1).then(
        response => {
          var successMessage = "文章删除成功！";
          var errorMessage = "文章删除失败！请稍后再试！";
          // 显示消息提示
          this.common.insertUpdateMessage(
            successMessage,
            errorMessage,
            response
          );
          // 回到当前页
          if (response.status == 201) {
            if ((this.totalArticlesLen - 1) % this.pageSize == 0) {
              this.getArticlesByCurrentPage(this.currentPage - 1);
            } else {
              this.getArticlesByCurrentPage(this.currentPage);
            }
          }
        }
      );
    },

    /**
     * @description: 跳转到文章详情页并传递文章ID
     * @param {Number} value
     * @return {void}
     */
    goArticle(value) {
      this.$router.push({
        name: "文章详情",
        params: { articleId: value }
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
.autoEnter {
  width: 300px;
  height: 95px;
  word-break: break-all;
}
</style>
