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
      @selection-change="handleSelectionChange()"
      style="width: 100%;"
    >
      <el-table-column label="序号" type="index" min-width="50" align="center">
      </el-table-column>

      <el-table-column
        label="文章标题"
        prop="title"
        min-width="180"
        sortable
        align="center"
        show-overflow-tooltip
      >
      </el-table-column>
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
      <el-table-column
        label="专栏"
        prop="cateName"
        min-width="80"
        align="center"
      >
      </el-table-column>
      <el-table-column label="创建时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.publishTime | dateFormat }}</span>
        </template>
      </el-table-column>
      <el-table-column label="删除时间" sortable min-width="170" align="center">
        <template slot-scope="scope">
          <span>{{ scope.row.updateTime | dateFormat }}</span>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="200">
        <template slot="header" slot-scope="scope">
          <el-input
            style="width:80%"
            v-model="search"
            size="mini"
            placeholder="输入标题检索..."
          />
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="handleCurrentChange(scope.row)"
            type="primary"
            >查看</el-button
          >
          <el-button
            size="mini"
            @click="handleCurrentChange(scope.row)"
            type="success"
            >还原为草稿</el-button
          >
          &nbsp;
          <el-popconfirm
            confirmButtonText="残忍删除"
            cancelButtonText="我再想想"
            icon="el-icon-info"
            iconColor="red"
            title="确定删除这一条信息吗？"
            @onConfirm="handleDelete(scope.$index, scope.row)"
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
import { getRequest } from "../../../untils/axiosApi";
import { deleteRequest } from "../../../untils/axiosApi";
import { Message } from "element-ui";
import axios from "axios";

export default {
  name: "recycle",
  data() {
    return {
      articleInfo: [],
      search: ""
    };
  },
  beforeRouteEnter(to, from, next) {
    axios.all([getRequest("/article/de/")]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    setData(response) {
      if (response.status == 200) {
        this.articleInfo = response.data;
      }
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
</style>
