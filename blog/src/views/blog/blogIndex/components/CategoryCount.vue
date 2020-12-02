<template>
  <div id="category">
    <!--专栏-->
    <el-card shadow="always" class="m-radius-small m-b-margin card-padded">
      <!--header-->
      <div slot="header" class="clearfix">
        <span style="float: left">
          <svg
            class="icon m-svg-size-normal"
            aria-hidden="true"
          >
            <use xlink:href="#icon-tuisong"></use>
          </svg>
          <b>推荐专栏</b>
        </span>
        <!-- 跳转到专栏页 -->
        <router-link to="category">
          <svg
            class="icon m-svg-size-normal m-type-btn"
            aria-hidden="true"
          >
            <use xlink:href="#icon-jia1"></use>
          </svg>
        </router-link>
      </div>

      <!--专栏内容-->
      <el-collapse accordion class="m-padded">
        <el-collapse-item
          :name="item.cate_id"
          v-for="item in category_count"
          :key="item.cate_id"
        >
          <template slot="title">
            <!-- 专栏名称 -->
            <div @click="goCategory(item.cate_name)">
              <svg class="icon m-svg-size" aria-hidden="true">
                <use xlink:href="#icon-shuqian"></use>
              </svg>
              &nbsp;
              <span>{{ item.cate_name }}</span>
            </div>
          </template>
          <!-- 专栏介绍 -->
          <div class="m-lf-padded-small">
            {{ item.cate_summary }}
          </div>
        </el-collapse-item>
      </el-collapse>
    </el-card>
  </div>
</template>
<script>
export default {
  props: ["category_count"],
  methods: {
    //跳转到专栏页并且传递专栏名称
    goCategory(value) {
      this.$router.push({
        name: "专栏",
        params: { value }
      });
    }
  }
};
</script>
