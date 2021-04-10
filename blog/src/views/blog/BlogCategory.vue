<!--
 * @FileDescription: 博客专栏组件
 * @Author: 徐茂华
 * @Date: 2020-08-07 17:47:56
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-05 16:23:28
 * @FilePath: \src\views\blog\BlogCategory.vue
-->
<template>
  <div id="BlogCategory" v-if="articles">
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
        :class="{ isActive: checkedCateName == '暂无' }"
        class="m-btn"
        v-backgroundColor
        @click="changeCategory(checkedAllSummary, '暂无')"
        ><b>全部</b><span class="m-span">{{ totalArticleLen }}</span></el-button
      >

      <!-- 专栏按钮 -->
      <el-button
        :class="{ isActive: checkedCateName == item.cate_name }"
        class="m-btn btn-click"
        :name="item.cate_name"
        v-backgroundColor
        v-for="item in categories"
        :key="item.cate_id"
        @click="changeCategory(item.cate_summary, item.cate_name)"
        ><b>{{ item.cate_name }}</b
        ><span class="m-span">{{ item.counts }}</span></el-button
      >

      <!-- 专栏介绍 -->
      <el-divider><b>专栏介绍</b></el-divider>
      <p class="font-color">
        {{ checkedCategorySummary }}
      </p>
    </el-card>

    <!-- 文章列表 -->
    <articleInfo :initial_articles="articles"></articleInfo>

    <!-- 分页 -->
    <el-pagination
      background
      layout="prev, pager, next"
      :total="checkedCateArticleLen"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="getArticlesByCurrentPage"
    ></el-pagination>
  </div>
</template>

<script>
import axios from "axios";
import { eventBus } from "@/main";
import { Message } from "element-ui";
import articleInfo from "@/components/ArticleInfo";
import { getRequest, postRequest } from "@/../untils/axiosApi";

export default {
  name: "BlogCategory",
  data() {
    return {
      title: "", // 标题搜索关键字
      pageSize: 6, // 分页长度
      flag: false, // 存储请求时的response
      currentPage: 1, // 当前页码
      categoryLen: 0, // 总专栏数
      articles: null, // 文章列表集合
      categories: null, // 专栏数据集合
      checkedCateName: "暂无", // 当前选中专栏名
      totalArticleLen: 0, // 所有专栏下总文章数
      checkedAllSummary: "尚未选择专栏呢", // 选中全部时专栏介绍
      checkedCateArticleLen: 0, // 选中专栏下文章数
      checkedCategorySummary: "尚未选择专栏呢" // 当前选中专栏介绍
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
        postRequest("/article/category/" + categoryName + "/", page),
        getRequest("/category/counts")
      ];
    } else {
      list = [postRequest("/article/page/", page), getRequest("/category/counts")];
    }
    //请求数据
    axios.all(list).then(
      axios.spread((articleResponse, categoryResponse) => {
        next(vm => vm.setData(articleResponse, categoryResponse, categoryName));
      })
    );
  },
  mounted() {
    let vm = this;

    //兄弟组件传值：根据搜索框值检索文章
    //判断是否是当前路由
    eventBus.$on("title", data => {
      vm.getArticlesByTitleAndCategory(data);
    });
  },
  //兄弟组件解绑，避免切换组件后造成调用多次情况
  beforeDestroy() {
    eventBus.$off("title");
  },
  methods: {
    /**
     * @description: 处理专栏及专栏文章相关数据
     * @param {Map} articleResponse
     * @param {Map} categoryResponse
     * @param {String} categoryName
     * @return void
     */
    setData(articleResponse, categoryResponse, categoryName) {
      //设置专栏名
      if (categoryName) {
        this.checkedCateName = categoryName;
        //设置选中专栏概述
        var categoriesInfo = categoryResponse.data;
        for (var i = 0; i < categoriesInfo.length; i++) {
          if (categoriesInfo[i].cate_name === categoryName) {
            this.checkedCategorySummary = categoriesInfo[i].cate_summary;
          }
        }
      }

      // 处理文章相关数据
      this.setArticleData(articleResponse);

      //处理专栏文章数数据
      if (categoryResponse.status == 200) {
        this.categories = categoryResponse.data;
        this.tagsLen = this.categories.length;
        //计算所有文章数
        for (var i = 0; i < this.tagsLen; i++) {
          this.totalArticleLen += this.categories[i].counts;
        }
      }
    },

    /**
     * @description: 处理文章相关数据
     * @param {Map} articleResponse
     * @return void
     */
    setArticleData(articleResponse) {
      if (articleResponse.status == 200) {
        //文章信息
        this.articles = articleResponse.data.content;
        //总条数
        this.checkedCateArticleLen = articleResponse.data.totalSize;
        //当前页
        this.currentPage = articleResponse.data.pageNum;
        // 获取完毕后回到顶部
        this.common.backTop();
      }
    },

    /**
     * @description: 改变选中专栏并获取该专栏下文章数据集合
     * @param {String} summary
     * @param {String} categoryName
     * @return void
     */
    changeCategory(summary, categoryName) {
      //改变专栏名
      this.checkedCateName = categoryName;
      //改变专栏介绍
      this.checkedCategorySummary = summary;
      // 重置搜索关键字
      this.title = "";
      eventBus.$emit("cleared", true);
      //根据条件获取文章
      this.getArticlesByCurrentPage(1);
    },

    /**
     * @description: 根据页码及专栏名获取文章数据集合
     * @param {Map} currentPage
     * @return void
     */
    getArticlesByCurrentPage(currentPage) {
      var page = { pageNum: currentPage, pageSize: 6 };
      //根据专栏名及标题获取文章
      if (this.checkedCateName === "暂无") {
        postRequest("/article/" + this.title, page).then(response => {
          this.setArticleData(response);
          // 是否显示消息提示
          if (this.flag == true) {
            this.searchMessage(response);
          }
        });
      } else {
        postRequest(
          "/article/category/" + this.checkedCateName + "/" + this.title,
          page
        ).then(response => {
          this.setArticleData(response);

          // 是否显示消息提示
          if (this.flag == true) {
            this.searchMessage(response);
          }
        });
      }
    },

    /**
     * @description: 根据标题及专栏名获取文章数据集合
     * @param {String} title
     * @return void
     */
    getArticlesByTitleAndCategory(title) {
      // 设置搜索关键字
      this.title = title;
      // 显示消息提示
      this.flag = true;
      // 根据关键字及专栏名获取文章数据集合
      this.getArticlesByCurrentPage(1);
    },

    /**
     * @description: 搜索后显示消息提示
     * @param {Map} response
     * @return void
     */
    searchMessage(response) {
      if (response.status == 200) {
        var articleLen = response.data.totalSize;
        // 查找成功消息提示
        Message({
          type: "success",
          dangerouslyUseHTMLString: true,
          message:
            "<strong>搜索成功！共检索到<span style='font-size:20px;color:orange'> " +
            articleLen +
            " </span>篇文章。</strong>"
        });
      } else {
        // 查找失败消息提示
        Message({
          type: "error",
          dangerouslyUseHTMLString: true,
          message: "<strong>搜索失败！未找到相关文章。</strong>"
        });
      }
      // 关闭消息提示
      this.flag = false;
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
.isActive {
  transition: transform 0.6s;
  transform: scale(1.2);
}
/* 设置按钮选中边框颜色 */
.el-button:active {
  border-color: #fff;
}
</style>
