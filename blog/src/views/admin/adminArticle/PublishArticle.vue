<!--
 * @FileDescription: 后台文章发布组件
 * @Author: 徐茂华
 * @Date: 2020-11-06 14:50:30
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-03-28 17:16:19
 * @FilePath: \src\views\admin\adminArticle\PublishArticle.vue
-->
<template>
  <div id="publishArticle">
    <div class="mavonEditor">
      <div class="fotter">
        <el-row :gutter="20">
          <el-col :span="18">
            <!-- 文章标题 -->
            <el-input
              type="text"
              placeholder="给文章起个名吧..."
              v-model="form.title"
              maxlength="30"
              show-word-limit
            ></el-input>
          </el-col>
          <el-col :span="6">
            <!-- 按钮组 -->
            <el-button-group>
              <el-button type="success" @click="drafted(0)" round
                >存为草稿</el-button
              >
              <el-button type="primary" @click="dialogFormVisible = true" round
                >发布文章</el-button
              >
            </el-button-group>
          </el-col>
        </el-row>
      </div>
      <!-- markdown编辑区 -->
      <mavon-editor
        v-model="value"
        @save="save"
        @change="change"
        placeholder="读书破万卷，下笔如有神..."
        fontSize="16px"
        style="height: 100%;"
      />
    </div>
    <!-- dialog -->
    <el-dialog title="发布文章" :visible.sync="dialogFormVisible">
      <el-form
        :model="form"
        :rules="rules"
        ref="ruleForm"
        class="demo-ruleForm"
        :label-width="formLabelWidth"
      >
        <!-- 文章标签 -->
        <el-form-item label="文章标签:">
          <el-tag
            style="margin-right: 8px;"
            :key="tag.value"
            v-for="tag in form.tags"
            closable
            :type="tag.type"
            :disable-transitions="false"
            effect="dark"
            @close="handleClose(tag)"
          >
            {{ tag.value }}
          </el-tag>
          <!-- 添加文章标签 -->
          <el-popover placement="bottom-start" width="400" trigger="click">
            <el-button slot="reference" type="primary" class="publicBtnPadded"
              >+ 添加新标签</el-button
            >

            <div style="margin-bottom:20px;font-size: 16px;font-weight: bold;">
              <span v-if="dynamicTagsLen < 5"
                >还可添加{{ 5 - dynamicTagsLen }}个标签。</span
              >
              <span v-else style="color: red">最多只能添加5个标签。</span>

              <el-button
                type="danger"
                class="publicBtnPadded"
                style="float: right;"
                @click="getTopTags()"
                >热门标签</el-button
              >
            </div>
            <!-- 文章标签筛选 -->
            <el-input
              placeholder="请输入内容"
              v-model="search"
              clearable
              @keyup.enter.native="querySearchTags"
            >
            </el-input>

            <el-button
              :class="[
                'search-tag',
                'publicBtnPadded',
                tag.state ? 'is-disabled' : ''
              ]"
              style="margin-top:10px;"
              :name="tag.value"
              :key="index"
              v-for="(tag, index) in filterTags"
              :type="tag.type"
              @click="selectTag(index, tag)"
            >
              {{ tag.value }}
            </el-button>
          </el-popover>
        </el-form-item>
        <!-- 文章专栏 -->
        <el-form-item label="文章专栏:" prop="cateId">
          <el-radio-group v-model="form.cateId">
            <el-radio-button
              v-for="(category, index) in categories"
              :key="index"
              :label="category.cateId"
              class="category-button"
              >{{ category.cateName }}</el-radio-button
            >
          </el-radio-group>
        </el-form-item>
        <!-- 文章类型 -->
        <el-form-item label="文章类型:" prop="typeId">
          <el-select v-model="form.typeId" placeholder="请选择..">
            <el-option label="原创" :value="1"></el-option>
            <el-option label="转载" :value="2"></el-option>
            <el-option label="翻译" :value="3"></el-option>
          </el-select>
        </el-form-item>
        <!-- 是否推荐 -->
        <el-form-item label="是否推荐:" prop="recommend">
          <el-radio-group v-model="form.recommend">
            <el-radio :label="1" border>是</el-radio>
            <el-radio :label="0" border>否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success" @click="drafted(0)">保存为草稿</el-button>
        <el-button @click="resetForm('ruleForm')">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm', 1)"
          >发 布</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>
<script>
import qs from "qs";
import $ from "jquery";
import axios from "axios";
import { Message } from "element-ui";
import { clickNode } from "@/views/blog/mavon";
import {
  getRequest,
  putArticleRequest,
  postArticleRequest
} from "@/../untils/axiosApi";

export default {
  name: "publishArticle",
  data() {
    return {
      value: "", // markdown数据
      initTags: [], // 初始化标签集合
      categories: [], // 专栏集合
      dynamicTagsLen: 0, // 已经添加标签数(<=5)
      filterTags: [], // 筛选后的标签集合
      search: "", // 标签筛选关键字
      dialogFormVisible: false, // dialog是否显示
      formLabelWidth: "120px", // label大小
      // 文章提交表单
      form: {
        title: "", // 文章标题
        cateId: "", // 专栏ID
        typeId: "", // 类型ID
        recommend: null, // 是否推荐
        tags: [], // 标签集合
        articleState: "", // 文章状态，0:草稿，1：发布
        mdContent: "", // markdown数据
        htmlContent: "" // html数据
      },
      // 验证规则
      rules: {
        cateId: [
          { required: true, message: "请选择文章专栏", trigger: "change" }
        ],
        typeId: [
          { required: true, message: "请选择文章类型", trigger: "change" }
        ],
        recommend: [
          { required: true, message: "是否推荐该文章", trigger: "change" }
        ]
      }
    };
  },
  beforeRouteEnter(to, from, next) {
    // 文章ID
    var articleId = to.params.value;
    var list = [getRequest("/tags/order/"), getRequest("/category/")];
    // 存在文章ID，表示编辑文章
    if (articleId !== undefined) {
      list = [
        getRequest("/tags/order/"),
        getRequest("/category/"),
        getRequest("/article/markdown/" + articleId)
      ];
    }

    axios.all(list).then(
      axios.spread((tagsResponse, categoriesResponse, articleResponse) => {
        next(vm =>
          vm.setData(tagsResponse, categoriesResponse, articleResponse)
        );
      })
    );
  },
  methods: {
    /**
     * @description: 进行标签、专栏和文章数据的处理
     * @param {Map} tagsResponse
     * @param {Map} categoriesResponse
     * @param {Map} articleResponse
     * @return {void}
     */
    setData(tagsResponse, categoriesResponse, articleResponse) {
      // 处理标签相关数据
      if (tagsResponse.status == 200) {
        this.initTags = tagsResponse.data;
        //设置Top15标签
        this.getTopTags();
      }

      // 处理专栏相关数据
      if (categoriesResponse.status == 200) {
        this.categories = categoriesResponse.data;
      }

      // 处理文章相关数据
      if (articleResponse !== undefined && articleResponse.status == 200) {
        var article = articleResponse.data;
        // 赋值表单
        this.form = {
          articleId: article.article_id,
          title: article.title,
          cateId: article.cate_id,
          typeId: article.type_id,
          tags: [],
          recommend: article.recommend ? 1 : 0
        };
        // 赋值文章标签数据
        console.log(article.tags);
        var tagsLen = article.tags.length;
        for (var i = 0; i < tagsLen; i++) {
          this.changeTagState(article.tags[i], 1);
          this.form.tags.push(article.tags[i]);
        }
        this.dynamicTagsLen = tagsLen;
        // 赋值文章内容数据
        this.value = article.md_content;
      }
    },

    /**
     * @description: 提交文章数据(单击发布文章时)
     * @param {String} formName
     * @param {Number} state
     * @return {void}
     */
    submitForm(formName, state) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.drafted(state);
        } else {
          return false;
        }
      });
    },

    /**
     * @description: 提交文章数据(点击草稿哦时)
     * @param {Number} state
     * @return {void}
     */
    drafted(state) {
      // 关闭Dialog
      this.dialogFormVisible = false;

      // 检查文章是否规范
      if (!this.checkedArticle()) {
        return;
      }

      //设置文章状态
      this.form.articleState = state;

      //处理标签对应后端标签实体
      for (var i = 0; i < this.dynamicTagsLen; i++) {
        //删除并返回第一个数据
        var ele = this.form.tags.shift();

        var setTag = {
          tagId: ele.tag_id,
          tagName: ele.value,
          tagType: ele.type
        };
        //添加到标签集合中
        this.form.tags.push(setTag);
      }

      if (this.form.articleId !== undefined) {
        // 更新文章数据
        postArticleRequest("/admin/article/", this.form).then(response => {
          // 显示消息提示
          this.postArticleMessage(response, state, 1);
        });
      } else {
        //提交表单数据到后台
        putArticleRequest("/admin/article/", this.form).then(response => {
          // 显示消息提示
          this.postArticleMessage(response, state, undefined);
        });
      }
    },

    /**
     * @description: 发布文章显示消息提示
     * @param {Map} response
     * @param {Number} state
     * @param {Number} articleId
     * @return {void}
     */
    postArticleMessage(response, state, articleId) {
      var successMessage, errorMessage;
      // 判断当前是草稿还是发布状态
      if (state == 1) {
        successMessage = "文章发布成功！";
        errorMessage = "文章发布失败！已存在该标题或请稍后再试！";
      } else {
        successMessage = "草稿保存成功！";
        errorMessage = "草稿保存失败！已存在该标题或请稍后再试！";
      }
      // 更新文章消息提示
      if (articleId !== undefined) {
        successMessage = "文章更新成功！";
        errorMessage = "文章更新失败！已存在该标题或请稍后再试！";
      }

      // 显示消息提示
      if (response.status == 201) {
        Message({
          type: "success",
          dangerouslyUseHTMLString: true,
          message: "<strong>" + successMessage + "</strong>"
        });

        // 跳转到文章管理页面
        this.goArticleManager();
      } else if (response.status == 500) {
        Message({
          type: "error",
          dangerouslyUseHTMLString: true,
          message: "<strong>未知错误！请联系管理员。</strong>"
        });
      } else {
        Message({
          type: "error",
          dangerouslyUseHTMLString: true,
          message: "<strong>" + errorMessage + "</strong>"
        });
      }
    },

    /**
     * @description: 重置表單
     * @param {String} formName
     * @return {void}
     */
    resetForm(formName) {
      // 关闭Dialog
      this.dialogFormVisible = false;
      // 清除表单
      //this.$refs[formName].resetFields();
    },

    /**
     * @description: 进行文章标题和文章内容的检查
     * @return {void}
     */
    checkedArticle() {
      //检查文章标题是否规范
      if (this.form.title === "") {
        Message({
          type: "error",
          dangerouslyUseHTMLString: true,
          message: "<strong>文章标题不能为空！</strong>"
        });
        return false;
      } else if (this.form.title.length < 5) {
        Message({
          type: "error",
          dangerouslyUseHTMLString: true,
          message: "<strong>文章标题不能太短！</strong>"
        });
        return false;
      }
      //检查文章内容是否规范
      if (this.form.mdContent === "") {
        Message({
          type: "error",
          dangerouslyUseHTMLString: true,
          message: "<strong>文章内容不能为空！</strong>"
        });
        return false;
      }
      return true;
    },

    /**
     * @description: mavon-editor保存文章
     * @param {String} markdown
     * @param {String} html
     * @return {void}
     */
    save(markdown, html) {
      //显示Dialog
      this.dialogFormVisible = true;
      //赋值文章数据
      this.form.mdContent = markdown;
      this.form.htmlContent = html;
    },

    /**
     * @description: mavon-editor编辑
     * @param {String} markdown
     * @param {String} html
     * @return {void}
     */
    change(markdown, html) {
      //赋值文章数据
      this.form.mdContent = markdown;
      this.form.htmlContent = html;
    },

    /**
     * @description: 获取前15个热门标签
     * @return {void}
     */
    getTopTags() {
      // 截取前15的标签
      this.filterTags = this.initTags.slice(0, 15);
    },

    /**
     * @description: 根据名称模糊查询标签
     * @return {void}
     */
    querySearchTags() {
      //初始化筛选标签集合
      this.filterTags = [];
      //搜索关键字
      var search = this.search.toLowerCase();

      for (var i = 0; i < this.initTags.length; i++) {
        //名称有效时添加到筛选后的标签集合中
        if (this.initTags[i].value.toLowerCase().indexOf(search) !== -1) {
          this.filterTags.push(this.initTags[i]);
        }
      }
    },

    /**
     * @description: 选择标签
     * @param {Number} index
     * @param {Map} tag
     * @return {void}
     */
    selectTag(index, tag) {
      //找到筛选后的标签元素集合
      var _dom = document.querySelectorAll(".search-tag");
      if (_dom && this.dynamicTagsLen < 5) {
        //若标签未禁用则禁用标签并改变状态值然后添加进动态标签集合
        if (!_dom[index].classList.contains("is-disabled")) {
          //更改标签状态
          this.changeTagState(tag, 1);
          this.form.tags.push(tag);
          this.dynamicTagsLen += 1;
          _dom[index].classList.add("is-disabled");
        }
      }
    },

    /**
     * @description: 取消选择标签
     * @param {Map} tag
     * @return {void}
     */
    handleClose(tag) {
      //根据标签名获取元素
      var ele = document.getElementsByName(tag.value);
      //移除该元素禁用class
      if (ele.length > 0) {
        ele[0].classList.remove("is-disabled");
      }
      //更改标签状态
      this.changeTagState(tag, 0);
      //从动态标签集合中移除该标签,splice(刪除的位置，刪除个数)
      this.form.tags.splice(this.form.tags.indexOf(tag), 1);
      this.dynamicTagsLen -= 1;
    },

    /**
     * @description: 更改指定标签状态
     * @param {Map} tag
     * @param {Number} state
     * @return {void}
     */
    changeTagState(tag, state) {
      //遍历标签集合，根据tag改变状态值
      for (var i = 0; i < this.initTags.length; i++) {
        if (tag.value == this.initTags[i].value) {
          this.initTags[i].state = state;
          tag.state = state;
        }
      }
    },

    /**
     * @description: 跳转到文章管理页面
     * @return {void}
     */
    goArticleManager() {
      var toEnd = "true";
      this.$router.push({
        name: "管理文章"
      });
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
/* 文章标题及按钮组 */
.fotter {
  padding: 10px;
  position: sticky;
  top: 0;
  background-color: #f4f4f4;
  z-index: 1501;
}

.v-note-op button {
  font-size: 18px !important;
}
.v-note-op {
  position: sticky;
  top: 60px;
}
/* 按钮padding */
.publicBtnPadded {
  padding: 7px 10px;
}

span.el-radio-button__inner {
  font-size: 16px;
}
label.el-form-item__label {
  font-size: 18px;
  font-weight: 600;
}
/* 专栏按钮 */
.category-button {
  margin-top: 10px;
}
/* 文章标题 */
.article-title {
  width: 80%;
  margin-left: 10px;
}
</style>
