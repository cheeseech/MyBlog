<template>
  <div
    :style="{ width: e_width + '%', height: e_height + 'px' }"
    style="margin-top: 20px;margin-left: 25px;margin-bottom: 20px;"
  ></div>
</template>
<script>
export default {
  name: "echarts",
  props: ["e_width", "e_height", "days", "data"],
  data() {
    return {
      chart: null
    };
  },
  watch:{
    //当日期数据变化时重新渲染图表
      days() {
        this.initChart();
      }
  },
  mounted() {
    //图表初始化
    this.initChart();
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    //图表初始化
    initChart() {
      this.chart = this.$echarts.init(this.$el);
      this.setOptions();
    },
    //图表参数设置
    setOptions() {
      var _this = this;
      this.chart.setOption({
        title: { text: "每天数据详情" },
        tooltip: {},
        xAxis: {
          data: _this.days
        },
        dataZoom: [
          {
            show: true,
            realtime: true
          },
          {
            type: "inside",
            realtime: true,
            start: 45,
            end: 85
          }
        ],
        grid: {
          left: "3%",
          right: "4%",
          bottom: "15%",
          containLabel: true
        },
        yAxis: {},
        series: [
          {
            type: "line",
            step: "strat",
            //数据
            data: _this.data
          }
        ]
      });
    }
    
  }
};
</script>
