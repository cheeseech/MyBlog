<!--
 * @FileDescription: 博客首页热门标签组件
 * @Author: 徐茂华
 * @Date: 2020-11-25 15:39:28
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-12 14:36:01
 * @FilePath: \src\views\blog\blogIndex\components\TagsCount.vue
-->
<template>
  <div id="tags">
    <el-card shadow="always" class="m-radius-small m-b-margin card-wordCloud">
      <!--header-->
      <div slot="header" class="clearfix">
        <span style="float: left">
          <svg
            class="icon m-svg-size"
            aria-hidden="true"
            style="font-size: 26px"
          >
            <use xlink:href="#icon-huo"></use>
          </svg>
          <b>热门标签</b></span
        >
        <router-link to="tags">
          <svg
            class="icon m-svg-size m-type-btn"
            aria-hidden="true"
            style="font-size: 26px"
          >
            <use xlink:href="#icon-jia1"></use>
          </svg>
        </router-link>
      </div>

      <!--引入词云组件：传入id(不能重复！) 宽高以及数据，当拿到数据后才开始渲染-->
      <wordCloud
        v-if="tags_count"
        :wc_id="wordCloudId"
        :wc_height="300"
        :wc_width="340"
        :wc_data="tags_count"
        @sendTagName="goTags"
      ></wordCloud>
    </el-card>
  </div>
</template>
<script>
import wordCloud from "@/components/WordCloud";
export default {
  props: ["tags_count"],
  data() {
    return {
      wordCloudId: "wordCloud" // 词云组件ID
    };
  },
  methods: {
    /**
     * @description: 跳转到标签页并传递标签名
     * @param {String} value
     * @return void
     */
    goTags(value) {
      this.$router.push({
        name: "标签",
        params: { value }
      });
    }
  },
  components: { wordCloud }
};
</script>
