<!--
 * @FileDescription: 博客归档组件
 * @Author: 徐茂华
 * @Date: 2020-08-08 18:08:24
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-05 16:31:01
 * @FilePath: \src\views\blog\BlogArchives.vue
-->
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
              class="m-title"
              v-for="(i, index) in m.info"
              :key="index"
              :timestamp="i.publish_time | dateTimeFormat"
            >
              <!-- 标题 -->
              <svg class="icon m-svg-size-big" aria-hidden="true">
                <use xlink:href="#icon-ri"></use>
              </svg>
              <a @click="goArticleInfo(i.article_id)">{{ i.title }}</a>

              <!--专栏-->
              <el-button type="primary" round class="btnPadded cate-button">{{
                i.cate_name
              }}</el-button>
              <!-- 类型 -->
              <el-button type="warning" round class="btnPadded type-button">{{
                i.type_name
              }}</el-button>
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
      activeName: "", // 选中下拉
      timeLine: null // 时间线相关数据
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    getRequest("/article/timeline/").then(response => {
      next(vm => vm.setData(response));
    });
  },
  methods: {
    /**
     * @description: 时间线相关数据处理
     * @param {Map} response
     * @return void
     */
    setData(response) {
      if (response.status == 200) {
        this.timeLine = response.data;
      }
    },

    /**
     * @description: 跳转到文章详情页并传递文章ID
     * @param {Number} value
     * @return void
     */
    goArticleInfo(value) {
      this.$router.push({
        name: "文章详情",
        params: { articleId: value }
      });
    }
  }
};
</script>
<style scoped>
.m-title a {
  color: #000;
  cursor: pointer;
  font-weight: bold;
  font-size: 17px;
}
.m-title a:hover {
  color: #3476d2;
}
.type-button,
.cate-button {
  cursor: default;
}
.type-button:focus,
.type-button:hover {
  color: #fff;
  background-color: #e6a23c;
  border-color: #e6a23c;
}
.cate-button:focus,
.cate-button:hover {
  color: #fff;
  background-color: #409eff;
  border-color: #409eff;
}
</style>
