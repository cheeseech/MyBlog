<template>
  <div id="allArticleAnalysis" v-if="days">
    <div style="margin-top:20px;margin-left:40px;">
      <span style="font-size: 19px;font-weight:bold">数据筛选：</span>
      <el-date-picker
        v-model="timesEcharts"
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
    <el-tabs
      v-model="activeName"
      @tab-click="handleClick"
      style="margin-top:20px;margin-left: 40px;margin-right: 40px;"
    >
      <el-tab-pane label="浏览量" name="views">
        <charts
          v-if="'views' === activeName"
          :e_width="90"
          :e_height="380"
          :days="days"
          :data="views"
          >views</charts
        >
      </el-tab-pane>
      <el-tab-pane label="点赞数" name="likes">
        <charts
          v-if="'likes' === activeName"
          :e_width="90"
          :e_height="380"
          :days="days"
          :data="likes"
          >likes</charts
        >
      </el-tab-pane>
      <el-tab-pane label="评论数" name="comments">
        <charts
          v-if="'comments' === activeName"
          :e_width="90"
          :e_height="380"
          :days="days"
          :data="comments"
          >comments</charts
        >
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { getRequest } from "../../../../untils/axiosApi";
import { Message } from "element-ui";
import charts from "./Echarts";
export default {
  data() {
    return {
      activeName: "views",
      days: null,
      likes: [],
      views: [],
      comments: [],
      timesEcharts: "",
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
    timesEcharts: function() {
      getRequest(
        "/admin/daysEcharts/" +
          this.timesEcharts[0] +
          "/" +
          this.timesEcharts[1]
      ).then(response => {
        this.setData(response);
      });
    }
  },
  mounted() {
    this.setTime();
  },
  methods: {
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.times = [start, end];
      this.timesEcharts = [start, end];
    },
    setData(response) {
      if (response.status == 200) {
        this.days = response.data.days;
        this.likes = response.data.likes;
        this.views = response.data.views;
        this.comments = response.data.comments;
      } else {
        this.days = "";
        this.likes = "";
        this.views = "";
        this.comments = "";
      }
    },
    handleClick(tab, event) {}
  },
  components: {
    charts: charts
  }
};
</script>
