<template>
  <div id="welcome" style="padding:20px;" v-if="days">
    <totalArticleData></totalArticleData>

    <el-card style="margin-top:40px">
      <div id="tu" style="width:95%;height: 400px;"></div>
    </el-card>
  </div>
</template>
<script>
import { getRequest } from "../../../untils/axiosApi";
import totalArticleData from "./TotalArticleData"
import axios from "axios";

export default {
  name: "welcome",
  data() {
    return {
      likes: [],
      views: [],
      comments: [],
      days: []
    };
  },
  beforeRouteEnter(to, from, next) {
      const end=new Date();
      const start=new Date();
      start.setTime(start.getTime()-3600*1000*24*30);
      getRequest("/admin/daysEcharts/"+start+"/"+end).then(
        monthsResponse => {
          next(vm => {
            vm.setData(monthsResponse);
          });
        })
  },
  watch: {
    days() {
      this.$nextTick(() => {
        this.myEcharts();
      });
    }
  },
  methods: {
    setData(monthsResponse) {
      if (monthsResponse.status == 200) {
        this.days = monthsResponse.data.days;
        this.likes = monthsResponse.data.likes;
        this.views = monthsResponse.data.views;
        this.comments = monthsResponse.data.comments;
      }
    },
    myEcharts() {
      // 基于准备好的dom，初始化echarts实例
      var myChart = this.$echarts.init(document.getElementById("tu"), "light");
      var _this = this;

      // 指定图表的配置项和数据
      var option = {
        title: {
          text: "近一月文章数据详情"
        },
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985"
            }
          }
        },
        dataZoom: [
          {
            show: true,
            realtime: true,
            start: 65,
            end: 85
          },
          {
            type: "inside",
            realtime: true,
            start: 45,
            end: 85
          }
        ],

        legend: {
          data: ["评论数", "点赞数", "浏览量"]
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "10%",
          containLabel: true
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            data: _this.days
          }
        ],
        yAxis: [
          {
            type: "value"
          }
        ],
        series: [
          {
            name: "评论数",
            type: "line",
            stack: "总量",
            areaStyle: {},
            data: _this.comments
          },
          {
            name: "点赞数",
            type: "line",
            stack: "总量",
            areaStyle: {},
            data: _this.likes
          },
          {
            name: "浏览量",
            type: "line",
            stack: "总量",
            label: {
              normal: {
                show: true,
                position: "top"
              }
            },
            areaStyle: {},
            data: _this.views
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      myChart.setOption(option);
    }
  },
  components: {
      totalArticleData: totalArticleData
  }
};
</script>
<style scoped></style>
