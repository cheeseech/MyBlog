<template>
  <div id="blogArchives" v-show="timeLine">
    <el-card
      class="box-card m-radius-small m-b-margin card-padded"
      v-for="(item, index) in timeLine"
      :key="index"
    >
      <div slot="header" class="clearfix">
        <svg
        class="icon m-svg-size"
        aria-hidden="true"
        style="font-size: 32px;"
      >
        <use xlink:href="#icon-nian"></use>
      </svg>
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
            <svg
            class="icon m-svg-size"
            aria-hidden="true"
            style="font-size: 30px;"
          >
            <use xlink:href="#icon-yue"></use>
          </svg>
              &nbsp;{{ m.months | MonthFormat }}
              <strong style="font-size: 22px;color: orange">&nbsp;(5)</strong>
          </template>
          <el-timeline class="m-t-margin-mini">
            <el-timeline-item
              size="large"
              color="#409EFF"
              v-for="(i, index) in m.info"
              :key="index"
              :timestamp="i.publish_time"
            >
            <svg
            class="icon m-svg-size"
            aria-hidden="true"
            style="font-size: 28px;"
          >
            <use xlink:href="#icon-ri"></use>
          </svg>
              <b>{{ i.title }}</b>

              <!--专栏-->
        <el-button
        type="primary"
        round
        class="btnPadded"
      style="margin-left: 10px;"
        >Java</el-button
      >
              <el-button
          type="warning"
          round
          style="display: inline-block;"
          class="btnPadded"
          >原创</el-button
        >
        
            </el-timeline-item>
          </el-timeline>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>

<script>
import { getRequest } from "../../untils/axiosApi";
import axios from "axios";

export default {
  name: "BlogArchives",
  data() {
    return {
      activeName: "",
      timeLine: null
    };
  },
  beforeRouteEnter(to,from, next){
    getRequest("/article/timeline/").then(response => {
        next(vm=>vm.setData(response))
    })
  },
  methods: {
      setData(response){
        if(response.status == 200){
          this.timeLine = response.data;
        }
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
