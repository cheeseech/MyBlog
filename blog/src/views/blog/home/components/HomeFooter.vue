<!--
 * @FileDescription: 博客底部组件
 * @Author: 徐茂华
 * @Date: 2020-08-05 16:33:15
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-11 11:58:22
 * @FilePath: \src\views\blog\home\components\HomeFooter.vue
-->
<template>
  <div class="m-container">
    <el-row :gutter="20">
      <!--二维码-->
      <el-col :span="6" class="m-t-margin-small">
        <el-card class="card-padded m-QR-footer-size">
          <el-image :src="wechatUrl" fit="fill"></el-image>
        </el-card>
      </el-col>

      <!--最新博客-->
      <el-col :span="6" class="m-t-margin-mini">
        <div class="m-new-blog">
          <h3>最新博客</h3>
          <li
            v-for="item in articles"
            :key="item.article_id"
            @click="goBlogArticle(item.article_id)"
          >
            <a href="#">{{ item.title | TitleFormat }}</a>
          </li>
        </div>
      </el-col>

      <!--联系我-->
      <el-col :span="6" class="m-t-margin-mini">
        <div class="m-new-blog contact-me">
          <h3>联系我</h3>
          <a>Email：xumaohua98@163.com</a>
          <a>QQ：1355125277</a>
        </div>
      </el-col>

      <!--博客简介-->
      <el-col :span="6" class="m-t-margin-mini">
        <div class="m-new-blog">
          <h3>MyBlog</h3>
          <p class="m-footer-p">
            这是我的个人博客，会分享关于编程相关的内容，希望可以给你一点帮助...
          </p>
        </div>
      </el-col>
    </el-row>

    <el-divider></el-divider>

    <p class="m-footer-p" style="text-align: center;">
      Copyright@2020-2021 Xumaohua Designed by Xumaohua
    </p>
  </div>
</template>

<script>
import { getRequest } from "@/../untils/axiosApi";

export default {
  name: "BlogFooter",
  data() {
    return {
        articles: null , // 文章列表集合
        wechatUrl: "http://qr3f6y9b1.hb-bkt.clouddn.com/wechat.png", // 二维码图片
    };
  },
  created() {
    let vm = this;
    vm.init();
  },
  methods: {
    /**
     * @description: 获取最新博客数据
     * @return void
     */
    init() {
      getRequest("/article/new/").then(response => {
        if (response.status == 200) {
          this.articles = response.data;
        }
      });
    },

    /**
     * @description: 跳转到文章详情页并传递文章ID
     * @param {Number} id
     * @return void
     */
    goBlogArticle(id) {
      this.$router.push({
        name: "文章详情",
        params: { articleId: id }
      });
    }
  },
  filters: {
    /**
     * @description: 文章标题长度限制
     * @param {String} title
     * @return {String} 修改后标题
     */
    TitleFormat: function(title) {
      // 限制标题长度为15，超过后加...
      if (title.length > 15) {
        title = title.substring(0, 15) + "...";
      }
      return `${title}`;
    }
  }
};
</script>

<style scoped>
.m-t-margin-small {
  margin-top: 2em;
}

/*底部二维码尺寸*/
.m-QR-footer-size {
  width: 150px;
  height: 150px;
  border-radius: 15px;
  margin: auto;
}

/*博客标题样式*/
.m-new-blog {
  text-align: center;
}
.m-new-blog h3 {
  color: white;
}
.m-new-blog a {
  color: #e9eef3;
  line-height: 1.8;
}
.m-new-blog a:hover {
  color: #3476d2;
}
.m-footer-p {
  color: #e9eef3;
  line-height: 1.8;
}

/* 联系我a标签 */
.contact-me a {
  cursor: default;
}
</style>
