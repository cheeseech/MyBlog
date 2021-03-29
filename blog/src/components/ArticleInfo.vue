<!--
 * @FileDescription: 文章列表组件
 * @Author: 徐茂华
 * @Date: 2020-08-09 17:55:42
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-12 14:58:53
 * @FilePath: \src\components\ArticleInfo.vue
-->
<template>
  <div id="BlogArticle">
    <el-card
      class="box-card m-radius-big m-b-margin card-padded"
      v-for="article in initial_articles"
      :key="article.articleId"
      shadow="hover"
    >
      <!--文章主要内容-->
      <div style="padding: 20px">
        <!--是否推荐-->
        <svg
          v-if="article.recommend"
          class="icon m-svg-size"
          aria-hidden="true"
          color="red"
        >
          <use xlink:href="#icon-tuijian"></use>
        </svg>

        <!--原创、转载、翻译-->
        <el-button type="warning" round class="btnPadded type-button">{{
          article.typeName
        }}</el-button>
        <!--专栏-->
        <el-button
          @click="goCategory(article.cateName)"
          type="primary"
          round
          class="btnPadded m-r-margin-small"
          style="float: right"
          >{{ article.cateName }}</el-button
        >
        <!--标题-->
        <h2 class="m-article-title" @click="goArticleInfo(article.articleId)">
          <a href="javascript:void(0)">{{ article.title }}</a>
        </h2>

        <!--日期-->
        <p style="color: gray">
          {{ article.updateTime | monthDayYearFormat }}
        </p>
        <!--文章内容-->
        <p class="m-text-lined">{{ article.summary }}</p>

        <!--标签、评论、点赞、浏览次数-->
        <el-menu class="el-menu-demo" mode="horizontal">
          <!--文章标签-->
          <el-menu-item class="m-lf-padded-mini">
            <el-button
              @click="goTags(item.tagName)"
              type="primary"
              round
              class="btnPadded"
              v-for="item in article.tags"
              :key="item.tagId"
              :type="item.tagType"
              effect="dark"
            >
              {{ item.tagName }}
            </el-button>
          </el-menu-item>

          <!--评论-->
          <el-menu-item class="m-lf-padded-mini" style="float: right">
            <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
              <use xlink:href="#icon-pinglun2"></use>
            </svg>

            <span class="m-article-icon-text">{{ article.comments }}</span>
          </el-menu-item>

          <!--浏览次数-->
          <el-menu-item class="m-lf-padded-mini" style="float: right">
            <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
              <use xlink:href="#icon-ai-eye"></use>
            </svg>

            <span class="m-article-icon-text">{{ article.views }}</span>
          </el-menu-item>
        </el-menu>
      </div>
    </el-card>
  </div>
</template>

<script>
import { eventBus } from "@/main";
import { getRequest, postRequest } from "../../untils/axiosApi";

export default {
  name: "BlogArticle",
  props: ["initial_articles"],
  created() {
    //兄弟组件传值：根据搜索框值检索文章
    eventBus.$on("title", data => {
      this.getArticlesByTitle(data);
    });
  },
  methods: {
    /**
     * @description: 根据文章标题模糊检索文章
     * @param {String} value
     * @return {void}
     */
    getArticlesByTitle(value) {
      postRequest("/article/" + value).then(response => {
        if (response.status == 200) {
          this.articles = response.data;
        }
      });
    },

    /**
     * @description: 传递标签名并跳转到标签页
     * @param {String} value
     * @return {void}
     */
    goTags(value) {
      this.$router.push({
        name: "标签",
        params: { value }
      });
    },

    /**
     * @description: 传递专栏名并跳转到专栏页
     * @param {String} value
     * @return {void}
     */
    goCategory(value) {
      this.$router.push({
        name: "专栏",
        params: { value }
      });
    },

    /**
     * @description: 传递文章ID并跳转到文章详情页
     * @param {Number} value
     * @return {void}
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
.m-lf-padded-mini {
  padding-left: 0.4em;
  padding-right: 0.4em;
}

.m-r-margin-small {
  margin-right: 0.4em;
}

/*文章标题*/
.m-article-title {
  margin-top: 0;
  margin-bottom: 0;
  display: inline-block;
}

.m-article-title a {
  color: #000;
}

.m-article-title a:hover {
  color: #3476d2;
}

.type-button {
  cursor: default;
  float: right;
}
.type-button:focus,
.type-button:hover {
  color: #fff;
  background-color: #e6a23c;
  border-color: #e6a23c;
}
</style>
