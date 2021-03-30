<!--
 * @FileDescription: 博客文章详情组件
 * @Author: 徐茂华
 * @Date: 2020-08-09 22:00:51
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-11 11:18:35
 * @FilePath: \src\views\blog\BlogArticle.vue
-->
<template>
  <div id="articleInfo" v-if="articleInfo">
    <el-row :gutter="30" style="margin-top:2%">
      <el-col :span="16">
        <el-card class="m-radius-big m-b-margin card-padded" shadow="hover">
          <!--文章主要内容-->
          <div style="padding: 40px;">
            <el-button
              type="warning"
              round
              style="float: right;margin-right: 13px;font-size:16px"
              class="btnPadded"
              >{{ articleInfo.type_name }}</el-button
            >

            <!--专栏-->
            <el-button
              type="primary"
              round
              class="btnPadded m-r-margin-small"
              style="float: right;font-size:16px;margin-right: 15px;"
              >{{ articleInfo.cate_name }}</el-button
            >

            <!--是否推荐-->
            <svg
              v-if="articleInfo.recommend"
              class="icon m-svg-size-biger"
              aria-hidden="true"
              color="red"
            >
              <use xlink:href="#icon-tuijian"></use>
            </svg>

            <!-- 文章标题 -->
            <h1 class="m-article">{{ articleInfo.title }}</h1>

            <el-menu
              class="el-menu-demo"
              mode="horizontal"
              text-color="#303133"
            >
              <!-- 头像及昵称 -->
              <el-menu-item style="padding-left: 0px;cursor: default;">
                <el-avatar
                  :size="50"
                  :src="articleInfo.user_face"
                  style="vertical-align:middle;"
                ></el-avatar>
                <span style="padding-left: 12px;font-size:18px"
                  ><strong>{{ articleInfo.nickname }}</strong></span
                >
              </el-menu-item>

              <!-- 文章发布时间 -->
              <el-menu-item style="padding-left: 6px;cursor: default;">
                <svg
                  class="icon m-svg m-article-icon"
                  aria-hidden="true"
                  style="color:#409EFF"
                >
                  <use xlink:href="#icon-shijian01"></use>
                </svg>
                <span
                  style="color:#606266;font-size: 14px;font-weight: bold;"
                  >{{ articleInfo.update_time | monthDayYearFormat }}</span
                >
              </el-menu-item>

              <!-- 浏览数及评论数 -->
              <el-menu-item
                style="float: right;padding-left: 0px;cursor: default;"
              >
                <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
                  <use xlink:href="#icon-ai-eye"></use>
                </svg>
                <span class="m-article-icon-text">{{ articleInfo.views }}</span>

                <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
                  <use xlink:href="#icon-icon4"></use>
                </svg>
                <span class="m-article-icon-text">{{ articleInfo.likes }}</span>

                <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
                  <use xlink:href="#icon-pinglun2"></use>
                </svg>
                <span class="m-article-icon-text">{{
                  articleInfo.comments
                }}</span>
              </el-menu-item>
            </el-menu>

            <!-- 文章主要内容 -->
            <div
              class="m-text-lined m-t-margin-small js-toc-content markdown-body"
              id="articleContent"
              ref="articleContent"
              v-html="articleInfo.html_content"
            ></div>

            <!-- 文章下标签集合 -->
            <div>
              <el-button
                type="primary"
                round
                class="btnPadded"
                v-for="item in articleInfo.tags"
                :key="item.tagId"
                :type="item.tagType"
                effect="dark"
                style="font-size:16px"
              >
                {{ item.tagName }}
              </el-button>
            </div>

            <!-- 分割线 -->
            <el-divider></el-divider>

            <!-- 知识共享许可协议 -->
            <div class="cc-license">
              <a
                rel="license"
                href="http://creativecommons.org/licenses/by-nc/4.0/"
                ><img
                  alt="知识共享许可协议"
                  style="border-width:0"
                  :src="ccImg"/></a
              ><br />本作品采用<a
                rel="license"
                href="http://creativecommons.org/licenses/by-nc/4.0/"
                >知识共享署名-非商业性使用 4.0 国际许可协议</a
              >进行许可。
            </div>

            <!-- 分割线 -->
            <el-divider></el-divider>
          </div>
        </el-card>
      </el-col>

      <el-col :span="7">
        <div style="position: fixed;width:inherit">
          <!-- 文章标题导航 -->
          <div id="skytoc"></div>

          <!-- 音乐插件 -->
          <div style="margin-top: 20px" class="m-music">
            <meting-js
              server="netease"
              style="border-radius: 10px;"
              type="playlist"
              id="5398429287"
              list-max-height="100px"
              list-folded="true"
            >
            </meting-js>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import axios from "axios";
import tocbot from "tocbot";
import "mavon-editor/dist/css/index.css";
import { getRequest } from "@/../untils/axiosApi";
import { addCodeBtn } from "@/views/blog/mavon";

export default {
  name: "ArticleInfo",
  data() {
    return {
      articleInfo: null, // 文章数据集合
      ccImg: require("@/assets/images/cc.png")
    };
  },
  // 在路由组件跳转前获取数据
  beforeRouteEnter(to, from, next) {
    var articleId = to.params.articleId;
    axios.all([getRequest("/article/info/" + articleId)]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  updated() {
    this.addMao();

    var node = document.getElementById("skytoc");
    tocbot.init({
      tocSelector: "#skytoc", //要把目录添加元素位置，支持选择器
      contentSelector: ".js-toc-content", //获取html的元素
      headingSelector: "h1, h2, h3", //要显示的id的目录
      hasInnerContainers: false, //For headings inside relative or absolute positioned containers within content.
      collapseDepth: 2, //不应折叠多少标题级别(0-6),0表示全部折叠,
      //主样式
      linkClass: "toc-link1",
      //选中时样式
      activeLinkClass: "is-active-link1"
    });

    this.$nextTick(_ => {
      addCodeBtn();
    });
  },
  methods: {
    /**
     * @description: 处理文章相关数据
     * @param {Map} response
     * @return {void}
     */
    setData(response) {
      if (response.status == 200) {
        this.articleInfo = response.data;
      }
    },

    /**
     * @description: 给h标签添加id
     * @return {void}
     */
    addMao() {
      let nodes = this.$refs.articleContent.children;
      if (nodes.length) {
        for (let i = 0; i < nodes.length; i++) {
          let node = nodes[i];
          let reg = /^H[1-6]{1}$/;
          if (reg.exec(node.tagName)) {
            node.id = node.firstElementChild.id;
            node.firstElementChild.removeAttribute("id");
          }
        }
      }
    }
  }
};
</script>

<style scoped>
@import url("https://cdnjs.cloudflare.com/ajax/libs/tocbot/4.11.1/tocbot.css");
@import url("https://cdn.jsdelivr.net/npm/aplayer/dist/APlayer.min.css");
@import url("https://cdnjs.cloudflare.com/ajax/libs/highlight.js/10.4.1/styles/github.min.css");
.el-card:hover {
  /*倍数*/
  transform: translateZ(0) scale(1);
}
.m-r-margin-small {
  margin-right: 0.4em;
}
/*文章标题*/
.m-article {
  margin-top: 0;
  margin-bottom: 1;
  display: inline-block;
  font-size: 26px;
}
.cc-license {
  text-align: center;
  margin-top: 2%;
  margin-bottom: 2%;
}
.cc-license a {
  color: #000;
}
.cc-license a:hover {
  color: #3476d2;
}
</style>

<style>
h1::before,
h2::before,
h3::before,
h4::before,
h5::before,
h6::before {
  display: block;
  content: " ";
  height: 80px;
  margin-top: -80px;
  visibility: hidden;
}
/* 点击目录跳转时设置黑框为0 */
h1:focus,
h2:focus,
h3:focus,
h4:focus,
h5:focus,
h6:focus {
  outline: 0 !important;
}

#skytoc {
  /* position: fixed; */
  padding: 20px;
  border-radius: 15px;
  background-color: white;
  font-size: 17px;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.15);
}
#articleInfo {
  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.15);
}
.m-music .aplayer {
  border-radius: 10px;
}
a.toc-link1 {
  color: black !important;
  height: 100%;
}
#skytoc .is-active-link1 {
  font-weight: 700;
  color: #1890ff !important;
}
</style>
