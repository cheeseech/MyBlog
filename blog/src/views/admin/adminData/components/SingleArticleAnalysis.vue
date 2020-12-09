<template>
  <div id="single" v-show="articleData">
    <!-- 文章日期数据筛选 -->
    <div class="m-all-single-picker">
      <span>文章数据报表：</span>
      <el-date-picker
        v-model="timesTables"
        type="daterange"
        align="right"
        unlink-panels
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        :picker-options="pickerOptions"
      >
      </el-date-picker>
    </div>

    <el-divider></el-divider>

    <!-- 文章数据表格 -->
    <el-table :data="articleData" highlight-current-row class="m-single-category-table">
      <!-- 文章标题 -->
      <el-table-column label="文章标题" sortable min-width="150">
        <template slot-scope="scope">
          <span class="article-title">{{ scope.row.title }}</span>
        </template>
      </el-table-column>

      <!-- 创建日期 -->
      <el-table-column label="创建日期" sortable align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 创建日期 -->
      <el-table-column label="更新日期" sortable align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 浏览量 -->
      <el-table-column label="浏览" sortable align="center">
        <template slot-scope="scope">
          <el-tag type="primary" effect="dark">{{ scope.row.views }}</el-tag>
        </template>
      </el-table-column>

      <!-- 点赞数 -->
      <el-table-column label="点赞" prop="likes" sortable align="center">
        <template slot-scope="scope">
          <el-tag type="danger" effect="dark">{{ scope.row.likes }}</el-tag>
        </template>
      </el-table-column>

      <!-- 评论数 -->
      <el-table-column label="评论" prop="comments" sortable align="center">
        <template slot-scope="scope">
          <el-tag type="warning" effect="dark">{{ scope.row.comments }}</el-tag>
        </template>
      </el-table-column>
      
    </el-table>
  </div>
</template>
<script>
import { getRequest } from "@/../untils/axiosApi";
import { Message } from "element-ui";
export default {
  name: "single",
  data() {
    return {
      articleData: null,
      timesTables: "",
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6;
        },
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      }
    };
  },
  watch: {
    //当时间变化时根据选择时间获取数据
    timesTables: function() {
      getRequest(
        "/admin/days/single/" + this.timesTables[0] + "/" + this.timesTables[1]
      ).then(response => {
        if (response.status == 200) {
          this.articleData = response.data;
          Message.success("日期文章数据选择成功！");
        } else {
          Message.error("日期文章数据选择失败！请稍后再试。");
        }
      });
    }
  },
  mounted() {
    //初始化时间
    this.setTime();
  },
  methods: {
    //时间初始化为近90天
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.timesTables = [start, end];
    }
  }
};
</script>
<style>
.article-title {
  font-size: 17px;
  font-weight: bold;
}
</style>
