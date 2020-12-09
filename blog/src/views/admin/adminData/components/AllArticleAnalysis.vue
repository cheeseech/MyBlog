<template>
  <div id="allArticleAnalysis" v-if="days">
    <!-- 筛选数据 -->
    <div class="m-all-single-picker">
      <span>数据筛选：</span>
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

    <!-- Echarts图表 -->
    <el-tabs v-model="activeName" class="m-all-tabs">
      <!-- 浏览量图表 -->
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

      <!-- 点赞数图表 -->
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

      <!-- 评论数图表 -->
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
import { Message } from "element-ui";
import { getRequest } from "@/../untils/axiosApi";
import charts from "@/views/admin/adminData/components/Echarts";
export default {
  data() {
    return {
      days: null,
      likes: [],
      views: [],
      comments: [],
      activeName: "views",
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
    //时间变化时根据选择时间获取数据
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
    //页面初始化时设置时间
    this.setTime();
  },
  methods: {
    //设置时间为近90天
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.times = [start, end];
      this.timesEcharts = [start, end];
    },
    //数据处理
    setData(response) {
      if (response.status == 200) {
        //日期数据
        this.days = response.data.days;
        //点赞数数据
        this.likes = response.data.likes;
        //浏览量数据
        this.views = response.data.views;
        //评论数数据
        this.comments = response.data.comments;
        Message.success(response.msg);
      } else {
        this.days = "";
        this.likes = "";
        this.views = "";
        this.comments = "";
        Message.error(response.msg);
      }
    }
  },
  components: { charts }
};
</script>
<style>
#tab-single,
#tab-all,
#tab-category {
  font-size: 20px;
  font-weight: bold;
}
.m-all-tabs {
  margin-top: 20px;
  margin-left: 40px;
  margin-right: 40px;
}
</style>
