<template>
  <el-row :gutter="40" v-show="initialArticles">
    <!--文章列表-->
    <el-col :span="15">
      <blogArticle :initial_articles="initialArticles"></blogArticle>
    </el-col>

    <!--专栏、标签、最新推荐、二维码-->
    <el-col :span="8">
      <!--专栏-->
      <el-card shadow="always" class="box-card m-radius-small m-b-margin">
        <!--header-->
        <div slot="header" class="clearfix">
          <span style="float: left">
            <svg
              class="icon m-svg-size"
              aria-hidden="true"
              style="font-size: 26px"
            >
              <use xlink:href="#icon-tuisong"></use>
            </svg>
            <b>推荐专栏</b></span
          >

          <router-link to="category">
            <svg
              class="icon m-svg-size m-type-btn"
              aria-hidden="true"
              style="font-size: 26px"
            >
              <use xlink:href="#icon-jia1"></use>
            </svg>
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
      <el-card shadow="always" class="m-radius-small m-b-margin card-wordCloud">
        <!--header-->
        <div slot="header" class="clearfix">
          <span style="float: left">
            <svg
              class="icon m-svg-size"
              aria-hidden="true"
              style="font-size: 26px"
            >
              <use xlink:href="#icon-huo"></use>
            </svg>
            <b>热门标签</b></span
          >
          <router-link to="tags">
            <svg
              class="icon m-svg-size m-type-btn"
              aria-hidden="true"
              style="font-size: 26px"
            >
              <use xlink:href="#icon-jia1"></use>
            </svg>
          </router-link>
        </div>

        <!--引入词云组件：传入id(不能重复！) 宽高以及数据，当拿到数据后才开始渲染-->
        <wordCloud
          v-if="tagsData"
          :wc_id="wordCloudId"
          :wc_height="300"
          :wc_width="340"
          :wc_data="echartsData"
        ></wordCloud>
      </el-card>

      <!--最新推荐-->
      <el-card shadow="always" class="box-card m-radius-small m-b-margin">
        <!--header-->
        <div slot="header" class="clearfix">
          <span style="float: left">
            <svg
              class="icon m-svg-size"
              aria-hidden="true"
              style="font-size: 26px"
            >
              <use xlink:href="#icon-tuiguang"></use>
            </svg>
            <b>最新推荐</b></span
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
import blogArticle from "./BlogArticle";
import wordCloud from "./WordCloud";
import { getRequest } from "../../untils/axiosApi";
import axios from "axios";

export default {
  name: "BlogIndex",
  data() {
    return {
      wechatUrl: require("./../assets/images/wechat.png"),
      cateArticleCount: null,
      initialArticles: null,
      tagsData: null,
      titles: null,
      wordCloudId: "wordCloud",
      echartsData: [
        {
          name: "十九大精",
          value: 15000,
        },
        {
          name: "两学一",
          value: 10081,
        },
        {
          name: "中华",
          value: 9386,
        },
        {
          name: "马克义",
          value: 7500,
        },
        {
          name: "民族复兴",
          value: 7500,
        },
        {
          name: "社会主制度",
          value: 6500,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
      ],
    };
  },
  beforeRouteEnter(to, from, next) {
    //在路由组件跳转前获取数据
    axios
      .all([
        getRequest("/article/"),
        getRequest("/category/count/"),
        getRequest("/tags/counts/"),
        getRequest("/article/recommend/"),
      ])
      .then(
        axios.spread(
          (articleResponse, categoryResponse, tagsResponse, titleResponse) => {
            next((vm) => {
              vm.setData(
                articleResponse,
                categoryResponse,
                tagsResponse,
                titleResponse
              );
            });
          }
        )
      );
  },
  methods: {
    //数据处理
    setData(articleResponse, categoryResponse, tagsResponse, titleResponse) {
      var _this = this;

      if (articleResponse.status == 200) {
        _this.initialArticles = articleResponse.data;
      }

      if (categoryResponse.status == 200) {
        _this.cateArticleCount = categoryResponse.data;
      }

      if (tagsResponse.status == 200) {
        _this.tagsData = tagsResponse.data;
      }

      if (titleResponse.status == 200) {
        _this.titles = titleResponse.data;
      }
    },
    goTags(value) {
      this.$router.push({
        name: "标签",
        params: { value },
      });
    },
  },
  filters: {
    //数字过滤器：不足十位自动补0
    CountFormat: function (value) {
      var num = value.toString().padStart(2, "0");
      return `${num}`;
    },
    //文章标题过滤器：限制标题为15字符超过加...
    TitleFormat: function (value) {
      if (value.length > 15) {
        value = value.substring(0, 15) + "...";
      }
      return `${value}`;
    },
  },
  components: {
    blogArticle: blogArticle,
    wordCloud: wordCloud,
  },
};
</script>

<style>
/* 词云卡片边距处理 */
.card-wordCloud div.el-card__body {
  padding-top: 5px;
  padding-bottom: 10px;
  padding-left: 10px;
}
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
