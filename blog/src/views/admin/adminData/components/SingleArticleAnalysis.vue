<template>
  <div id="single" v-show="articleData">
    <!-- 文章日期数据筛选 -->
    <div style="margin-top:20px;margin-left:40px;">
      <span style="font-size: 19px;font-weight:bold">文章数据报表：</span>
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

    <el-table
      :data="articleData"
      highlight-current-row
      style="margin-top:20px;width:90%;margin-left: 5%;margin-bottom: 25px;"
    >
      <!-- 文章标题 -->
      <el-table-column label="文章标题" sortable min-width="200">
        <template slot-scope="scope">
          <span style="font-size:17px">{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <!-- 创建日期 -->
      <el-table-column label="创建日期" sortable align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>
      <!-- 浏览量 -->
      <el-table-column label="浏览" prop="views" sortable align="center">
      </el-table-column>
      <!-- 点赞数 -->
      <el-table-column label="点赞" prop="likes" sortable align="center">
      </el-table-column>
      <!-- 评论数 -->
      <el-table-column label="评论" prop="comments" sortable align="center">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { getRequest } from "@/../untils/axiosApi";
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
        "/admin/single/" + this.timesTables[0] + "/" + this.timesTables[1]
      ).then(response => {
        if (response.status == 200) {
          this.articleData = response.data;
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
