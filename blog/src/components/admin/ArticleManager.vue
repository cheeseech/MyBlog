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
      @selection-change="handleSelectionChange()"
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index" min-width="50" align="center">
      </el-table-column>

      <el-table-column
        label="文章标题"
        prop="title"
        min-width="200"
        sortable
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>
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
      <el-table-column label="类型" min-width="80" align="center">
        <template slot-scope="scope">
          <el-tag size="medium" type="danger" effect="dark" v-if="scope.row.typeId == 1">{{ scope.row.typeName }}</el-tag>
          <el-tag size="medium" type="warning" effect="dark" v-if="scope.row.typeId == 2">{{ scope.row.typeName }}</el-tag>
          <el-tag size="medium" type="success" effect="dark" v-if="scope.row.typeId == 3">{{ scope.row.typeName }}</el-tag>
        </template>
      </el-table-column>
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
      <el-table-column
        label="专栏"
        prop="cateName"
        min-width="80"
        align="center"
      >
      </el-table-column>
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
            type="danger"
            v-if="scope.row.articleState === -1"
            effect="dark"
            >已删除</el-tag
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
      <el-table-column label="创建时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateFormat }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="180">
        <template slot="header" slot-scope="scope">
          <el-input
            style="width:80%"
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
import { getRequest } from "../../../untils/axiosApi";
import { deleteRequest } from "../../../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "articleManager",
  data() {
    return {
      articleInfo: [],
      search: ""
    };
  },
  beforeRouteEnter(to, from, next){
    axios.all([getRequest("/article/noDe/")]).then(
        axios.spread(response => {
        next(vm=>{
            vm.setData(response)
        })
    }))
  },
  methods: {
    setData(response) {
        if (response.status == 200) {
          this.articleInfo = response.data;
        }
    },
    handleDelete(index, row) {
      deleteRequest("/admin/article/" + row.articleId).then(response => {
        if (response.status == 204) {
          Message.success(response.msg);
          this.getArticle();
        } else {
          Message.error(response.msg);
        }
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
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

      return `${years}-${months}-${days} ${hours}:${minutes}:${seconds}`;
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
.autoEnter{
            width: 300px;
            height: 95px;
            word-break: break-all;

}
</style>
