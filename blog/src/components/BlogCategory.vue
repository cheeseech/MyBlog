<template>
  <div id="blogTypes">
    <el-card class="box-card m-radius-small m-b-margin card-padded">
      <div slot="header" class="clearfix">
        <span
          ><strong style="font-size: 24px">
            <svg class="icon m-svg-size" aria-hidden="true">
              <use xlink:href="#icon-shu"></use>
            </svg>
            专栏</strong
          ></span
        >
        <span style="float: right; padding: 3px 0"
          >共 <strong style="font-size: 24px;color: orange">10</strong> 个</span
        >
      </div>

      <el-row>
        <el-col :span="12">
          <el-collapse accordion class="m-padded">
            <el-collapse-item
              :name="item.cateId"
              v-for="(item, index) in category"
              :key="item.cateId"
              v-if="index % 2 == 0"
            >
              <template slot="title">
                <div @click="sendNameForArticle(item.cateName)">
                  <span
                    ><svg
                      class="icon m-svg-size"
                      aria-hidden="true"
                      color="#409EFF"
                    >
                      <use xlink:href="#icon-shu"></use>
                    </svg>
                    {{ item.cateName }}</span
                  >
                </div>
              </template>

              <div class="m-lf-padded-small">{{ item.cateSummary }}</div>
            </el-collapse-item>
          </el-collapse>
        </el-col>
        <el-col :span="12">
          <el-collapse accordion class="m-padded">
            <el-collapse-item
              :name="item.cateId"
              v-for="(item, index) in category"
              :key="item.cateId"
              v-if="index % 2 == 1"
            >
              <template slot="title">
                <div @click="sendNameForArticle(item.cateName)">
                  <span
                    ><svg
                      class="icon m-svg-size"
                      aria-hidden="true"
                      color="#F56C6C"
                    >
                      <use xlink:href="#icon-shu"></use>
                    </svg>
                    {{ item.cateName }}</span
                  >
                </div>
              </template>

              <div class="m-lf-padded-small">{{ item.cateSummary }}</div>
            </el-collapse-item>
          </el-collapse>
        </el-col>
      </el-row>
    </el-card>

    <blogArticle :cate_Name="cateName"></blogArticle>
  </div>
</template>

<script>
const blogArticle = () =>import(/* webpackChunkName: "blog_article" */ "@/components/BlogArticle");
import { getRequest } from "../../untils/axiosApi";

export default {
  name: "BlogTypes",
  data() {
    return {
      category: null,
      cateName: null
    };
  },
  created() {
    this.getCategory();
  },
  mounted() {
    if (this.$route.params.value) {
      this.cateName = this.$route.params.value;
    }
  },
  methods: {
    sendNameForArticle(value) {
      this.cateName = value;
    },
    getCategory() {
      getRequest("/category/").then(response => {
        if (response.status == 200) {
          this.category = response.data;
        }
      });
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
</style>
