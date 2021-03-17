<!--
 * @FileDescription: 后台数据单篇文章分析组件
 * @Author: 徐茂华
 * @Date: 2020-11-22 09:10:20
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-17 15:53:30
 * @FilePath: \src\views\admin\adminData\components\SingleArticleAnalysis.vue
-->
<template>
  <div id="single" v-show="articleData">
    <!-- 文章日期数据筛选 -->
    <div class="m-all-single-picker">
      <span>文章数据报表：</span>
      <el-date-picker
        v-model="timesTables"
        type="daterange"
        align="right"
        unlink-panels
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :picker-options="pickerOptions"
      >
      </el-date-picker>
    </div>

    <el-divider></el-divider>

    <!-- 文章数据表格 -->
    <el-table
      :data="articleData"
      highlight-current-row
      class="m-single-category-table"
    >
      <!-- 文章标题 -->
      <el-table-column label="文章标题" sortable prop="title" min-width="150">
        <template slot-scope="scope">
          <span class="article-title">{{ scope.row.title }}</span>
        </template>
      </el-table-column>

      <!-- 专栏名称 -->
      <el-table-column label="专栏名称" prop="cateName" min-width="60">
        <template slot-scope="scope">
          <span class="article-title">{{ scope.row.cateName }}</span>
        </template>
      </el-table-column>

      <!-- 创建日期 -->
      <el-table-column
        label="创建日期"
        sortable
        prop="publishTime"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 创建日期 -->
      <el-table-column
        label="更新日期"
        sortable
        prop="updateTime"
        align="center"
      >
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 浏览量 -->
      <el-table-column label="浏览" sortable prop="views" align="center">
        <template slot-scope="scope">
          <el-tag type="primary" effect="dark">{{ scope.row.views }}</el-tag>
        </template>
      </el-table-column>

      <!-- 评论数 -->
      <el-table-column label="评论" prop="comments" sortable align="center">
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
        :total="articlesLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getArticlesByCurrentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import { postRequest } from "@/../untils/axiosApi";
export default {
  name: "single",
  data() {
    return {
      flag: 0, // 避免首次显示消息提示
      pageSize: 6, // 分页长度
      articlesLen: 0, // 文章总数
      currentPage: 1, // 当前页码
      articleData: null, // 单篇文章数据集合
      timesTables: "", // 当前选择时间范围
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6;
        },
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      }
    };
  },
  watch: {
    /**
     * @description: 当时间变化时根据选择时间获取数据
     * @return {void}
     */
    timesTables: function() {
      this.getArticlesByCurrentPage(1);
    }
  },
  mounted() {
    //初始化时间
    this.setTime();
  },
  methods: {
    /**
     * @description: 时间初始化为近90天
     * @return {void}
     */
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.timesTables = [start, end];
    },

    /**
     * @description: 根据页码获取文章数据集合
     * @param {Number} currentPage
     * @return {void}
     */
    getArticlesByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: 6 };

      postRequest(
        "/admin/days/single/" + this.timesTables[0] + "/" + this.timesTables[1],
        page
      ).then(response => {
        if (response.status == 200) {
          this.articleData = response.data.content;
          this.currentPage = response.data.pageNum;
          this.articlesLen = response.data.totalSize;
        }

        // 首次不显示消息提示
        this.flag++;
        if (this.flag > 1) {
          this.common.showMessage(response);
        }
      });
    }
  }
};
</script>
<style>
.article-title {
  font-size: 17px;
  font-weight: bold;
}
.m-paging {
  margin-left: 4%;
}
</style>
