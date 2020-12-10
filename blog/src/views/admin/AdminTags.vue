<template>
  <div id="tags">
    <el-table
      :data="
        tags.filter(
          data =>
            !search || data.tagName.toLowerCase().includes(search.toLowerCase())
        )
      "
      highlight-current-row
      class="m-table"
    >
      <!-- 序号 -->
      <el-table-column label="序号" type="index"> </el-table-column>

      <!-- 标签名称 -->
      <el-table-column label="标签名" prop="tagName" sortable show-overflow-tooltip>
      </el-table-column>

      <!-- 标签类型 -->
      <el-table-column label="标签类型" show-overflow-tooltip>
        <template slot-scope="scope">
          <el-tag size="medium" :type="scope.row.tagType" effect="dark">{{
            scope.row.tagType
          }}</el-tag>
        </template>
      </el-table-column>

      <!-- 工具列 -->
      <el-table-column>
        <template slot="header" slot-scope="scope">
          <el-input
            class="m-search"
            v-model="search"
            size="mini"
            placeholder="输入标签名搜索"
          />
          &emsp;
          <el-button type="primary" size="mini" @click="handleCurrentChange()"
            >新增标签</el-button
          >
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

    <!-- Dialog -->
    <el-dialog
      title="编辑标签"
      :visible.sync="centerDialogVisible"
      width="30%"
      center
    >
      <!-- 表单 -->
      <el-form
        :model="form"
        :rules="rules"
        ref="ruleForm"
        label-position="right"
      >
        <el-form-item
          label="标签名:"
          prop="tagName"
          :label-width="formLabelWidth"
        >
          <el-input
            id="tagName"
            v-focus
            v-model="form.tagName"
            autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item
          label="标签类型:"
          prop="tagType"
          :label-width="formLabelWidth"
        >
          <el-select v-model="form.tagType" placeholder="请选择标签：">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.value"
              :value="item.value"
            >
              <span class="tag-value">{{ item.value }}</span>
              <span class="tag-label">{{ item.label }}</span>
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import axios from "axios";
import { Message } from "element-ui";
import {
  getRequest,
  postRequest,
  putRequest,
  deleteRequest
} from "@/../untils/axiosApi";

export default {
  name: "tags",
  data() {
    return {
      tags: [],
      search: "",
      formLabelWidth: "100px",
      centerDialogVisible: false,
      form: {
        tagName: "",
        tagType: ""
      },
      rules: {
        tagName: [
          { required: true, message: "请输入标签名称", trigger: "blur" }
        ],
        tagType: [
          { required: true, message: "请选择标签类型", trigger: "blur" }
        ]
      },
      options: [
        {
          value: "success",
          label: "绿色"
        },
        {
          value: "primary",
          label: "蓝色"
        },
        {
          value: "warning",
          label: "黄色"
        },
        {
          value: "danger",
          label: "红色"
        },
        {
          value: "info",
          label: "灰色"
        }
      ]
    };
  },
  //在路由跳转前获取数据
  beforeRouteEnter(to, from, next) {
    axios.all([getRequest("/tags/")]).then(
      axios.spread(response => {
        next(vm => {
          vm.setData(response);
        });
      })
    );
  },
  methods: {
    //获取所有标签数据
    getTags() {
      getRequest("/tags/").then(response => {
        if (response.status == 200) {
          this.tags = response.data;
        }
      });
    },
    //点击提交执行方法
    onSubmit() {
      this.$refs["ruleForm"].validate(valid => {
        if (valid) {
          //关闭Dialog
          this.centerDialogVisible = false;
          //tagId为空则添加标签
          if (this.form.tagId === "") {
            this.insertTag();
            //tagId不为空则更新标签
          } else {
            this.updateTag();
          }
        } else {
          return false;
        }
      });
    },
    //关闭Dialog，清除表单
    resetForm() {
      this.$refs["ruleForm"].resetFields();
      //关闭Dialog
      this.centerDialogVisible = false;
    },
    //根据表单更新标签
    updateTag() {
      postRequest("/admin/tags/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          //重新获取标签数据
          this.getTags();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //根据表单添加标签
    insertTag() {
      putRequest("/admin/tags/", this.form).then(response => {
        if (response.status == 201) {
          Message.success(response.msg);
          //重新获取标签数据
          this.getTags();
        } else {
          Message.error(response.msg);
        }
      });
    },
    //数据处理
    setData(response) {
      if (response.status == 200) {
        this.tags = response.data;
      }
    },
    //点击按钮弹出Dialog赋值表单
    handleCurrentChange(row) {
      //弹出Dialog
      this.centerDialogVisible = true;
      //若row不为undefined时表示编辑标签，将行数据赋值给表单
      if (row != undefined) {
        this.form = {
          tagId: row.tagId,
          tagName: row.tagName,
          tagType: row.tagType
        };
        //若row为undefined时表示添加标签，将表单内容置为空
      } else {
        this.form = {
          tagId: "",
          tagName: "",
          tagType: ""
        };
      }
    },
    //执行标签删除方法
    handleDelete(index, row) {
      deleteRequest("/admin/tags/" + row.tagId).then(response => {
        if (response.status == 204) {
          Message.success(response.msg);
          this.getTags();
        } else {
          Message.error(response.msg);
        }
      });
    }
  },
  directives: {
    focus: {
      //聚焦输入框
      inserted: function(el) {
        var element = document.getElementById("tagName");
        element.focus();
      }
    }
  }
};
</script>
<style>
.el-table td,
.el-table th {
  text-align: center;
}
.el-main {
  padding: 0;
}
.el-switch {
  height: auto;
}
.tag-value {
  float: left;
}
.tag-label {
  float: right;
  color: #8492a6;
  font-size: 13px;
}
</style>
