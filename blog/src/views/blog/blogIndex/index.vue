<!--
 * @FileDescription: 博客首页主组件
 * @Author: 徐茂华
 * @Date: 2020-08-07 19:45:49
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-04-09 15:06:43
 * @FilePath: \src\views\blog\blogIndex\index.vue
-->
<template>
  <el-row :gutter="40" v-if="initArticles">
    <!--文章列表-->
    <el-col :span="15">
      <articleInfo :initial_articles="initArticles"></articleInfo>

      <!-- 分页 -->
      <el-pagination
        background
        layout="prev, pager, next"
        :total="totalArticleLen"
        :page-size="pageSize"
        @current-change="getArticlesByCurrentPage"
      ></el-pagination>
    </el-col>

    <!--展示、专栏、标签、最新推荐、二维码-->
    <el-col :span="8">
      <!-- 展示 -->
      <showMe :article_cate_tag_count="articleCateTagCount"></showMe>

      <!-- 专栏 -->
      <category :categories="initCategoryCount"></category>

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
import { eventBus } from "@/main";
import { Message } from "element-ui";
import wordCloud from "@/components/WordCloud";
import { postRequest, getRequest } from "@/../untils/axiosApi";
import articleInfo from "@/components/ArticleInfo";
import hotTags from "@/views/blog/blogIndex/components/TagsCount";
import showMe from "@/views/blog/blogIndex/components/showMe";
import category from "@/views/blog/blogIndex/components/CategoryCount";
import newRecommend from "@/views/blog/blogIndex/components/NewRecommend";

export default {
  name: "BlogIndex",
  data() {
    return {
      title: "", // 搜索框值，为""时默认搜索全部
      pageSize: 6, // 分页长度
      currentPage: 1, // 当前页码
      totalArticleLen: 0, // 总文章数
      initArticles: null, // 文章列表集合
      initTagsCount: null, // 标签及标签文章数集合
      initRecommend: null, // 最新推荐文章列表集合
      initCategoryCount: null, // 专栏集合
      articleCateTagCount: null, // 文章数、专栏数及标签数
      wechatUrl: require("@/assets/images/wechat.png") // 微信二维码
    };
  },
  //在路由组件跳转前获取数据
  beforeRouteEnter(to, from, next) {
    axios
      .all([
        postRequest("/article/page/", { pageNum: 1, pageSize: 6 }),
        getRequest("/category/counts/"),
        getRequest("/tags/counts/"),
        getRequest("/article/recommend/"),
        getRequest("/article/count/")
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
  mounted() {
    //兄弟组件传值：根据搜索框值检索文章
    //判断是否是当前路由
    eventBus.$on("title", data => {
      this.getArticlesByTitle(data);
    });
  },
  //兄弟组件解绑，避免切换组件后造成调用多次情况
  beforeDestroy() {
    eventBus.$off("title");
  },
  methods: {
    /**
     * @description: 处理首页相关数据
     * @return void
     */
    setData(
      articleResponse,
      categoryResponse,
      tagsResponse,
      recommendResponse,
      infoResponse
    ) {
      //初始化文章数据
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

    /**
     * @description: 处理文章相关数据
     * @param {Map} articleResponse
     * @return void
     */
    setArticleData(articleResponse) {
      if (articleResponse.status == 200) {
        //设置文章信息
        this.initArticles = articleResponse.data.content;
        //总条数
        this.totalArticleLen = articleResponse.data.totalSize;

        // 获取完毕后回到顶部
        this.common.backTop();
      }
    },

    /**
     * @description: 分页获取文章信息
     * @param {Number} currentPage
     * @return void
     */
    getArticlesByCurrentPage(currentPage) {
      postRequest("/article/" + this.title, {
        pageNum: currentPage,
        pageSize: 6
      }).then(response => {
        // 处理文章相关数据
        this.setArticleData(response);
      });
    },

    /**
     * @description: 根据标题模糊查找文章
     * @param {String} value
     * @return void
     */
    getArticlesByTitle(value) {
      this.title = value;
      postRequest("/article/" + value, { pageNum: 1, pageSize: 6 }).then(
        response => {
          if (response.status == 200) {
            this.setArticleData(response);
            // 获取查找文章篇数
            var articleLen = response.data.totalSize;
            // 查找成功消息提示
            Message({
              type: "success",
              dangerouslyUseHTMLString: true,
              message:
                "<strong>搜索成功！共检索到<span style='font-size:20px;color:orange'> " +
                articleLen +
                " </span>篇文章。</strong>"
            });
          } else {
            // 查找失败消息提示
            Message({
              type: "error",
              dangerouslyUseHTMLString: true,
              message: "<strong>搜索失败！未找到相关文章。</strong>"
            });
          }
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
