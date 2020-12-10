<template>
  <div id="category" v-show="categoryData">
    <el-table
      :data="categoryData"
      highlight-current-row
      @selection-change="handleSelectionChange()"
      class="m-single-category-table"
    >
      <!-- 专栏名称 -->
      <el-table-column label="专栏名称" sortable prop="name" min-width="70" align="center">
        <template slot-scope="scope">
          <span class="categpry-name">{{ scope.row.name }}</span>
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
      <el-table-column label="创建时间" prop="create_time" sortable align="center" min-width="160">
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
      <el-table-column label="点赞" sortable prop="likes" align="center">
        <template slot-scope="scope">
          <el-tag type="danger" effect="dark">{{ scope.row.likes }}</el-tag>
        </template>
      </el-table-column>

      <!-- 评论数 -->
      <el-table-column label="评论" sortable prop="comments" align="center">
        <template slot-scope="scope">
          <el-tag type="warning" effect="dark">{{ scope.row.comments }}</el-tag>
        </template>
      </el-table-column>
      
    </el-table>
  </div>
</template>
<script>
import { getRequest } from "@/../untils/axiosApi";
export default {
  data() {
    return {
      categoryData: null
    };
  },
  created() {
    //专栏数据获取
    this.getCategory();
  },
  methods: {
    //获取专栏数据
    getCategory() {
      getRequest("/admin/category/analysis/").then(response => {
        if (response.status == 200) {
          this.categoryData = response.data;
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
</style>
