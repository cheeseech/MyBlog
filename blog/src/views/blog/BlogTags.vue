<!--
 * @FileDescription: 博客标签组件
 * @Author: 徐茂华
 * @Date: 2020-08-06 16:38:31
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-11 11:19:02
 * @FilePath: \src\views\blog\BlogTags.vue
-->
<template>
  <div id="blogTags" v-show="articles">
    <el-card class="box-card m-radius-small m-b-margin">
      <div slot="header" class="clearfix">
        <!-- 全部标签及标签文章数 -->
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-book-2"></use>
        </svg>
        <span
          ><strong class="m-font-size-big">全部标签</strong>
          <strong class="m-font-size-big-orange"
            >( {{ totalTagsLen }} )</strong
          ></span
        >

        <!-- 当前选择标签 -->
        <span style="margin-left:20%">
          <strong class="m-font-size-normal">当前选择：</strong>
          <strong class="m-font-size-normal-orange">{{
            tagNameArticleLen
          }}</strong>

          <!-- 选择全部文章标签 -->
          <strong
            ><a
              href="javascript:void(0)"
              class="m-font-size-normal-blue m-l-margin-small"
              @click="changeTagName('暂无')"
              >选择全部</a
            ></strong
          >
        </span>

        <!-- 搜索框-->
        <el-input
          v-model="input"
          placeholder="输入标签名检索"
          @keyup.enter.native="searchTags()"
          style="float: right; width: 20%"
          class="input-with-select m-search-input m-search-btn"
        >
          <el-button slot="append" @click="searchTags()"
            ><svg class="icon m-svg-size" aria-hidden="true">
              <use xlink:href="#icon-gosearch"></use>
            </svg>
          </el-button>
        </el-input>
      </div>

      <!--引入词云组件：传入id(不能重复！) 宽高以及数据,当拿到数据后才开始渲染-->
      <wordCloud
        v-if="tagsArticles"
        :wc_id="wordCloudId"
        :wc_height="200"
        :wc_width="1130"
        :wc_data="tagsArticles"
        @sendTagName="changeTagName"
      ></wordCloud>
    </el-card>

    <!-- 文章列表 -->
    <articleInfo :initial_articles="articles"></articleInfo>

    <!-- 分页 -->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="tagArticleLen"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="getArticlesByCurrentPage"
    ></el-pagination>
  </div>
</template>

<script>
import axios from "axios";
import { Message } from "element-ui";
import { eventBus } from "@/main";
import wordCloud from "@/components/WordCloud";
import articleInfo from "@/components/ArticleInfo";
import { postRequest, getRequest } from "@/../untils/axiosApi";

export default {
  name: "BlogTags",
  data() {
    return {
      input: "", // 搜索框值
      flag: false, // 是否显示消息提示
      pageSize: 6, // 分页长度
      title: "", // 搜索关键字
      articles: null, // 文章列表集合
      chooseTags: "暂无", // 当前选中标签
      tagNameArticleLen: "", // 选中标签+文章数
      currentPage: 1, // 当前页码
      tagsArticles: null, // 标签及标签文章数集合
      totalTagsLen: 0, // 总标签数
      tagArticleLen: 0, // 选中标签下文章数
      wordCloudId: "wordCloud2" // 词云组件ID
    };
  },
  //在路由组件跳转前获取数据
  beforeRouteEnter(to, from, next) {
    var list,
      tagName = to.params.value,
      page = { pageNum: 1, pageSize: 6 };
    //判断路由组件是否传参，是则改变请求语句
    if (tagName) {
      list = [
        getRequest("/tags/counts/"),
        postRequest("/article/tags/" + tagName, page)
      ];
    } else {
      list = [getRequest("/tags/counts/"), postRequest("/article/", page)];
    }
    //请求数据
    axios.all(list).then(
      axios.spread((tagsResponse, articleResponse) => {
        next(vm => vm.setData(tagsResponse, articleResponse, tagName));
      })
    );
  },
  mounted() {
    let vm = this;
    //兄弟组件传值：根据搜索框值检索文章
    eventBus.$on("title", data => {
      vm.getArticlesByTitleAndTag(data);
    });
  },
  methods: {
    /**
     * @description: 处理标签及标签文章相关数据
     * @param {Map} tagsResponse
     * @param {Map} articleResponse
     * @param {String} tagName
     * @return void
     */
    setData(tagsResponse, articleResponse, tagName) {
      //标签文章数数据
      if (tagsResponse.status == 200) {
        this.tagsArticles = tagsResponse.data;
        this.totalTagsLen = this.tagsArticles.length;
      }

      //文章相关数据
      this.setArticleData(articleResponse);

      //标签名
      if (tagName !== undefined) {
        this.chooseTags = tagName;
      }
    },

    /**
     * @description: 处理文章相关数据
     * @param {Map} articleResponse
     * @return void
     */
    setArticleData(articleResponse) {
      if (articleResponse.status == 200) {
        //文章信息
        this.articles = articleResponse.data.content;
        //总条数
        this.tagArticleLen = articleResponse.data.totalSize;
        // 标签名及文章数文本显示
        this.tagNameArticleLen =
          this.chooseTags + "（" + this.tagArticleLen + "）";
        //当前页
        this.currentPage = articleResponse.data.pageNum;
      }
      // 回到顶部
      this.common.backTop();
    },

    /**
     * @description: 改变选中标签并获取该标签下文章数据集合
     * @param {String} tagName
     * @return void
     */
    changeTagName(tagName) {
      //改变选择标签名
      this.chooseTags = tagName;
      // 重置搜索关键字
      this.title = "";
      eventBus.$emit("cleared", this.title);
      // 获取文章相关数据
      this.getArticlesByCurrentPage(1);
    },

    /**
     * @description: 根据标题及选中标签获取文章数据集合
     * @param {String} title
     * @return void
     */
    getArticlesByTitleAndTag(title) {
      //设置标题关键字
      this.title = title;
      //显示消息提示
      this.flag = true;
      // 获取文章数据集合
      this.getArticlesByCurrentPage(1);
    },

    /**
     * @description: 根据页码及标签名获取文章数据集合
     * @param {Number} currentPage
     * @return void
     */
    getArticlesByCurrentPage(currentPage) {
      var page = { pageNum: currentPage, pageSize: 6 };
      if (this.chooseTags === "暂无") {
        axios
          .all([
            getRequest("/tags/counts/"),
            postRequest("/article/page/", page)
          ])
          .then(
            axios.spread((tagsResponse, articleResponse) => {
              this.setData(tagsResponse, articleResponse, undefined);

              // 是否显示消息提示
              if (this.flag == true) {
                this.searchMessage(response);
              }
            })
          );
      } else {
        postRequest(
          "/article/tags/" + this.chooseTags + "/" + this.title,
          page
        ).then(response => {
          this.setArticleData(response);

          // 是否显示消息提示
          if (this.flag == true) {
            this.searchMessage(response);
          }
        });
      }
    },

    /**
     * @description: 根据输入值模糊搜索标签名及标签文章数集合
     * @return void
     */
    searchTags() {
      getRequest("/tags/like/" + this.input.toLowerCase()).then(response => {
        if (response.status == 200) {
          this.tagsArticles = response.data;
          var tagsLen = response.data.length;
          Message({
            type: "success",
            dangerouslyUseHTMLString: true,
            message:
              "<strong>搜索成功！共检索到<span style='font-size:20px;color:orange'> " +
              tagsLen +
              " </span>个标签。</strong>"
          });
        } else {
          Message({
            type: "error",
            dangerouslyUseHTMLString: true,
            message: "<strong>标签搜索结果为空！</strong>"
          });
        }
      });
    },

    /**
     * @description: 搜索后显示消息提示
     * @param {Map} response
     * @return void
     */
    searchMessage(response) {
      if (response.status == 200) {
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
      // 关闭消息提示
      this.flag = false;
    }
  },
  components: {
    articleInfo,
    wordCloud
  }
};
</script>
