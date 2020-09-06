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
          <li v-for="item in titles" :key="item.article_id">
            <a href="#">{{ item.title | TitleFormat }}</a>
          </li>
        </div>
      </el-col>

      <!--联系我-->
      <el-col :span="6" class="m-t-margin-mini">
        <div class="m-new-blog">
          <h3>联系我</h3>
          <a>Email:xumaohua98@163.com</a>
          <a>QQ:1355125277</a>
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
import { getRequest } from "../../untils/axiosApi";

export default {
  name: "BlogFooter",
  data() {
    return {
      wechatUrl: require("./../assets/images/wechat.png"),
      titles: null
    };
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      var _this = this;
      getRequest("/article/new/").then(response => {
        if (response.status == 200) {
          _this.titles = response.data;
        }
      });
    }
  },
  filters: {
    TitleFormat: function(value) {
      if (value.length > 15) {
        value = value.substring(0, 15) + "...";
      }
      return `${value}`;
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

/*博客样式*/
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
.m-footer-p {
  color: #e9eef3;
  line-height: 1.8;
}
</style>
