<template>
  <div id="blogArchives" v-show="timeLine">
    <el-card
      class="box-card m-radius-small m-b-margin card-padded"
      v-for="(item, index) in timeLine"
      :key="index"
    >
      <!-- 每年共有几篇文章 -->
      <div slot="header" class="clearfix">
        <!-- 年份 -->
        <svg class="icon m-svg-size m-font-size-large" aria-hidden="true">
          <use xlink:href="#icon-nian"></use>
        </svg>
        <span
          ><strong class="m-font-size-big">{{ item.years }}</strong></span
        >
        <!-- 篇数 -->
        <span class="m-span-right-pd"
          >共
          <strong class="m-font-size-big-orange">{{ item.counts }}</strong>
          篇文章</span
        >
      </div>

      <el-collapse v-model="activeName" accordion class="m-padded">
        <el-collapse-item
          :name="m.info[0].article_id"
          v-for="(m, index) in item.months"
          :key="index"
        >
          <!-- 每月共有几篇文章 -->
          <template slot="title">
            <!-- 月份 -->
            <svg class="icon m-svg-size-biger" aria-hidden="true">
              <use xlink:href="#icon-yue"></use>
            </svg>
            &nbsp;{{ m.months | monthsFormat }}
            <!-- 篇数 -->
            <strong class="m-font-size-normal-orange"
              >&nbsp;({{ m.monthCount }})</strong
            >
          </template>
          <!-- 文章列表遍历 -->
          <el-timeline class="m-t-margin-mini">
            <el-timeline-item
              size="large"
              color="#409EFF"
              v-for="(i, index) in m.info"
              :key="index"
              :timestamp="i.publish_time | dateTimeFormat"
            >
              <!-- 标题 -->
              <svg class="icon m-svg-size-big" aria-hidden="true">
                <use xlink:href="#icon-ri"></use>
              </svg>
              <b>{{ i.title }}</b>

              <!--专栏-->
              <el-button type="primary" round class="btnPadded cate-margin-left"
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
import axios from "axios";
import { getRequest } from "@/../untils/axiosApi";

export default {
  name: "BlogArchives",
  data() {
    return {
      activeName: "",
      timeLine: null
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    getRequest("/article/timeline/").then(response => {
      next(vm => vm.setData(response));
    });
  },
  methods: {
    //数据处理
    setData(response) {
      if (response.status == 200) {
        this.timeLine = response.data;
      }
    }
  }
};
</script>
<style scoped>
.cate-margin-left {
  margin-left: 10px;
}
</style>
