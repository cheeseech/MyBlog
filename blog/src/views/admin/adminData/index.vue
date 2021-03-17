<!--
 * @FileDescription: 后台管理数据分析主组件
 * @Author: 徐茂华
 * @Date: 2020-11-11 16:19:54
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-14 14:46:08
 * @FilePath: \src\views\admin\adminData\index.vue
-->
<template>
  <div
    id="dataManager"
    v-if="echartsViews"
    style="padding:20px;background-color:#fff"
  >
    <!-- 所有文章数据统计 -->
    <totalArticleData :total_data="totalArticleData"></totalArticleData>

    <!-- 全部/单篇/专栏文章分析 -->
    <el-tabs type="border-card" v-model="activeName" class="m-tabs">
      <el-tab-pane label="全部文章分析" name="all">
        <allArticleAnalysis
          v-show="'all' === activeName"
          :echarts_views="echartsViews"
          :echarts_days="echartsDays"
        ></allArticleAnalysis>
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

    <!-- 日期数据筛选 -->
    <div class="m-date-picker">
      <span>日期数据报表：</span>
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

    <!-- 日期数据表格 -->
    <el-table :data="daysData" highlight-current-row class="m-analysis-table">
      <!-- 日期 -->
      <el-table-column label="日期" sortable prop="days" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.days | dateFormat }}</span>
        </template>
      </el-table-column>

      <!-- 浏览数 -->
      <el-table-column label="浏览" prop="views" sortable align="center">
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
        :total="daysDataLen"
        :page-size="pageSize"
        :current-page="currentPage"
        @current-change="getDaysByCurrentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>
<script>
import axios from "axios";
import totalArticleData from "@/components/TotalArticleData";
import { getRequest, postRequest } from "@/../untils/axiosApi";
import categoryAnalysis from "@/views/admin/adminData/components/CategoryAnalysis";
import allArticleAnalysis from "@/views/admin/adminData/components/AllArticleAnalysis";
import singleArticleAnalysis from "@/views/admin/adminData/components/SingleArticleAnalysis";

export default {
  data() {
    return {
      pageSize: 7, // 分页长度
      currentPage: 1, // 当前页码
      daysDataLen: 0, // 日期数据长度
      flag: -1, // 避免首次显示消息提示
      times: "", // 当前选择时间范围
      daysData: null, // 日期文章数据集合
      echartsDays: null, // echarts日期数据
      echartsViews: null, // echarts浏览量数据
      activeName: "all", // 当前选择Tab页
      totalArticleData: [], // 总文章数据
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
  beforeRouteEnter(to, from, next) {
    //初始化分页查询
    var page = { pageNum: 1, pageSize: 10 };
    //设置时间为近30天
    const end = new Date();
    const start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);

    //根据时间获取数据
    axios
      .all([
        postRequest("/admin/daysData/" + start + "/" + end, page),
        getRequest("/admin/daysEcharts/" + start + "/" + end),
        getRequest("/admin/article/totalData/")
      ])
      .then(
        axios.spread((daysDataResponse, echartsResponse, totalDataResponse) => {
          next(vm => {
            vm.setData(daysDataResponse, echartsResponse, totalDataResponse);
          });
        })
      );
  },
  watch: {
    /**
     * @description: 时间变化时根据选择时间获取数据
     * @return {void}
     */
    times: function() {
      this.getDaysByCurrentPage(1);
    }
  },
  mounted() {
    //页面初始化设置时间
    this.setTime();
  },
  methods: {
    /**
     * @description: 处理日期数据
     * @param {Map} daysDataResponse
     * @return {void}
     */
    setDaysData(daysDataResponse) {
      if (daysDataResponse.status == 200) {
        this.daysData = daysDataResponse.data.content;
        this.daysDataLen = daysDataResponse.data.totalSize;
        this.currentPage = daysDataResponse.data.pageNum;
      }

      // 首次不显示消息提示
      this.flag++;
      if (this.flag > 1) {
        this.common.showMessage(daysDataResponse);
      }
    },

    /**
     * @description: 处理相关数据
     * @param {Map} daysDataResponse
     * @param {Map} echartsResponse
     * @param {Map} totalDataResponse
     * @return {void}
     */
    setData(daysDataResponse, echartsResponse, totalDataResponse) {
      // 处理Echarts浏览量数据
      if (echartsResponse.status == 200) {
        this.echartsViews = echartsResponse.data.views;
        this.echartsDays = echartsResponse.data.days;
      }

      // 处理文章总数据
      if (totalDataResponse.status == 200) {
        this.totalArticleData = totalDataResponse.data;
      }

      // 处理日期数据
      this.setDaysData(daysDataResponse);
    },

    /**
     * @description: 初始化时间为近30天
     * @return {void}
     */
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);

      this.times = [start, end];
    },

    /**
     * @description: 根据页码获取日期数据
     * @param {Number} currentPage
     * @return {void}
     */
    getDaysByCurrentPage(currentPage) {
      // 分页对象
      var page = { pageNum: currentPage, pageSize: 7 };

      postRequest(
        "/admin/daysData/" + this.times[0] + "/" + this.times[1],
        page
      ).then(response => {
        this.setDaysData(response);
      });
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
.m-tabs {
  margin-top: 25px;
}
.m-date-picker {
  margin-top: 40px;
  margin-left: 40px;
}
.m-date-picker span {
  font-size: 18px;
  font-weight: bold;
}
.m-analysis-table {
  margin-top: 20px;
  width: 84%;
  margin-left: 8%;
}
</style>
<style scoped>
.m-paging {
  margin-left: 17%;
  margin-top: 1%;
}
</style>
