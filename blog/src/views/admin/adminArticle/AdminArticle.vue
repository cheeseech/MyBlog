<template>
  <div id="articleManager">
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
        min-width="200"
        sortable
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>

      <!-- 简介 -->
      <el-table-column
        label="简介"
        min-width="300"
        align="center"
        show-overflow-tooltip
      >
        <template slot-scope="scope">
          <el-tooltip placement="top" effect="light">
            <div slot="content" class="autoEnter">{{ scope.row.summary }}</div>
            <el-button>查看详情</el-button>
          </el-tooltip>
        </template>
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

      <!-- 文章标签列表 -->
      <el-table-column label="标签" min-width="70" header-align="center">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <el-button
              type="primary"
              round
              class="btnPadded"
              v-for="tag in scope.row.tags"
              :key="tag.tagId"
              :type="tag.tagType"
              effect="dark"
            >
              {{ tag.tagName }}
            </el-button>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium" type="primary" effect="dark">详情</el-tag>
            </div>
          </el-popover>
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

      <!-- 文章状态 -->
      <el-table-column label="状态" min-width="90" align="center">
        <template slot-scope="scope">
          <el-tag
            size="medium"
            type="success"
            v-if="scope.row.articleState === 1"
            effect="dark"
            >已发布</el-tag
          >
          <el-tag
            size="medium"
            type="primary"
            v-if="scope.row.articleState === 0"
            effect="dark"
            >草稿箱</el-tag
          >
        </template>
      </el-table-column>

      <!-- 创建时间 -->
      <el-table-column label="创建时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 更新时间 -->
      <el-table-column label="更新时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateTimeLongFormat }}</span>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column align="center" min-width="180">
        <template slot="header" slot-scope="scope">
          <el-input
            class="article-search"
            v-model="search"
            size="mini"
            placeholder="输入标题检索..."
          />
        </template>
        <template slot-scope="scope">
          <el-button size="mini" @click="handleCurrentChange(scope.row)"
            >编辑</el-button
          >

          &emsp;
          <!-- 删除文章 -->
          <el-popconfirm
            confirmButtonText="残忍删除"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定删除这一条信息吗？"
            @onConfirm="handleDelete(scope.$index, scope.row)"
          >
            <el-button slot="reference" size="mini" type="danger"
              >删除</el-button
            >
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getRequest, postRequest } from "@/../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "articleManager",
  data() {
    return {
      search: "",
      articleInfo: []
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    //获取未被标记删除的文章
    axios.all([getRequest("/article/notDelete/")]).then(
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
    //获取未被删除的文章数据
    getArticle() {
      getRequest("/article/notDelete/").then(response => {
        this.setData(response);
      });
    },
    //执行删除文章
    handleDelete(index, row) {
      postRequest("/admin/article/" + row.articleId + "/" + -1).then(
        response => {
          if (response.status == 201) {
            //重新获取数据
            this.getArticle();
            Message.success("删除文章成功！");
          } else {
            Message.error("删除失败！请稍后再试。");
          }
        }
      );
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
.autoEnter {
  width: 300px;
  height: 95px;
  word-break: break-all;
}
</style>
