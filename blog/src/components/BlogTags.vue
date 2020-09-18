<template>
  <div id="blogTags" v-if="initialArticles">
    <el-card
      class="box-card m-radius-small m-b-margin"
    >
      <div slot="header" class="clearfix">
        <span><strong style="font-size: 24px">标签</strong></span>
        <span style="float: right; padding: 3px 0"
          >共
          <strong style="font-size: 24px;color: orange">{{
            tagItemsLen
          }}</strong>
          个</span
        >
      </div>

      <el-button
        @click="sendIdForArticle(item.tagId)"
        v-for="item in tagItems"
        :key="item.tagId"
        :type="item.tagType"
        round
      >
        {{ item.tagName }}
      </el-button>
    </el-card>

    <blogArticle
      :tag_id="tagId"
      :initial_articles="initialArticles"
    ></blogArticle>
  </div>
</template>

<script>
import blogArticle from "./BlogArticle"
import axios from "axios";
import { getRequest } from "../../untils/axiosApi";

export default {
  name: "BlogTags",
  data() {
    return {
      tagItems: null,
      initialArticles: null,
      tagItemsLen: 0,
      tagId: ""
    };
  },
  beforeRouteEnter(to, from, next) {
    axios.all([getRequest("/tags/"), getRequest("/article/")]).then(
      axios.spread((tagsResponse, articleResponse) => {
        next(vm => vm.setData(tagsResponse, articleResponse));
      })
    );
  },
  mounted() {
    /*
     * 在页面模版挂载后，判断BlogArticle是否传值过来(点击文章标签),
     * 成功则改变tagId即传值给BlogArticle生成文章列表
     * 如果没有在mounted中，将BlogArticle将监听不到tag_id的变化*/
    // console.log("monted")
    var _this = this;
    if (_this.$route.params.value) {
      _this.tagId = _this.$route.params.value;
    }
  },
  methods: {
    setData(tagsResponse, articleResponse) {
      var _this = this;
      _this.tagItems = tagsResponse.data;
      _this.tagItemsLen= _this.tagItems.length;
      _this.initialArticles = articleResponse.data;
    },
    sendIdForArticle(value) {
      var _this = this;
      _this.tagId = value;
    }
  },
  components: {
    blogArticle: blogArticle
  }
};
</script>

<style></style>
