<!--
 * @FileDescription: 博客首页最新推荐组件
 * @Author: 徐茂华
 * @Date: 2020-11-25 15:34:17
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-12 14:33:02
 * @FilePath: \src\views\blog\blogIndex\components\NewRecommend.vue
-->
<template>
  <div id="new">
    <el-card shadow="always" class="box-card m-radius-small m-b-margin">
      <!--header-->
      <div slot="header" class="clearfix">
        <span style="float: left">
          <svg
            class="icon m-svg-size"
            aria-hidden="true"
            style="font-size: 26px"
          >
            <use xlink:href="#icon-tuiguang"></use>
          </svg>
          <b>最新推荐</b></span
        >
      </div>

      <!--推荐文章-->
      <div style="text-align: center">
        <h3
          v-for="item in recommend"
          :key="item.article_id"
          @click="goArticle(item.article_id)"
        >
          <a class="recommend-color" href="#">{{ item.title | titleFormat }}</a>
        </h3>
      </div>
    </el-card>
  </div>
</template>
<script>
export default {
  props: ["recommend"],
  methods: {
    /**
     * @description: 跳转到文章详情并传递文章ID
     * @param {Number} value
     * @return void
     */
    goArticle(value) {
      this.$router.push({
        name: "文章详情",
        params: { articleId: value }
      });
    }
  }
};
</script>
<style scoped>
.recommend-color {
  color: #303133;
}
.recommend-color:hover {
  color: #3476d2;
}
</style>
