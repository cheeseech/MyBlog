<!--
 * @FileDescription: 后台全部文章数据组件
 * @Author: 徐茂华
 * @Date: 2020-11-18 16:41:42
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-08 10:58:06
 * @FilePath: \src\components\TotalArticleData.vue
-->
<template>
  <div id="totalData">
    <el-row :gutter="20">
      <el-col :span="8">
        <!-- 浏览次数 -->
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
              total_data.views
            }}</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <!-- 评论次数 -->
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
              total_data.comments
            }}</span>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <!-- 文章篇数 -->
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
              total_data.articles
            }}</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getRequest } from "@/../untils/axiosApi";
export default {
  props: ["total_data"],
  data() {
    return {
      msgImg: "http://images.cheese.host/msg.png", // 评论条数
      eyeImg: "http://images.cheese.host/eye.png", // 浏览次数
      articleImg: "http://images.cheese.host/article.png" // 文章篇数
    };
  }
};
</script>
