<!--
 * @FileDescription: 后台数据专栏文章分析组件
 * @Author: 徐茂华
 * @Date: 2020-11-22 15:33:09
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-08 10:35:24
 * @FilePath: \src\views\admin\adminData\components\CategoryAnalysis.vue
-->
<template>
  <div id="category" v-show="categoryData">
    <el-table
      :data="categoryData"
      highlight-current-row
      @selection-change="handleSelectionChange()"
      class="m-single-category-table"
    >
      <!-- 专栏名称 -->
      <el-table-column
        label="专栏名称"
        sortable
        prop="name"
        min-width="70"
        align="center"
      >
        <template slot-scope="scope">
          <span class="category-name">{{ scope.row.name }}</span>
        </template>
      </el-table-column>

      <!-- 专栏概述 -->
      <el-table-column
        label="专栏概述"
        show-overflow-tooltip
        min-width="300"
        align="center"
      >
        <template slot-scope="scope">
          <span style="font-size:16px;">{{ scope.row.summary }}</span>
        </template>
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column
        label="创建时间"
        prop="create_time"
        sortable
        align="center"
        min-width="160"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.create_time | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 文章数 -->
      <el-table-column label="文章数" sortable prop="articles" align="center">
        <template slot-scope="scope">
          <el-tag type="success" effect="dark">{{ scope.row.articles }}</el-tag>
        </template>
      </el-table-column>

      <!-- 浏览量 -->
      <el-table-column label="浏览" sortable prop="views" align="center">
        <template slot-scope="scope">
          <el-tag type="primary" effect="dark">{{ scope.row.views }}</el-tag>
        </template>
      </el-table-column>

      <!-- 点赞数 -->
      <!-- <el-table-column label="点赞" sortable prop="likes" align="center">
        <template slot-scope="scope">
          <el-tag type="danger" effect="dark">{{ scope.row.likes }}</el-tag>
        </template>
      </el-table-column> -->

      <!-- 评论数 -->
      <el-table-column label="评论" sortable prop="comments" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" effect="dark">{{ scope.row.comments }}</el-tag>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="m-paging">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="categoryLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getCategoryByCurrentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { postRequest } from "@/../untils/axiosApi";
export default {
  data() {
    return {
      pageSize: 7, // 分页长度
      currentPage: 1, // 当前页码
      categoryLen: 0, // 专栏数据长度
      categoryData: null // 专栏分析数据集合
    };
  },
  created() {
    //专栏数据获取
    this.getCategory();
  },
  methods: {
    /**
     * @description: 获取专栏分析相关数据
     * @return {void}
     */
    getCategory() {
      this.getCategoryByCurrentPage(1);
    },

    /**
     * @description: 根据页码获取专栏分析相关数据
     * @param {Number} currentPage
     * @return {void}
     */
    getCategoryByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: 7 };

      postRequest("/admin/category/analysis/", page).then(response => {
        if (response.status == 200) {
          this.categoryData = response.data.content;
          this.categoryLen = response.data.totalSize;
          this.currentPage = response.data.pageNum;
        }
      });
    }
  }
};
</script>
<style>
.category-name {
  font-size: 17px;
  font-weight: bold;
}
.m-paging {
  margin-left: 4%;
}
</style>
