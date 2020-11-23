<template>
  <div id="category" v-show="categoryData">
    <el-table
      :data="categoryData"
      highlight-current-row
      @selection-change="handleSelectionChange()"
      style="margin-top:20px;width:90%;margin-left: 5%;margin-bottom: 25px;"
    >
      <el-table-column
        label="专栏名称"
        sortable
        min-width="70"
        align="center"
      >
      <template slot-scope="scope">
        <el-tag size="medium" type="primary" effect="dark">{{ scope.row.name }}</el-tag>
      </template>
      </el-table-column>
      <el-table-column
        label="专栏概述"
        prop="summary"
        show-overflow-tooltip
        min-width="250"
        align="center"
      >
      </el-table-column>
      <el-table-column label="创建时间" sortable align="center" min-width="160">
        <template slot-scope="scope">
          <span>{{ scope.row.create_time | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column label="文章数" prop="articles" sortable align="center">
      </el-table-column>
      <el-table-column label="浏览" prop="views" sortable align="center">
      </el-table-column>
      <el-table-column label="点赞" prop="likes" sortable align="center">
      </el-table-column>
      <el-table-column label="评论" prop="comments" sortable align="center">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { getRequest } from "../../../../untils/axiosApi";
export default {
  data() {
    return {
      categoryData: null
    };
  },
  created() {
    this.getCategory();
  },
  methods: {
    getCategory() {
      getRequest("/admin/category/analysis/").then(response => {
        if (response.status == 200) {
          this.categoryData = response.data;
          console.log(this.categoryData);
        }
      });
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

      return `${years}年${months}月${days}日 ${hours}:${minutes}:${seconds}`;
    }
  }
};
</script>
