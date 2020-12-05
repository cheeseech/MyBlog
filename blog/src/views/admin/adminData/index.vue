<template>
  <div
    id="dataManager"
    style="padding:20px;background-color:#fff"
    v-if="daysData"
  >
    <!-- 所有文章数据统计 -->
    <totalArticleData></totalArticleData>

    <el-tabs type="border-card" v-model="activeName" style="margin-top:25px;">
      <el-tab-pane label="全部文章分析" name="all">
        <allArticleAnalysis v-show="'all' === activeName"></allArticleAnalysis>
      </el-tab-pane>

      <el-tab-pane label="单篇文章分析" name="single">
        <singleArticleAnalysis
          v-show="'single' === activeName"
        ></singleArticleAnalysis>
      </el-tab-pane>

      <el-tab-pane label="专栏数据分析" name="category">
        <categoryAnalysis v-show="'category' === activeName"></categoryAnalysis>
      </el-tab-pane>
    </el-tabs>

    <!-- 日期数据统计 -->
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
import axios from "axios";
import { Message } from "element-ui";
import { getRequest } from "@/../untils/axiosApi";
import totalArticleData from "@/components/TotalArticleData";
import categoryAnalysis from "@/views/admin/adminData/components/CategoryAnalysis";
import allArticleAnalysis from "@/views/admin/adminData/components/AllArticleAnalysis";
import singleArticleAnalysis from "@/views/admin/adminData/components/SingleArticleAnalysis";

export default {
  data() {
    return {
      times: "",
      daysData: null,
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
    //时间变化时根据选择时间获取数据
    times: function() {
      getRequest("/admin/daysData/" + this.times[0] + "/" + this.times[1]).then(
        response => {
          this.setData(response);
        }
      );
    }
  },
  mounted() {
    //页面初始化设置时间
    this.setTime();
  },
  methods: {
    //数据处理
    setData(response) {
      if (response.status == 200) {
        this.daysData = response.data;
      } else {
        this.daysData = "";
      }
    },
    //表格行数据不为0时添加背景色
    tableRowClassName({ row, rowIndex }) {
      if (row.views !== 0 || row.likes !== 0 || row.comments !== 0) {
        return "warning-row";
      }
      return "";
    },
    //设置时间为近90天
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.times = [start, end];
    }
  },
  components: {
    allArticleAnalysis,
    totalArticleData,
    singleArticleAnalysis,
    categoryAnalysis
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
