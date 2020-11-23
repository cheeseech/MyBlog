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
      days: function() {
        // console.log(this.days)
        this.initChart();
      }
  },
  mounted: function() {
    this.initChart();
  },
  beforeDestroy: function() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    setOptions: function() {
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
            data: _this.data
          }
        ]
      });
    },
    initChart: function() {
      this.chart = this.$echarts.init(this.$el);
      this.setOptions();
    }
  }
};
</script>
