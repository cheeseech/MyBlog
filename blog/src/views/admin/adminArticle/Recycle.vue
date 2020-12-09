<template>
  <div id="recycle">
    <el-table
      :data="
        articleInfo.filter(
          data =>
            !search || data.title.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      class="m-table"
    >
      <!-- 序号 -->
      <el-table-column label="序号" type="index" min-width="50" align="center">
      </el-table-column>

      <!-- 文章标题 -->
      <el-table-column
        label="文章标题"
        prop="title"
        min-width="180"
        sortable
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 文章类型 -->
      <el-table-column label="类型" min-width="80" align="center">
        <template slot-scope="scope">
          <el-tag
            size="medium"
            type="danger"
            effect="dark"
            v-if="scope.row.typeId == 1"
            >{{ scope.row.typeName }}</el-tag
          >
          <el-tag
            size="medium"
            type="warning"
            effect="dark"
            v-if="scope.row.typeId == 2"
            >{{ scope.row.typeName }}</el-tag
          >
          <el-tag
            size="medium"
            type="success"
            effect="dark"
            v-if="scope.row.typeId == 3"
            >{{ scope.row.typeName }}</el-tag
          >
        </template>
      </el-table-column>

      <!-- 文章专栏 -->
      <el-table-column
        label="专栏"
        prop="cateName"
        min-width="80"
        align="center"
      >
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column label="创建时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 删除时间 -->
      <el-table-column label="删除时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column align="center" min-width="250">
        <template slot="header" slot-scope="scope">
          <el-input
            class="article-search"
            v-model="search"
            size="mini"
            placeholder="输入标题检索..."
          />
        </template>
        <template slot-scope="scope">
          <!-- 编辑文章 -->
          <el-button
            size="mini"
            @click="handleCurrentChange(scope.row)"
            type="primary"
            >编辑</el-button
          >
          &nbsp;
          <!-- 更改文章状态 -->
          <el-popconfirm
            confirmButtonText="确定还原"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定还原这一篇文章嘛？"
            @onConfirm="updateArticleState(scope.row.articleId)"
          >
            <el-button slot="reference" size="mini" type="success"
              >还原为草稿</el-button
            >
          </el-popconfirm>

          &nbsp;
          <!-- 删除文章 -->
          <el-popconfirm
            confirmButtonText="残忍删除"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定删除这一篇文章嘛？"
            @onConfirm="handleDelete(scope.row.articleId)"
          >
            <el-button slot="reference" size="mini" type="danger"
              >彻底删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
<script>
import { getRequest, deleteRequest, postRequest } from "@/../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "recycle",
  data() {
    return {
      search: "",
      articleInfo: []
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    //获取已被删除状态的文章
    axios.all([getRequest("/article/delete/")]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    //数据处理
    setData(response) {
      if (response.status == 200) {
        this.articleInfo = response.data;
      }
    },
    //获取已被删除状态的文章数据
    getArticle() {
      getRequest("/article/delete/").then(response => {
        this.setData(response);
      });
    },
    //编辑文章
    modifiedArticle(articleId) {},
    //更新文章状态
    updateArticleState(articleId) {
      postRequest("/admin/article/" + articleId + "/" + 0).then(response => {
        if (response.status == 201) {
          //重新获取文章数据
          this.getArticle();
          Message.success("还原文章状态成功！");
        } else {
          Message.error("还原失败！请稍后再试！");
        }
      });
    },
    //执行删除文章
    handleDelete(articleId) {
      deleteRequest("/admin/article/" + articleId).then(response => {
        if (response.status == 204) {
          //重新获取文章数据
          this.getArticle();
          Message.success("删除文章成功！");
        } else {
          Message.error("删除文章失败！请稍后再试！");
        }
      });
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
</style>
