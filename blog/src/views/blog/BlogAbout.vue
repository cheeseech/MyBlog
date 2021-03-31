<!--
 * @FileDescription: 博客关于我组件
 * @Author: 徐茂华
 * @Date: 2020-08-02 15:05:48
 * @LastEditors: 徐茂华
 * @LastEditTime: 2021-02-10 17:05:39
 * @FilePath: \src\components\Comment.vue
-->
<template>
  <div id="about">
    <el-row :gutter="20">
      <el-col :span="12">
        <!-- 技能卡片 -->
        <el-card class="m-radius-small">
          <!-- 头部文字 -->
          <span class="card-head-text">技能</span>
          <el-divider direction="vertical"></el-divider>
          <span class="card-head-text">Skill</span>
          <!-- 主要内容 -->
          <div class="card-content-text">
            <ul>
              <li>前端相关：Html/CSS/Js/Vue</li>
              <li>
                Java相关：Java/Spring/SpringMVC/Mybatis/<br />&emsp;&emsp;&emsp;&emsp;&emsp;SpringBoot/Security
              </li>
              <li>数据库相关：MySQL/Redis</li>
              <li>项目管理：Maven</li>
              <li>版本控制：Git</li>
              <li>其他：Linux</li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <!-- 简介卡片 -->
        <el-card class="m-radius-small">
          <!-- 头部文字  -->
          <span class="card-head-text">简介</span>
          <el-divider direction="vertical"></el-divider>
          <span class="card-head-text">Introduction</span>
          <!-- 主要内容 -->
          <div class="card-content-text">
            <ul>
              <li>学历：宁德某本科，计算机科学与技术专业学生。</li>
              <li>现况：虽菜但学</li>
              <li>目标：优秀Java开发工程师</li>
              <li>博客：芝士才是力量！</li>
              <li>兴趣：编程、跑步、健身、游戏</li>
              <li>其他：一切经历都是探险</li>
            </ul>
            <br />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <!-- 关于本站卡片 -->
        <el-card class="m-radius-small">
          <!-- 头部文字 -->
          <span class="card-head-text">关于本站</span>
          <el-divider direction="vertical"></el-divider>
          <span class="card-head-text">About this site</span>
          <!-- 主要内容 -->
          <div class="card-content-text">
            <ul>
              <li>本站使用Springboot+Security+Vue+ElementUI开发</li>
              <li>
                本站部署在阿里云,使用Nginx代理,数据库采用MySQL,文件存储使用七牛云存储
              </li>
            </ul>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12">
        <!-- 联系我卡片 -->
        <el-card class="m-radius-small">
          <!-- 头部文字 -->
          <span class="card-head-text">联系我</span>
          <el-divider direction="vertical"></el-divider>
          <span class="card-head-text">Contact me</span>
          <!-- 主要内容 -->
          <div class="card-contact-img">
            <!-- QQ -->
            <a
              href="http://wpa.qq.com/msgrd?v=3&uin=1355125277&site=qq&menu=yes"
              target="_blank"
            >
              <el-avatar
                :size="50"
                :src="qqImg"
                style="margin-right: 10%;"
              ></el-avatar>
            </a>
            <!-- Github -->
            <a href="https://github.com/cheeseech" target="_blank">
              <el-avatar
                :size="50"
                :src="gitImg"
                style="margin-right: 5%;"
              ></el-avatar>
            </a>
            <!-- CSDN -->
            <a href="https://blog.csdn.net/a23352321" target="_blank">
              <el-avatar
                :size="50"
                :src="cImg"
                style="margin-left: 5%;"
              ></el-avatar>
            </a>
            <!-- 邮箱 -->
            <a href="mailto:xumaohua98@163.com">
              <el-avatar
                :size="50"
                :src="emailImg"
                style="margin-left: 10%;"
              ></el-avatar>
            </a>
          </div>
          <br /><br />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "BlogAbout",
  data() {
    return {
      qqImg: require("@/assets/images/qq.jpg"), // qq图片
      cImg: require("@/assets/images/c.png"), // CSDN图片
      gitImg: require("@/assets/images/github.jpg"), // Github图片
      emailImg: require("@/assets/images/email.jpg") // 邮箱图片
    };
  }
};
</script>

<style scoped>
/* 列表圆点 */
li {
  list-style-type: disc;
}
/* 卡片文字居中 */
.el-card {
  text-align: center;
}
/* 卡片头部文字 */
.card-head-text {
  font-weight: bold;
  font-size: 25px;
}
/* 卡片主要内容文字 */
.card-content-text {
  text-align: left;
  line-height: 30px;
  font-size: 18px;
}
/* 联系我卡片内容 */
.card-contact-img {
  text-align: center;
  line-height: 30px;
  font-size: 18px;
  margin-top: 25px;
}
</style>
