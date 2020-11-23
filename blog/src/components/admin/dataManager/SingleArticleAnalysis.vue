<template>
  <div id="single" v-show="articleData">
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
      @selection-change="handleSelectionChange()"
      style="margin-top:20px;width:90%;margin-left: 5%;margin-bottom: 25px;"
    >
      <el-table-column label="文章标题" sortable min-width="200">
          <template slot-scope="scope">
              <span style="font-size:17px">{{ scope.row.title }}</span>
          </template>
      </el-table-column>
      <el-table-column label="创建日期" sortable align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column label="浏览" prop="views" sortable align="center">
      </el-table-column>
      <el-table-column label="点赞" prop="likes" sortable align="center">
      </el-table-column>
      <el-table-column label="评论" prop="comments" sortable align="center">
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { getRequest } from "../../../../untils/axiosApi";
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
    timesTables: function() {
      getRequest(
        "/admin/single/" + this.timesTables[0] + "/" + this.timesTables[1]
      ).then(response => {
        if (response.status == 200) {
          this.articleData = response.data;
          console.log(this.articleData);
        }
      });
    }
  },
  mounted() {
    this.setTime();
  },
  methods: {
    setTime() {
      const end = new Date();
      const start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);

      this.timesTables = [start, end];
    }
  },
  filters: {
    dateFormat: function(value) {
      var date = new Date(value);

      var years = date.getFullYear();
      var months = (date.getMonth() + 1).toString().padStart(2, "0");
      var days = date
        .getDate()
        .toString()
        .padStart(2, "0");
      var hours = date
        .getHours()
        .toString()
        .padStart(2, "0");
      var minutes = date
        .getMinutes()
        .toString()
        .padStart(2, "0");
      var seconds = date
        .getSeconds()
        .toString()
        .padStart(2, "0");

      return `${years}年${months}月${days}日 ${hours}:${minutes}:${seconds}`;
    }
  }
};
</script>
