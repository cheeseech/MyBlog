<template>
  <div id="totalData">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <el-image
            :src="eyeImg"
            fit="contain"
            class="img-transform"
            style="width:20%;height:20%;"
          ></el-image>
          <div style="float: right;text-align: center;margin-right: 10px;">
            <span style="font-size:25px;font-weight: bold;color:#606266"
              >浏览次数</span
            ><br />
            <span style="font-size:20px;font-weight: bold;">{{
              totalData.views
            }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-image
            :src="heartImg"
            fit="contain"
            class="img-transform"
            style="width:20%;height:20%;"
          ></el-image>
          <div style="float: right;text-align: center;margin-right: 10px;">
            <span style="font-size:25px;font-weight: bold;color:#606266"
              >点赞次数</span
            ><br />
            <span style="font-size:20px;font-weight: bold;">{{
              totalData.likes
            }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-image
            :src="msgImg"
            fit="contain"
            class="img-transform"
            style="width:20%;height:20%;"
          ></el-image>
          <div style="float: right;text-align: center;margin-right: 10px;">
            <span style="font-size:25px;font-weight: bold;color:#606266"
              >评论条数</span
            ><br />
            <span style="font-size:20px;font-weight: bold;">{{
              totalData.comments
            }}</span>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card>
          <el-image
            :src="articleImg"
            fit="contain"
            class="img-transform"
            style="width:20%;height:20%;"
          ></el-image>
          <div style="float: right;text-align: center;margin-right: 10px;">
            <span style="font-size:25px;font-weight: bold;color:#606266"
              >文章篇数</span
            ><br />
            <span style="font-size:20px;font-weight: bold;">{{
              totalData.articles
            }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getRequest } from "../../../untils/axiosApi";
export default {
  data() {
    return {
      msgImg: require("./../../assets/images/msg.png"),
      eyeImg: require("./../../assets/images/eye.png"),
      heartImg: require("./../../assets/images/heart.png"),
      articleImg: require("./../../assets/images/article.png"),
      totalData: []
    };
  },
  created() {
    getRequest("/admin/article/totalData/").then(response => {
      this.totalData = response.data;
    });
  }
};
</script>
