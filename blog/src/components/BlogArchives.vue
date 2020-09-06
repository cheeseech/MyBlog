<template>
  <div id="blogArchives">
    <el-card
      class="box-card m-radius-small m-b-margin card-padded"
      v-for="(item, index) in timeLine"
      :key="index"
    >
      <div slot="header" class="clearfix">
        <span
          ><strong style="font-size: 24px">{{ item.years }}</strong></span
        >
        <span style="float: right; padding: 3px 0"
          >共
          <strong style="font-size: 24px;color: orange">{{
            item.counts
          }}</strong>
          篇文章</span
        >
      </div>

      <el-collapse v-model="activeName" accordion class="m-padded">
        <el-collapse-item
          :name="m.info[0].article_id"
          v-for="(m, index) in item.months"
          :key="index"
        >
          <template slot="title">
            <svg class="icon m-svg-size" aria-hidden="true" color="#F56C6C">
              <use xlink:href="#icon-shu"></use></svg>
              &nbsp;{{ m.months | MonthFormat }}
          </template>
          <el-timeline class="m-t-margin-mini">
            <el-timeline-item
              size="large"
              color="#409EFF"
              v-for="(i, index) in m.info"
              :key="index"
              :timestamp="i.publish_time"
            >
              <i
                class="el-icon-video-camera-solid"
                style="font-size: 22px;color:#409EFF"
              ></i>
              {{ i.title }}
            </el-timeline-item>
          </el-timeline>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>

<script>
import { getRequest } from "../../untils/axiosApi";

export default {
  name: "BlogArchives",
  data() {
    return {
      activeName: "",
      timeLine: null
    };
  },
  created() {
    this.getTimeLine();
  },
  methods: {
    getTimeLine() {
      getRequest("/article/timeline/").then(response => {
        if (response.status == 200) {
          this.timeLine = response.data;
          console.log(this.timeLine);
        }
      });
    },
    monthGroup(month, title) {
      var date1 = new Date(month);
      var date2 = new Date(title);

      var months = date1.getMonth();
      var titles = date2.getMonth();
      if (months == titles) {
        return true;
      }
    }
  },
  filters: {
    MonthFormat: function(value) {
      var months = [
        "",
        "一月",
        "二月",
        "三月",
        "四月",
        "五月",
        "六月",
        "七月",
        "八月",
        "九月",
        "十月",
        "十一月",
        "十二月"
      ];
      var month = months[value];

      return `${month}`;
    }
  }
};
</script>

<style></style>
