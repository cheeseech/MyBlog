<!--
 * @FileDescription: 博客首页关于我组件
 * @Author: 徐茂华
 * @Date: 2020-11-24 15:32:29
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-12 14:34:28
 * @FilePath: \src\views\blog\blogIndex\components\ShowMe.vue
-->
<template>
  <div id="show" v-if="article_cate_tag_count">
    <el-card class="m-radius-small m-b-margin">
      <!-- img -->
      <div class="img-pos">
        <el-avatar
          class="img-transform img-shadow"
          :size="100"
          :src="meUrl"
        ></el-avatar>
      </div>
      <div class="p-align">
        <p><b>芝士</b></p>
      </div>

      <!-- 文章、专栏以及标签数量 -->
      <div class="info-pos">
        <div>
          <router-link to="archives" class="router-pos">
            <span>文章</span>
            <br />
            <span class="span-pos">{{ article_cate_tag_count.article }}</span>
          </router-link>
        </div>
        <div>
          <router-link to="category" class="router-pos">
            <span>专栏</span>
            <br />
            <span class="span-pos">{{ article_cate_tag_count.category }}</span>
          </router-link>
        </div>
        <div>
          <router-link to="tags" class="router-pos">
            <span>标签</span>
            <br />
            <span class="span-pos">{{ article_cate_tag_count.tag }}</span>
          </router-link>
        </div>
      </div>

      <!-- 底部svg -->
      <div class="bottom-svg-pos">
        <!-- github -->
        <a href="https://github.com/cheeseech" target="_blank">
          <svg class="icon m-svg-size" aria-hidden="true">
            <use xlink:href="#icon-GitHub"></use>
          </svg>
        </a>
        <!-- 滑动到底部微信二维码 -->
        <a href="javascript:void(0)" @click="backBottom()">
          <svg class="icon m-svg-size" aria-hidden="true">
            <use xlink:href="#icon-weixin"></use>
          </svg>
        </a>
        <!-- qq -->
        <a
          href="http://wpa.qq.com/msgrd?v=3&uin=1355125277&site=qq&menu=yes"
          target="_blank"
        >
          <svg class="icon m-svg-size" aria-hidden="true">
            <use xlink:href="#icon-qq"></use>
          </svg>
        </a>
        <!-- 邮箱 -->
        <a href="mailto:xumaohua98@163.com">
          <svg class="icon m-svg-size" aria-hidden="true">
            <use xlink:href="#icon-xinfeng"></use>
          </svg>
        </a>
      </div>
    </el-card>
  </div>
</template>
<script>
export default {
  props: ["article_cate_tag_count"],
  data() {
    return {
      meUrl: require("@/assets/images/me.png") // 头像
    };
  },
  methods: {
    /**
     * @description: 点击微信图标滑动到底部微信二维码处
     * @return void
     */
    backBottom() {
      let ispeed = 500;
      let timer = setInterval(() => {
        let scollBottom =
          document.documentElement.scrollHeight -
          (document.documentElement.scrollHeight - ispeed);
        ispeed += 50;
        window.scrollTo(0, scollBottom);
        if (scollBottom >= document.documentElement.scrollHeight) {
          clearInterval(timer);
        }
      }, 5);
    }
  }
};
</script>
<style scoped>
.bottom-svg-pos {
  margin-left: 22%;
  margin-top: 50px;
}
.bottom-svg-pos svg {
  font-size: 30px;
  margin-right: 6%;
}
.img-pos {
  text-align: center;
  margin-bottom: 10px;
}
.img-shadow {
  box-shadow: 3px 8px 10px rgb(131, 131, 131);
}
.p-align {
  text-align: center;
}
.info-pos {
  margin-left: 21%;
}
.info-pos div {
  width: 20%;
  height: 50px;
  float: left;
  margin-right: 10%;
}
.router-pos {
  position: absolute;
  font-weight: bold;
}
.span-pos {
  margin-left: 7px;
}
</style>
