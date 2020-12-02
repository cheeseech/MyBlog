<template>
  <div id="BlogCategory" v-if="initArticles">
    <el-card class="m-radius-small m-b-margin" id="m-card">
      <!-- 头部 -->
      <div slot="header" class="clearfix">
        <span
          ><strong class="m-font-size-big">
            <svg class="icon m-svg-size m-font-size-big" aria-hidden="true">
              <use xlink:href="#icon-tuisong"></use>
            </svg>
            全部专栏</strong
          ></span
        >
        <span class="m-span-right-pd"
          >共
          <strong class="m-font-size-big-orange">{{ tagsLen }}</strong> 个</span
        >
      </div>
      <!-- 专栏列表 -->
      <!-- 全部按钮 -->
      <el-button
        class="m-btn btn-click"
        id="all"
        v-backgroundColor
        @click="changeCategory(allSummary, '暂无')"
        ><b>全部</b><span class="m-span">{{ articleCount }}</span></el-button
      >
      <!-- 专栏按钮 -->
      <el-button
        class="m-btn btn-click"
        :name="item.cate_name"
        v-backgroundColor
        v-for="item in category"
        :key="item.cate_id"
        @click="changeCategory(item.cate_summary, item.cate_name)"
        ><b>{{ item.cate_name }}</b
        ><span class="m-span">{{ item.counts }}</span></el-button
      >
      <!-- 专栏介绍 -->
      <el-divider><b>专栏介绍</b></el-divider>
      <p class="font-color">
        {{ showSumary }}
      </p>
    </el-card>
    <!-- 文章列表 -->
    <articleInfo :initial_articles="initArticles"></articleInfo>

    <!-- 分页 -->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="totalSize"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="getArticlesByCurrentPage"
    ></el-pagination>
  </div>
</template>

<script>
import axios from "axios";
import { Message } from "element-ui";
import { postRequest } from "@/../untils/axiosApi";
import { getRequest } from "@/../untils/axiosApi";
import articleInfo from "@/components/ArticleInfo";

export default {
  name: "BlogCategory",
  data() {
    return {
      tagsLen: 0,
      pageSize: 6,
      cateName: "暂无",
      totalSize: 0,
      currentPage: 1,
      category: null,
      articleCount: 0,
      firstFocus: true,
      initArticles: null,
      allSummary: "尚未选择专栏呢",
      showSumary: "尚未选择专栏呢"
    };
  },
  //在路由组件跳转前获取数据
  beforeRouteEnter(to, from, next) {
    var list,
      categoryName = to.params.value,
      page = { pageNum: 1, pageSize: 6 };
    //判断路由组件是否传参，是则改变请求语句
    if (categoryName) {
      list = [
        postRequest("/article/category/" + categoryName, page),
        getRequest("/category/counts")
      ];
    } else {
      list = [
        postRequest("/article/page/", page),
        getRequest("/category/counts")
      ];
    }
    //请求数据
    axios.all(list).then(
      axios.spread((articleResponse, categoryResponse) => {
        next(vm => vm.setData(articleResponse, categoryResponse, categoryName));
      })
    );
  },
  updated() {
    //首次加载时才点击按钮
    if (this.firstFocus) {
      this.chooseCategory(this.cateName);
      this.firstFocus = false;
    }
  },
  methods: {
    //根据路由传参来点击按钮，没有则默认选择全部按钮
    chooseCategory(value) {
      //设置定时器防止跑到页面底部
      setTimeout(function() {
        if (value === "暂无") {
          var element = document.getElementById("all");
          element.focus();
        } else {
          var element = document.getElementsByName(value);
          element[0].focus();
          element[0].click();
        }
      }, 500);
    },
    //数据处理
    setData(articleResponse, categoryResponse, categoryName) {
      this.setArticleData(articleResponse);
      //获取专栏文章数数据
      if (categoryResponse.status == 200) {
        this.category = categoryResponse.data;
        this.tagsLen = this.category.length;
        //计算所有文章数
        for (var i = 0; i < this.tagsLen; i++) {
          this.articleCount += this.category[i].counts;
        }
      }
      //设置专栏名
      if (categoryName) {
        this.cateName = categoryName;
      }
    },
    //设置文章数据
    setArticleData(articleResponse) {
      if (articleResponse.status == 200) {
        //文章信息
        this.initArticles = articleResponse.data.content;
        //总条数
        this.totalSize = articleResponse.data.totalSize;
        //当前页
        this.currentPage = articleResponse.data.pageNum;
        //消息提示
        Message({
          type: "success",
          message: "成功切换了!",
          duration: 1000
        });
      }
    },
    //改变专栏
    changeCategory(summary, categoryName) {
      //改变专栏介绍
      this.showSumary = summary;
      //改变专栏名
      this.cateName = categoryName;
      //根据条件获取文章
      this.getArticlesByCurrentPage(1);
    },
    //根据页码获取文章
    getArticlesByCurrentPage(currentPage) {
      var page = { pageNum: currentPage, pageSize: 6 };
      //根据条件获取文章
      if (this.cateName === "暂无") {
        postRequest("/article/page/", page).then(response => {
          this.setArticleData(response);
        });
      } else {
        postRequest("/article/category/" + this.cateName, page).then(
          response => {
            if (response.status == 200) {
              this.setArticleData(response);
            }
          }
        );
      }
    }
  },
  directives: {
    //伪随机颜色
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
  components: { articleInfo }
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
  margin: 10px 7px 0px 7px;
}
.font-color {
  text-align: center;
  color: #303133;
}
/* 按钮选中效果 */
.btn-click {
  transition: transform 0.5s;
}
.btn-click:focus {
  /*倍数*/
  transform: scale(1.2);
}
</style>
