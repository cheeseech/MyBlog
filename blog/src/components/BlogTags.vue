<template>
  <div id="blogTags" v-show="initialArticles">
    <el-card class="box-card m-radius-small m-b-margin">
      <div slot="header" class="clearfix">
        <svg class="icon m-svg-size" aria-hidden="true" style="font-size: 26px">
          <use xlink:href="#icon-book-2"></use>
        </svg>
        <span
          ><strong style="font-size: 24px">全部标签</strong>
          <strong style="font-size: 24px; color: orange"
            >( {{ tagsDataLen }} )</strong
          ></span
        >

        <!-- 搜索框-->
        <el-input
          placeholder="请输入内容"
          style="float: right; width: 20%"
          v-model="input"
          class="input-with-select m-search-input m-search-btn"
        >
          <el-button slot="append"
            ><svg class="icon m-svg-size" aria-hidden="true">
              <use xlink:href="#icon-gosearch"></use></svg></el-button>
        </el-input>
      </div>
      <!--引入词云组件：传入id(不能重复！) 宽高以及数据,当拿到数据后才开始渲染-->
      <wordCloud
        v-if="tagsData"
        :wc_id="wordCloudId"
        :wc_height="200"
        :wc_width="1130"
        :wc_data="echartsData"
      ></wordCloud>
    </el-card>

    <blogArticle
      :tag_id="tagId"
      :initial_articles="initialArticles"
    ></blogArticle>
  </div>
</template>

<script>
import blogArticle from "./BlogArticle";
import wordCloud from "./WordCloud";
import axios from "axios";
import { getRequest } from "../../untils/axiosApi";

export default {
  name: "BlogTags",
  data() {
    return {
      tagsData: null,
      initialArticles: null,
      tagsDataLen: 0,
      tagId: "",
      input:"",
      wordCloudId: "wordCloud2",
      echartsData: [
        {
          name: "十九大精神",
          value: 15000,
        },
        {
          name: "两学一",
          value: 10081,
        },
        {
          name: "中华",
          value: 9386,
        },
        {
          name: "马克义",
          value: 7500,
        },
        {
          name: "民族复兴",
          value: 7500,
        },
        {
          name: "社会主制度",
          value: 6500,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国防皮书",
          value: 6500,
        },
        {
          name: "创新",
          value: 6000,
        },
        {
          name: "民革命",
          value: 4500,
        },
        {
          name: "文化国",
          value: 3800,
        },
        {
          name: "国权",
          value: 3000,
        },
        {
          name: "颠覆",
          value: 2500,
        },
        {
          name: "完整",
          value: 2300,
        },
        {
          name: "安全",
          value: 2000,
        },
        {
          name: "从治党",
          value: 1900,
        },
        {
          name: "现代化体系",
          value: 1800,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
        {
          name: "国动员",
          value: 1700,
        },
        {
          name: "信息争",
          value: 1600,
        },
        {
          name: "局部争",
          value: 1500,
        },
        {
          name: "教育",
          value: 1200,
        },
        {
          name: "职教育",
          value: 1100,
        },
        {
          name: "法",
          value: 900,
        },
        {
          name: "两制",
          value: 800,
        },
        {
          name: "社会主义思想",
          value: 700,
        },
      ],
    };
  },
  beforeRouteEnter(to, from, next) {
    //在路由组件跳转前获取数据
    axios.all([getRequest("/tags/counts/"), getRequest("/article/")]).then(
      axios.spread((tagsResponse, articleResponse) => {
        next((vm) => vm.setData(tagsResponse, articleResponse));
      })
    );
  },
  mounted() {
    /*
     * 在页面模版挂载后，判断BlogArticle是否传值过来(点击文章标签),
     * 成功则改变tagId即传值给BlogArticle生成文章列表*/
    if (this.$route.params.value) {
      this.tagId = this.$route.params.value;
    }
  },
  methods: {
    //数据处理
    setData(tagsResponse, articleResponse) {
      var _this = this;
      _this.tagsData = tagsResponse.data;
      _this.tagsDataLen = _this.tagsData.length;
      _this.initialArticles = articleResponse.data;
    },
    sendIdForArticle(value) {
      var _this = this;
      _this.tagId = value;
    },
  },
  components: {
    blogArticle: blogArticle,
    wordCloud: wordCloud,
  },
};
</script>
