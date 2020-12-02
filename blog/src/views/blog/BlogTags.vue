<template>
  <div id="blogTags" v-show="initArticles">
    <el-card class="box-card m-radius-small m-b-margin">
      <div slot="header" class="clearfix">
        <!-- 全部标签及标签数 -->
        <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
          <use xlink:href="#icon-book-2"></use>
        </svg>
        <span
          ><strong class="m-font-size-big">全部标签</strong>
          <strong class="m-font-size-big-orange"
            >( {{ tagsLen }} )</strong
          ></span
        >

        <!-- 当前选择标签 -->
        <span style="margin-left:20%">
          <strong class="m-font-size-normal">当前选择：</strong>
          <strong class="m-font-size-normal-orange"
            >{{ chooseTags }} ({{ totalSize }})</strong
          >
          <!-- 选择全部 -->
          <strong
            ><a
              href="javascript:void(0)"
              class="m-font-size-normal-blue m-l-margin-small"
              @click="getArticlesByName('暂无')"
              >选择全部</a
            ></strong
          >
        </span>
        <!-- 搜索框-->
        <el-input
          v-model="input"
          placeholder="请输入内容"
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
        v-if="tagsCount"
        :wc_id="wordCloudId"
        :wc_height="200"
        :wc_width="1130"
        :wc_data="tagsCount"
        @sendTagName="getArticlesByName"
      ></wordCloud>

    </el-card>
    <!-- 文章列表 -->
    <articleInfo :initial_articles="initArticles"></articleInfo>

    <!-- 分页 -->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="totalSize"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="getArticlesByCurrentPage"
    ></el-pagination>
  </div>
</template>

<script>
import axios from "axios";
import { Message } from "element-ui";
import wordCloud from "@/components/WordCloud";
import { getRequest } from "@/../untils/axiosApi";
import { postRequest } from "@/../untils/axiosApi";
import articleInfo from "@/components/ArticleInfo";

export default {
  name: "BlogTags",
  data() {
    return {
      input: "",
      tagsLen: 0,
      pageSize: 6,
      totalSize: 0,
      currentPage: 1,
      tagsCount: null,
      chooseTags: "暂无",
      initArticles: null,
      wordCloudId: "wordCloud2"
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
      list = [getRequest("/tags/counts/"), postRequest("/article/page/", page)];
    }
    //请求数据
    axios.all(list).then(
      axios.spread((tagsResponse, articleResponse) => {
        next(vm => vm.setData(tagsResponse, articleResponse, tagName));
      })
    );
  },
  methods: {
    //数据处理
    setData(tagsResponse, articleResponse, tagName) {
      //标签文章数数据
      if (tagsResponse.status == 200) {
        this.tagsCount = tagsResponse.data;
        this.tagsLen = this.tagsCount.length;
      }
      this.setArticleData(articleResponse);
      //标签名
      if (tagName !== undefined) {
        this.chooseTags = tagName;
      }
    },
    //文章数据处理
    setArticleData(articleResponse) {
      if (articleResponse.status == 200) {
        //文章信息
        this.initArticles = articleResponse.data.content;
        //总条数
        this.totalSize = articleResponse.data.totalSize;
        //当前页
        this.currentPage = articleResponse.data.pageNum;
      }
    },
    //根据子组件传递的标签名获取文章信息
    getArticlesByName(tagName) {
      //改变选择标签名
      this.chooseTags = tagName;
      this.getArticlesByCurrentPage(1);
    },
    //根据页码获取文章
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
              if (tagsResponse.status == 200) {
                this.tagsCount = tagsResponse.data;
              }
              this.setArticleData(articleResponse);
              Message({
                type: "success",
                message: "成功选择了!",
                duration: 1000
              });
            })
          );
      } else {
        postRequest("/article/tags/" + this.chooseTags, page).then(response => {
          this.setArticleData(response);
          Message({
            type: "success",
            message: "成功选择了!",
            duration: 1000
          });
        });
      }
    },
    //标签名模糊搜索
    searchTags() {
      getRequest("/tags/like/" + this.input.toLowerCase()).then(response => {
        if (response.status == 200) {
          this.tagsCount = response.data;
          Message.success(response.msg);
        } else {
          Message.error("搜索结果为空！");
        }
      });
    }
  },
  components: {
    articleInfo,
    wordCloud
  }
};
</script>
