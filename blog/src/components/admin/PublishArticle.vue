<template>
    <div class="mavonEditor">
        <div class="fotter">
            <el-input
  type="text"
  placeholder="给文章起个名吧..."
  v-model="text"
  maxlength="30"
  show-word-limit
  style="width: 81%;margin-left: 10px;"
></el-input>
            <el-button type="success" round style="margin-left: 10px;">存为草稿</el-button>
            <el-button type="primary" round>发布文章</el-button>
        </div>
        <mavon-editor v-model="value" @save="save" placeholder="读书破万卷，下笔如有神..." style="height: 100%;"/>
    </div>
</template>
<script>
export default {
  name: "publishArticle",
  data() {
    return {
      value: "",
      text: "",
    };
  },
  methods: {
    save(markdown, html) {
      console.log(markdown);
      console.log(html);
    }
  }
};
</script>
<style>
.el-main {
  padding: 0;
}
.fotter{
    /* margin-left: 42.2%;
    margin-bottom:30px; */
    padding:10px;
}
.mavonEditor{
    /* background-color: #fff; */
}
</style>
