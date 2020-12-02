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
          <h3 v-for="item in recommend" :key="item.article_id">
            <a href="#">{{ item.title | titleFormat }}</a>
          </h3>
        </div>
      </el-card>
  </div>
</template>
<script>
export default {
  props: ["recommend"],
};
</script>
