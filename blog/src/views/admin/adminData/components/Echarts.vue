<!--
 * @FileDescription: Echarts图表组件
 * @Author: 徐茂华
 * @Date: 2020-11-12 16:01:04
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-17 15:44:09
 * @FilePath: \src\views\admin\adminData\components\Echarts.vue
-->
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
      chart: null // 图表实例
    };
  },
  watch:{

    /**
       * @description: 当日期数据变化时重新渲染图表
       * @return {void}
       */
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
    
    /**
     * @description: 图表初始化
     * @return {void}
     */
    initChart() {
      this.chart = this.$echarts.init(this.$el);
      this.setOptions();
    },

    /**
     * @description: 图表参数设置
     * @return {void}
     */
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
