<!--
 * @FileDescription: 博客词云组件
 * @Author: 徐茂华
 * @Date: 2020-09-19 20:06:11
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:41:55
 * @FilePath: \src\components\WordCloud.vue
-->
<template>
  <div
    :id="wc_id"
    :style="{ height: wc_height + 'px', width: wc_width + 'px' }"
  ></div>
</template>
<script>
// 引入echarts-wordCloud
var echarts = require("echarts");
require("echarts-wordcloud");

export default {
  name: "wordCloud",
  props: ["wc_id", "wc_height", "wc_width", "wc_data"],
  data() {
    return {
      myChart: null //词云组件ID
    };
  },
  mounted() {
    //初始化
    this.initChart();
  },
  watch: {
    wc_data: function() {
      //重新渲染词云
      this.myChart.setOption({
        series: [
          {
            type: "wordCloud",
            data: this.wc_data
          }
        ]
      });
    }
  },
  methods: {
    /**
     * @description: 初始化词云
     * @return {void}
     */
    initChart() {
      const _this = this;
      this.myChart = echarts.init(document.getElementById(this.wc_id));
      const option = {
        //背景颜色
        backgroundColor: "#fff",
        series: [
          {
            type: "wordCloud",
            //词云形状
            shape: "square",
            //词之间的距离
            gridSize: 4,
            //词的大小范围
            sizeRange: [12, 50],
            //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
            // rotationRange: [-45, 0, 45, 90],
            // rotationRange: [ 0,90],
            rotationRange: [-45, 90],
            //是否允许部分单词在画布之外绘制
            drawOutOfBound: false,
            //随机生成字体颜色
            textStyle: {
              normal: {
                fontFamily: "sans-serif",
                fontWeight: "bold",
                color: function() {
                  return (
                    "rgb(" +
                    Math.round(Math.random() * 255) +
                    ", " +
                    Math.round(Math.random() * 255) +
                    ", " +
                    Math.round(Math.random() * 255) +
                    ")"
                  );
                }
              },
              //鼠标悬停的颜色
              emphasis: {
                shadowBlur: 5,
                shadowColor: "#E6A23C"
              }
            },
            //位置相关设置
            //紧随左上下宽高右下的位置用于放置单词cloud,默认放置在中间，大小为75％x 80％。
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "100%",
            height: "100%",
            //数据
            data: this.wc_data
          }
        ]
      };
      this.myChart.setOption(option);
      // 点击某个单词
      this.myChart.on("click", function(params) {
        //发送标签名到父组件中
        _this.$emit("sendTagName", params.data.name);
      });
    }
  }
};
</script>
