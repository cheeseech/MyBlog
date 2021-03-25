<!--
 * @FileDescription: 后台管理欢迎组件
 * @Author: 徐茂华
 * @Date: 2020-10-27 08:36:03
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-10 09:47:26
 * @FilePath: \src\views\admin\AdminWelcome.vue
-->
<template>
  <div id="welcome" v-if="days">
    <!-- 文章总数据 -->
    <totalArticleData :total_data="totalArticleData"></totalArticleData>
    <!-- echarts图表 -->
    <el-card class="card">
      <div id="tu"></div>
    </el-card>
  </div>
</template>
<script>
import axios from "axios";
import { getRequest } from "@/../untils/axiosApi";
import totalArticleData from "@/components/TotalArticleData";

export default {
  name: "welcome",
  data() {
    return {
      days: [], // 近一月文章数据集合
      views: [], // 总浏览数
      comments: [], // 总评论数
      totalArticleData: [] // 文章总数据集合
    };
  },
  beforeRouteEnter(to, from, next) {
    //设置时间为近30天
    const end = new Date();
    const start = new Date();
    start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);

    //根据时间获取每天文章数据
    axios
      .all([
        getRequest("/admin/daysEcharts/" + start + "/" + end),
        getRequest("/admin/article/totalData/")
      ])
      .then(
        axios.spread((monthsResponse, totalDataResponse) => {
          next(vm => {
            vm.setData(monthsResponse, totalDataResponse);
          });
        })
      );
  },
  watch: {
    //days变化时初始化echarts图表
    days() {
      this.$nextTick(() => {
        this.myEcharts();
      });
    }
  },
  methods: {
    /**
     * @description: 处理总文章数据及近一月文章数据
     * @param {Map} monthsResponse
     * @param {Map} totalDataResponse
     * @return {void}
     */
    setData(monthsResponse, totalDataResponse) {
      //近一月文章数据
      if (monthsResponse.status == 200) {
        //每天文章数据
        this.days = monthsResponse.data.days;
        //总浏览量
        this.views = monthsResponse.data.views;
        //总评论数
        this.comments = monthsResponse.data.comments;
      }

      //文章总数据
      if (totalDataResponse.status == 200) {
        this.totalArticleData = totalDataResponse.data;
      }
    },

    /**
     * @description: 生成Echarts图表
     * @return {void}
     */
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
          data: ["评论数", "浏览量"]
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
  components: { totalArticleData }
};
</script>
<style>
#welcome {
  padding: 20px;
}
.card {
  margin-top: 40px;
}
#tu {
  width: 95%;
  height: 400px;
}
</style>
