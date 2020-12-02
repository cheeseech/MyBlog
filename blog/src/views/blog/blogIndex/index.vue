<template>
  <el-row :gutter="40" v-if="initArticles">
    <!--文章列表-->
    <el-col :span="15">
      <articleInfo :initial_articles="initArticles"></articleInfo>

      <!-- 分页 -->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalSize"
        :page-size="pageSize"
        @current-change="getArticlesByCurrentPage"
      ></el-pagination>
    </el-col>

    <!--展示、专栏、标签、最新推荐、二维码-->
    <el-col :span="8">
      <!-- 展示 -->
      <showMe :article_cate_tag_count="articleCateTagCount"></showMe>

      <!-- 专栏 -->
      <category :category_count="initCategoryCount"></category>

      <!--标签-->
      <hotTags :tags_count="initTagsCount"></hotTags>

      <!--最新推荐-->
      <newRecommend :recommend="initRecommend"></newRecommend>

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
import axios from "axios";
import wordCloud from "@/components/WordCloud";
import { getRequest } from "@/../untils/axiosApi";
import { postRequest } from "@/../untils/axiosApi";
import articleInfo from "@/components/ArticleInfo";
import hotTags from "@/views/blog/blogIndex/components/TagsCount";
import showMe from "@/views/blog/blogIndex/components/showMe";
import category from "@/views/blog/blogIndex/components/CategoryCount";
import newRecommend from "@/views/blog/blogIndex/components/NewRecommend";

export default {
  name: "BlogIndex",
  data() {
    return {
      pageSize: 6,
      totalSize: 0,
      initArticles: null,
      initTagsCount: null,
      initRecommend: null,
      initCategoryCount: null,
      articleCateTagCount: null,
      wechatUrl: require("@/assets/images/wechat.png")
    };
  },
  beforeRouteEnter(to, from, next) {
    //在路由组件跳转前获取数据
    axios
      .all([
        postRequest("/article/page/", { pageNum: 1, pageSize: 6 }),
        getRequest("/category/counts/"),
        getRequest("/tags/counts/"),
        getRequest("/article/recommend/"),
        getRequest("/article/info/")
      ])
      .then(
        axios.spread(
          (
            articleResponse,
            categoryResponse,
            tagsResponse,
            recommendResponse,
            infoResponse
          ) => {
            next(vm => {
              vm.setData(
                articleResponse,
                categoryResponse,
                tagsResponse,
                recommendResponse,
                infoResponse
              );
            });
          }
        )
      );
  },
  methods: {
    //数据处理
    setData(
      articleResponse,
      categoryResponse,
      tagsResponse,
      recommendResponse,
      infoResponse
    ) {
      this.setArticleData(articleResponse);

      //初始化专栏文章数数据
      if (categoryResponse.status == 200) {
        this.initCategoryCount = categoryResponse.data;
      }
      //初始化标签文章数数据
      if (tagsResponse.status == 200) {
        this.initTagsCount = tagsResponse.data;
      }
      //初始化最新推荐数据
      if (recommendResponse.status == 200) {
        this.initRecommend = recommendResponse.data;
      }
      //初始化文章、专栏以及标签数数据
      if (infoResponse.status == 200) {
        this.articleCateTagCount = infoResponse.data;
      }
    },
    //设置文章数据
    setArticleData(articleResponse) {
      if (articleResponse.status == 200) {
        //设置文章信息
        this.initArticles = articleResponse.data.content;
        //总条数
        this.totalSize = articleResponse.data.totalSize;
      }
    },
    //分页获取文章信息
    getArticlesByCurrentPage(currentPage) {
      postRequest("/article/page/", { pageNum: currentPage, pageSize: 6 }).then(
        response => {
          this.setArticleData(response);
        }
      );
    }
  },
  components: {
    articleInfo,
    wordCloud,
    showMe,
    category,
    newRecommend,
    hotTags
  }
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
