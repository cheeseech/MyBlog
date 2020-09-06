<template>
  <div id="BlogArticle">
    <el-card
      class="box-card m-radius-big m-b-margin card-padded"
      v-for="article in articles"
      :key="article.articleId"
      shadow="hover"
    >
      <!--文章首图-->
      <el-image :src="imgSrc" fit="fill" class="img-transform"></el-image>

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
        <el-button
          type="warning"
          round
          style="display: inline-block;float: right"
          class="btnPadded"
          @click="getArticlesByType(article.typeName)"
          >{{ article.typeName }}</el-button
        >
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
        <h2 class="m-article">{{ article.title }}</h2>
        <!--日期-->
        <p style="color: gray">{{ article.publishTime | DateFormat }}</p>
        <!--文章内容-->
        <p class="m-text-lined">{{ article.summary }}</p>

        <!--标签、评论、点赞、浏览次数-->
        <el-menu class="el-menu-demo" mode="horizontal">
          <!--文章标签-->
          <el-menu-item class="m-lf-padded-mini">
            <el-button
              @click="goTags(item.tagId)"
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

          <!--点赞-->
          <el-menu-item class="m-lf-padded-mini" style="float: right">
            <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
              <use xlink:href="#icon-icon4"></use>
            </svg>

            <span class="m-article-icon-text">{{ article.likes }}</span>
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

    <!--分页-->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="100"
    ></el-pagination>
  </div>
</template>

<script>
import { getRequest } from "../../untils/axiosApi";
import { eventBus } from "@/main";

export default {
  name: "BlogArticle",
  props: ["tag_id", "cate_Name"],
  data() {
    return {
      imgSrc: require("./../assets/images/content.jpg"),
      articles: "",
      categoryName: null,
      typeName: null
    };
  },
  watch: {
    //监听tag_id是否发生变化
    tag_id: function() {
      this.getArticlesByTagId(this.tag_id);
    },
    cate_Name: function() {
      this.getArticlesByCateName(this.cate_Name);
    }
  },
  created() {
    this.getArticles();
    eventBus.$on("title", data => {
      this.getArticlesByTitle(data);
    });
  },
  methods: {
    getArticles() {
      getRequest("/article/").then(response => {
        if (response.status == 200) {
          this.articles = response.data;
        }
      });
    },
    getArticlesByType(value) {
      getRequest("/article/types/" + value).then(response => {
        if (response.status == 200) {
          this.articles = response.data;
        }
      });
    },
    getArticlesByTagId(value) {
      getRequest("/article/tags/" + value).then(response => {
        if (response.status == 200) {
          this.articles = response.data;
        }
      });
    },
    getArticlesByTitle(value) {
      getRequest("/article/" + value).then(response => {
        if (response.status == 200) {
          this.articles = response.data;
        }
      });
    },
    getArticlesByCateName(value) {
      getRequest("/article/category/" + value).then(response => {
        if (response.status == 200) {
          this.articles = response.data;
          console.log(this.articles);
        }
      });
    },
    goTags(value) {
      this.$router.push({
        name: "标签",
        params: { value }
      });
    },
    goCategory(value) {
      this.$router.push({
        name: "专栏",
        params: { value }
      });
    }
  },
  filters: {
    DateFormat: function(value) {
      // 根据给定的字符串，得到特定的日期
      var date = new Date(value);
      var year = date.getFullYear();

      var months = [
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
      var month = months[date.getMonth()];
      //该方法是在字符串前使用 fillString填充，直到字符串长度为 maxLength,这样字符串就自动在单位数前面补0.
      var day = date
        .getDate()
        .toString()
        .padStart(2, "0");

      return `${month} ${day}, ${year}`;
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
.m-article {
  margin-top: 0;
  margin-bottom: 0;
  display: inline-block;
}
</style>
