<template>
  <el-row :gutter="40">
    <!--文章列表-->
    <el-col :span="15">
      <blogArticle></blogArticle>
    </el-col>

    <!--专栏、标签、最新推荐、二维码-->
    <el-col :span="8">
      <!--专栏-->
      <el-card shadow="always" class="box-card m-radius-small m-b-margin">
        <!--header-->
        <div slot="header" class="clearfix">
          <span style="float: left;">
            <svg class="icon m-svg-size" aria-hidden="true">
              <use xlink:href="#icon-icon-"></use>
            </svg>
            专栏</span
          >

          <router-link to="category">
            <el-button type="text" class="m-type-btn"
              >更多<i class="el-icon-d-arrow-right"></i
            ></el-button>
          </router-link>
        </div>

        <!--专栏内容-->
        <el-card
          v-for="item in cateArticleCount"
          :key="item.cate_id"
          shadow="hover"
          style="border-radius: 10px"
          class="m-b-margin-mini card-padded-mini"
        >
          <span>{{ item.cate_name }}</span>
          <el-button
            type="danger"
            circle
            size="small"
            style="float: right"
            class="btnCirclePadded"
            >{{ item.counts | CountFormat }}</el-button
          >
        </el-card>
      </el-card>

      <!--标签-->
      <el-card shadow="always" class="box-card m-radius-small m-b-margin">
        <!--header-->
        <div slot="header" class="clearfix">
          <span style="float: left;">
            <svg class="icon m-svg-size" aria-hidden="true">
              <use xlink:href="#icon-icon"></use>
            </svg>
            标签</span
          >
          <router-link to="tags">
            <el-button type="text" class="m-type-btn"
              >更多<i class="el-icon-d-arrow-right"></i
            ></el-button>
          </router-link>
        </div>

        <!--标签内容-->
        <el-tag
          class="m-margin-small m-tags-radius"
          @click="goTags(item.tag_id)"
          v-for="item in tags"
          :key="item.tag_id"
          :type="item.tag_type"
          effect="dark"
        >
          <el-badge :value="item.counts" :max="10" class="item">
            {{ item.tag_name }}
          </el-badge>
        </el-tag>
      </el-card>

      <!--最新推荐-->
      <el-card shadow="always" class="box-card m-radius-small m-b-margin">
        <!--header-->
        <div slot="header" class="clearfix">
          <span style="float: left;">
            <svg class="icon m-svg-size" aria-hidden="true">
              <use xlink:href="#icon-tuijian1"></use>
            </svg>
            最新推荐</span
          >
        </div>

        <!--推荐文章-->
        <div style="text-align: center">
          <h3 v-for="item in titles" :key="item.article_id">
            <a href="#">{{ item.title | TitleFormat }}</a>
          </h3>
        </div>
      </el-card>

      <!--扫码关注-->
      <el-divider content-position="center" class="m-t-margin divider-color"
        ><strong>扫码关注我</strong></el-divider
      >

      <el-card class="card-padded m-QR-size">
        <el-image :src="wechatUrl" fit="fill"></el-image>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
const blogArticle = () =>import(/* webpackChunkName: "blog_article" */ "@/components/BlogArticle");
import { getRequest } from "../../untils/axiosApi";
import axios from "axios";

export default {
  name: "BlogIndex",
  data() {
    return {
      wechatUrl: require("./../assets/images/wechat.png"),
      cateArticleCount: null,
      tags: null,
      titles: null
    };
  },
  created() {
    axios
      .all([this.getData(), this.getTags(), this.getTitle()])
      .then(axios.spread(data => {}));
  },
  methods: {
    getData() {
      var _this = this;
      getRequest("/category/count/").then(response => {
        if (response.status == 200) {
          _this.cateArticleCount = response.data;
        }
      });
    },
    getTags() {
      var _this = this;
      getRequest("/tags/counts/").then(response => {
        if (response.status == 200) {
          _this.tags = response.data;
        }
      });
    },
    getTitle() {
      var _this = this;
      getRequest("/article/recommend/").then(response => {
        if (response.status == 200) {
          _this.titles = response.data;
        }
      });
    },
    goTags(value) {
      this.$router.push({
        name: "标签",
        params: { value }
      });
    }
  },
  filters: {
    CountFormat: function(value) {
      var num = value.toString().padStart(2, "0");
      return `${num}`;
    },
    TitleFormat: function(value) {
      if (value.length > 15) {
        value = value.substring(0, 15) + "...";
      }
      return `${value}`;
    }
  },
  components: {
    blogArticle: blogArticle
  }
};
</script>

<style scoped>
/*分割线背景颜色*/
.divider-color .el-divider__text {
  background-color: #f4f4f4;
}

/*专栏更多按钮*/
.m-type-btn {
  float: right;
  padding: 3px 0;
}

/*二维码尺寸*/
.m-QR-size {
  width: 200px;
  height: 200px;
  border-radius: 15px;
  margin: auto;
}

/*卡片padding大小*/
.card-padded-mini div.el-card__body {
  padding: 14px;
}

/*圆形按钮padding*/
.btnCirclePadded.el-button--small.is-circle {
  padding: 5px;
}
</style>
