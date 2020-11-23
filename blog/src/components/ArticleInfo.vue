<template>
  <div id="articleInfo">
    <el-row :gutter="30">
      <el-col :span="18">
        <el-card class="m-radius-big m-b-margin card-padded" shadow="hover">
          <!--文章首图-->
          <el-image :src="imgSrc" fit="fill" class="img-transform"></el-image>

          <!--文章主要内容-->
          <div style="padding: 40px;">
            <el-button
              type="warning"
              round
              style="display: inline-block;float: right;margin-right: 13px;"
              class="btnPadded"
              >原创</el-button
            >
            <!--专栏-->
            <el-button
              type="primary"
              round
              class="btnPadded m-r-margin-small"
              style="float: right"
              >Spring</el-button
            >
            <h2 class="m-article">失败要趁早</h2>

            <el-menu
              class="el-menu-demo"
              mode="horizontal"
              text-color="#303133"
            >
              <el-menu-item style="padding-left: 0px">
                <el-avatar
                  :size="40"
                  :src="imgSrc"
                  style="vertical-align:middle;"
                ></el-avatar>
                <span style="padding-left: 4px;"><strong>徐茂华</strong></span>
              </el-menu-item>

              <el-menu-item class="m-lf-padded-mini">
                <span style="color:#606266;font-size: 13px;"
                  >六月 16, 2020</span
                >
              </el-menu-item>

              <el-menu-item style="float: right;padding-left: 0px;">
                <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
                  <use xlink:href="#icon-ai-eye"></use>
                </svg>
                <span class="m-article-icon-text">20</span>
              </el-menu-item>

              <el-menu-item class="m-lf-padded-mini" style="float: right">
                <svg class="icon m-svg-size m-article-icon" aria-hidden="true">
                  <use xlink:href="#icon-icon4"></use>
                </svg>
                <span class="m-article-icon-text">18</span>
              </el-menu-item>
            </el-menu>

            <div class="m-text-lined m-t-margin-small">
              <p>
                上次日本飞国内的飞机上，我旁边刚好坐了一位女士，大概是30多岁的样子。一坐上飞机，那个女士就拿出了一堆打印的资料在那里看。
                看了没过两分钟，然后她就突然跟我搭讪，问:
                你知道下飞机之后那边的气温如何？我说，你可以查一下手机上的天气预报或者等会空乘会告诉你。
              </p>
              <p>
                然后我们就开始聊了起来，本的工作经历，学会计的，当过文秘，也和别人合伙开过外贸公司倒腾纸尿裤。
              </p>
              <p>
                聊了一段时间之后，她突然问我可不可以去欧洲留学，然后，想知道欧洲可不可以学香氛的临床操作治疗。
                <br />我一开始楞了一下，因为根本就不知道香氛是什么东西。
              </p>
              <p>
                然后她就开始给我科普起来，香氛治疗是什么。她说现在很多疾病医学都没办法解决，
                而且医学有很多的副作用。香氛这个就可以提高弥补医学的短板，或者是甚至解决一些医学没办法解决的事情。然后又说，现代医学也不好用，
                那么多病也治不了，就连吃药都有副作用！香氛可以从人们的灵，身，心三方面去解救大家，她觉得这个东西非常好。然后她又告诉我，
                她现在在北京和上海都有的培训学校，有了好几百名学员，开了好几个分校，还有有钱的大佬要投资她。
              </p>
              <p>
                听完她这一顿瞎扯，我就知道又碰到混江湖的
                骗子了。于是我尴尬又不失礼的回答道，我在欧洲也不是学医学的，如果要临床的话，应该是专业的医学，你在国内没办法操作的事情，在国外应该也很难操作。
              </p>
              <p>
                没想到这个姐姐对我热情不减，不断给我“科普”她的香氛。一开始的时候，她不断强调她不想赚钱，只是想要帮助别人，但是一会儿她又说她只负责做技术，
                是技术类的人才，困于没人给她做市场。虽然我不太理解她，一个做了十几年文秘的会计出身的人，怎么会成为一个医疗培训技术类的人才的。
                心想现在江湖也太好混了，就这种玩法还能够骗到人？
              </p>
              <p>
                直到后来她说到一点，我才知道他她这个玩法是怎么回事。她的这个香氛受众主要是女性，
                尤其是一些家庭事业生活并不幸福如意的女性。那些没有工作的，经济不独立的，生活不太幸福的女性，通过加入这种香氛培训，期望获得一技之长，
                然后自己又成为这种香氛医疗的受众。
              </p>
              <p>
                等她说到这里，我就知道为什么会有大佬愿意给她投钱，因为她准确的抓住了一个群体的人的需求。<b>
                  重要的不是香氛真的能不能治病，重要的是，她通过这样的方式，把希望改变人生的女人们聚集起来，给提供的一种，类似于幻想的希望和安全感，
                  这是价值观的贩卖。</b
                >
              </p>
              <p>
                其实要按照我以前的认知和性格，我肯定会高冷地不理。但是，这次我并没有，还耐心地跟她进行了一些讨论。
              </p>
              <p>
                因为这几年我明白了，要进行价值观的批判总是容易的，但是却是站着说话不腰疼，不是所有人都有我这样的幸运能获得高质量的教育的。每个人都有
                自己的挣扎和痛苦，她察觉到了一群人的痛苦，并且尝试以一种无害的方式提供希望，不比我高冷的指责要好？当然，她过大的夸大香氛的作用，
                确实存在欺骗嫌疑，这一点还是要指出。
              </p>
              <p>
                逻辑思维也是相似原理，我想逻辑思维很准确的抓住了大众对知识贫乏带来的安全感缺失的最根本的需求。
              </p>
              <p>
                它提供了一种至少看起来可行的一个途径，订阅逻辑思维，就能够获得知识，追赶甚至引领时代，收获更好的未来。
              </p>
              <p>虽然，这一点上来说，有 点推广营销的意思。</p>
              <p>
                但是，读书真的是一个非常奢侈的资源，从上一次火热的那篇文章，中国农村读高中的学生不过63%，再看看我们的一本
                的升学率，就知道，对于我们的这个社会来说，高效的教育资源真的是非常稀缺的。
              </p>
              <p>
                国家每年投入巨大的资源在教育上面都不能解决的问题，逻
                辑思维能够解决到什么程度呢？
              </p>
              <p>它也只能解决部分有悟性的成年人，一些认知上的更新。</p>
              <p>
                但是它就像这个香氛治疗一样，确实给一些人
                提供了一些希望。而有些人只需要希望就可以了，或者说安慰好自己就可以了。
              </p>
              <p><b>最怕的是，连希望都没有，已经放弃挣扎。</b></p>
              <p>
                <b>
                  题主的朋友是不是真的有问题，不好说。但是最重要的是，他有这种不服输的上进心，那么也许他在未来的一段时间里会彷徨，会继续挣扎，会穷困潦倒。
                </b>
              </p>
              <p>
                <b
                  >但是，这个世界总有那么多的机会，万一有一个机会被他抓住了呢？</b
                ><br /><br /><b
                  >况且，人对这个世界的认知本事就是螺旋式前进的，
                  即使是科研也有曲折试探索的时候，何况是个人的人生？</b
                ><br /><br /><b
                  >清醒的幸运者已经找到了门路和方向，可以自救，但是否定他人的挣扎，却是最
                  不应该去做的事情。</b
                >
              </p>
              <p><b>------</b></p>
              <p><b>学习不能单纯地指望逻辑思维。</b></p>
              <p>
                <b
                  >问题点还是在于，有太多的人，求
                  诉无门，如果你不能提供解决之法，先不要轻易批评。</b
                ><br /><br /><b>——END——</b>
              </p>
            </div>

            <div>
              <el-button type="primary" round class="btnPadded" effect="dark"
                >Html</el-button
              >
              <el-button type="primary" round class="btnPadded" effect="dark"
                >Vue</el-button
              >
              <el-button type="primary" round class="btnPadded" effect="dark"
                >Java</el-button
              >
            </div>
            <el-divider></el-divider>
          </div>

          <!-- <comment :comments="comments"></comment> -->
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card class="m-radius-big">
          【1x00】获取数据 get_51job_data.py 【01x01】构建请求地址
          【01x02】获取总页数 【01x03】提取详情页 URL 【01x04】提取职位信息
          【01x05】保存数据到 MongoDB 【2x00】数据可视化 draw_bar_chart.py
          【02x01】数据初处理 【02x02】数据清洗
          【02x03】绘制经验与平均薪资关系图 【02x04】绘制学历与平均薪资关系图
          【3x00】数据截图 【4x00】完整代码
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
const comment = () =>
  import(/* webpackChunkName "blog_article" */ "@/components/Comment");
export default {
  name: "ArticleInfo",
  data() {
    return {
      imgSrc: require("./../assets/images/content.jpg"),
      comments: [
        {
          id: "comment0001", //主键id
          date: "2018-07-05 08:30", //评论时间
          ownerId: "talents100020", //文章的id
          fromId: "errhefe232213", //评论者id
          fromName: "犀利的评论家", //评论者昵称
          fromAvatar:
            "http://ww4.sinaimg.cn/bmiddle/006DLFVFgy1ft0j2pddjuj30v90uvagf.jpg", //评论者头像
          likeNum: 3, //点赞人数
          content: "非常靠谱的程序员", //评论内容
          reply: [
            //回复，或子评论
            {
              id: "34523244545", //主键id
              commentId: "comment0001", //父评论id，即父亲的id
              fromId: "observer223432", //评论者id
              fromName: "夕阳红", //评论者昵称
              fromAvatar:
                "https://wx4.sinaimg.cn/mw690/69e273f8gy1ft1541dmb7j215o0qv7wh.jpg", //评论者头像
              toId: "errhefe232213", //被评论者id
              toName: "犀利的评论家", //被评论者昵称
              toAvatar:
                "http://ww4.sinaimg.cn/bmiddle/006DLFVFgy1ft0j2pddjuj30v90uvagf.jpg", //被评论者头像
              content: "赞同，很靠谱，水平很高", //评论内容
              date: "2018-07-05 08:35" //评论时间
            },
            {
              id: "34523244545",
              commentId: "comment0001",
              fromId: "observer567422",
              fromName: "清晨一缕阳光",
              fromAvatar:
                "http://imgsrc.baidu.com/imgad/pic/item/c2fdfc039245d688fcba1b80aec27d1ed21b245d.jpg",
              toId: "observer223432",
              toName: "夕阳红",
              toAvatar:
                "https://wx4.sinaimg.cn/mw690/69e273f8gy1ft1541dmb7j215o0qv7wh.jpg",
              content: "大神一个！",
              date: "2018-07-05 08:50"
            }
          ]
        },
        {
          id: "comment0002",
          date: "2018-07-05 08:30",
          ownerId: "talents100020",
          fromId: "errhefe232213",
          fromName: "毒蛇郭德纲",
          fromAvatar:
            "http://ww1.sinaimg.cn/bmiddle/006DLFVFgy1ft0j2q2p8pj30v90uzmzz.jpg",
          likeNum: 0,
          content: "从没见过这么优秀的人",
          reply: []
        }
      ]
    };
  },
  created() {
    //模拟评论数据
  },
  components: {
    comment: comment
  }
};
</script>

<style scoped>
.el-card >>> .el-card:hover {
  color: #333;
}
.m-r-margin-small {
  margin-right: 0.4em;
}
/*文章标题*/
.m-article {
  margin-top: 0;
  margin-bottom: 1;
  margin-left: 43%;
  display: inline-block;
}
</style>
