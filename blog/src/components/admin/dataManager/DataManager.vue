<template>
  <div
    id="dataManager"
    style="padding:20px;background-color:#fff"
    v-if="daysData"
  >
    <totalArticleData></totalArticleData>

    <el-tabs type="border-card" v-model="activeName" style="margin-top:25px;">
      <el-tab-pane label="全部文章分析" name="all">
        <allArticleAnalysis v-if="'all' === activeName"></allArticleAnalysis>
      </el-tab-pane>
      <el-tab-pane label="单篇文章分析" name="single">
        <singleArticleAnalysis
          v-if="'single' === activeName"
        ></singleArticleAnalysis>
      </el-tab-pane>
      <el-tab-pane label="专栏数据分析" name="category">
        <categoryAnalysis v-if="'category' === activeName"></categoryAnalysis>
      </el-tab-pane>
    </el-tabs>

    <div style="margin-top:40px;margin-left: 40px;">
      <span style="font-size:18px;font-weight: bold">日期数据报表：</span>
      <el-date-picker
        v-model="times"
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

    <el-table
      :data="daysData"
      highlight-current-row
      @selection-change="handleSelectionChange()"
      :row-class-name="tableRowClassName"
      style="margin-top:20px;width:84%;margin-left: 8%;"
    >
      <el-table-column label="日期" sortable align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.days | dateFormat }}</span>
        </template>
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
import { Message } from "element-ui";
import allArticleAnalysis from "./AllArticleAnalysis";
import singleArticleAnalysis from "./SingleArticleAnalysis";
import categoryAnalysis from "./CategoryAnalysis";
import totalArticleData from "../TotalArticleData";
import axios from "axios";

export default {
  data() {
    return {
      daysData: null,
      times: "",
      activeName: "all",
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
    times: function() {
      getRequest("/admin/daysData/" + this.times[0] + "/" + this.times[1]).then(
        response => {
          this.setData(response);
        }
      );
    }
  },
  mounted() {
    this.setTime();
  },
  methods: {
    setData(response) {
      if (response.status == 200) {
        this.daysData = response.data;
      } else {
        this.daysData = "";
      }
    },
    tableRowClassName({ row, rowIndex }) {
      if (row.views !== 0 || row.likes !== 0 || row.comments !== 0) {
        return "warning-row";
      }
      return "";
    },
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.times = [start, end];
    }
  },
  components: {
    allArticleAnalysis: allArticleAnalysis,
    totalArticleData: totalArticleData,
    singleArticleAnalysis: singleArticleAnalysis,
    categoryAnalysis: categoryAnalysis,
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

      return `${years}年${months}月${days}日`;
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
.el-table .warning-row {
  background: oldlace;
}
</style>
