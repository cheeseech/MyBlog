<template>
  <div id="blogTags">
    <el-card
      class="box-card m-radius-small m-b-margin"
      v-loading="loading"
      element-loading-text="玩命加载中..."
      element-loading-spinner="el-icon-loading"
      element-loading-background="rgba(0, 0, 0, 0.8)"
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

    <blogArticle :tag_id="tagId"></blogArticle>
  </div>
</template>

<script>
const blogArticle = () =>import(/* webpackChunkName: "blog_article" */ "@/components/BlogArticle");
import { getRequest } from "../../untils/axiosApi";

export default {
  name: "BlogTags",
  data() {
    return {
      tagItems: null,
      tagItemsLen: 0,
      loading: false,
      tagId: ""
    };
  },
  created() {
    var _this = this;
    _this.getData();
  },
  mounted() {
    /*
     * 在页面模版挂载后，判断BlogArticle是否传值过来(点击文章标签),
     * 成功则改变tagId即传值给BlogArticle生成文章列表
     * 如果没有在mounted中，将BlogArticle将监听不到tag_id的变化*/
    var _this = this;
    if (_this.$route.params.value) {
      _this.tagId = _this.$route.params.value;
    }
  },
  methods: {
    getData() {
      var _this = this;
      getRequest("/tags/").then(response => {
        _this.loading = true;
        if (response.status == 200) {
          _this.tagItems = response.data;
          _this.tagItemsLen = _this.tagItems.length;
          _this.loading = false;
        }
      });
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
