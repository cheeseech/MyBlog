<template>
  <div id="blogTypes" v-show="initialArticles">
    <el-card class="m-radius-small m-b-margin" id="m-card">
      <div slot="header" class="clearfix">
        <span
          ><strong style="font-size: 24px">
            <svg
              class="icon m-svg-size"
              aria-hidden="true"
              style="font-size: 26px"
            >
              <use xlink:href="#icon-tuisong"></use>
            </svg>
            全部专栏</strong
          ></span
        >
        <span style="float: right; padding: 3px 0"
          >共 <strong style="font-size: 24px;color: orange">10</strong> 个</span
        >
      </div>

      <el-button class="m-btn" v-backgroundColor
        ><b>按钮</b><span class="m-span">5</span></el-button
      >
      <el-button class="m-btn" v-backgroundColor
        ><b>Java</b><span class="m-span">2</span></el-button
      >
      <el-button class="m-btn" v-backgroundColor
        ><b>生活感悟</b><span class="m-span">15</span></el-button
      >
      <el-button class="m-btn" v-backgroundColor
        ><b>按钮</b><span class="m-span">5</span></el-button
      >
      <el-button class="m-btn" v-backgroundColor
        ><b>Java</b><span class="m-span">2</span></el-button
      >
      <el-divider><b>专栏介绍</b></el-divider>
      <p style="text-align: center;color:#303133;">
        萨的功能，
        在超女暗示案件佛教为安居吗，女萨斯你哦v郊外v呢奥斯卡老师的离开你，sxcv./ml萨拉点击发送旅客撒旦减肥
      </p>
    </el-card>

    <blogArticle
      :cate_Name="cateName"
      :initial_articles="initialArticles"
    ></blogArticle>
  </div>
</template>

<script>
const blogArticle = () =>
  import(/* webpackChunkName: "blog_article" */ "@/components/BlogArticle");
import { getRequest } from "../../untils/axiosApi";
import axios from "axios";

export default {
  name: "BlogTypes",
  data() {
    return {
      category: null,
      cateName: null,
      initialArticles: null
    };
  },
  beforeRouteEnter(to, from, next) {
    axios.all([getRequest("/article/"), getRequest("/category/")]).then(
      axios.spread((articleResponse, categoryResponse) => {
        next(vm => vm.setData(articleResponse, categoryResponse));
      })
    );
  },
  mounted() {
    if (this.$route.params.value) {
      this.cateName = this.$route.params.value;
    }
  },
  methods: {
    setData(articleResponse, categoryResponse) {
      if (articleResponse.status == 200) {
        this.initialArticles = articleResponse.data;
      }
      if (categoryResponse.status == 200) {
        this.category = categoryResponse.data;
      }
    },
    sendNameForArticle(value) {
      this.cateName = value;
    }
  },
  directives: {
    backgroundColor: {
      bind(el, binding, vnode) {
        var colorArray = [
          "#409EFF",
          "#67C23A",
          "#E6A23C",
          "#F56C6C",
          "#6639a6",
          "#3ec1d3",
          "#f67280",
          "#00b8a9",
          "#903749",
          "#11999e",
          "#8785a2",
          "#aa96da",
          "#fc5185",
          "#95e1d3",
          "#f38181"
        ];
        var index = Math.floor(Math.random() * colorArray.length);
        el.style.backgroundColor = colorArray[index];
      }
    }
  },
  components: {
    blogArticle: blogArticle
  }
};
</script>

<style scoped>
.bg-purple {
  background: #d3dce6;
}
.grid-content {
  border-radius: 4px;
  min-height: 36px;
}
.m-span {
  margin-left: 5px;
  padding: 5px 10px;
  border-radius: 4px;
  color: black;
  font-size: 16px;
  font-weight: 550;
  background-color: #fff;
}
.m-btn {
  padding: 19px 15px;
  border-radius: 10px;
  color: #fff;
  margin-top: 10px;
}
</style>
