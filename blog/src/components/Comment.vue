<!--评论模块-->
<template>
  <div id="Comment">
    <div
      class="m-comment"
      v-for="(item,index) in comments"
      :key="index"
      style="padding-left: 40px;"
    >
      <!--用户头像、昵称及评论时间-->
      <div class="m-userInfo">
        <el-avatar size="35" :src="item.fromAvatar"></el-avatar>
        <div class="m-right">
          <div class="m-name">{{item.fromName}}</div>
          <div class="m-date">{{item.date}}</div>
        </div>
      </div>

      <!--评论内容-->
      <div class="m-content">{{item.content}}</div>

      <!--赞、回复-->
      <div class="m-control">
        <!--点赞-->
        <span class="m-like" :class="{active: item.isLike}" @click="likeClick(item)">
          <span class="like-num">{{item.likeNum > 0 ? item.likeNum + '人赞' : '赞'}}</span>
        </span>

        <!--回复-->
        <span class="comment-reply" @click="showCommentInput(item)">
          <span>回复</span>
        </span>
      </div>

      <!--回复/子评论-->
      <div class="m-reply">
        <!--子评论-->
        <div class="m-item" v-for="(reply,index) in item.reply" :key="index">
          <!--评论内容-->
          <div class="m-reply-content">
            <span class="m-from-name">{{reply.fromName}}</span>
            <span>:</span>
            <span class="m-to-name">@{{reply.toName}}</span>
            <span>{{reply.content}}</span>
          </div>

          <!--评论底部-->
          <div class="m-reply-bottom">
            <span>{{reply.date}}</span>
            <span class="m-reply-text" @click="showCommentInput(item, reply)">
              <span>回复</span>
            </span>
          </div>
        </div>

        <!--新评论-->
        <div class="m-new-replay" v-if="item.reply.length > 0" @click="showCommentInput(item)">
          <span>添加新评论</span>
        </div>

        <transition name="fade">
          <div class="input-wrapper" v-if="showItemId === item.id">
            <el-input
              class="gray-bg-input"
              v-model="inputComment"
              type="textarea"
              :rows="3"
              autofocus
              placeholder="写下你的评论"
            ></el-input>
            <div class="btn-control">
              <el-button type="info" round @click="cancel">取 消</el-button>
              <el-button type="primary" round @click="commitComment">确 定</el-button>
            </div>
          </div>
        </transition>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Comment",
  props: ["comments"],
  data() {
    return {
      inputComment: "",
      showItemId: "",
    };
  },
  created() {
    console.log(this.comments);
  },
  methods: {
    /**
     * 点赞
     */
    likeClick(item) {
      if (item.isLike === null) {
        //为点过赞设置点赞，人数加一
        Vue.$set(item, "isLike", true);
        item.likeNum++;
      } else {
        //已点过赞，人数相应增加或减少，属性设为相反
        if (item.isLike) {
          item.likeNum--;
        } else {
          item.likeNum++;
        }
        item.isLike = !item.isLike;
      }
    },

    /**
     * 点击取消按钮
     */
    cancel() {
      this.showItemId = "";
    },

    /**
     * 提交评论
     */
    commitComment() {
      console.log(this.inputComment);
    },

    /**
     * 点击评论按钮显示输入框
     * item: 当前大评论
     * reply: 当前回复的评论
     */
    showCommentInput(item, reply) {
      if (reply) {
        this.inputComment = "@" + reply.fromName + " ";
      } else {
        this.inputComment = "";
      }
      this.showItemId = item.id;
    },
  },
};
</script>

<style>
.m-comment {
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-bottom: 1px solid #e4e7ed;
}
.m-userInfo {
  display: flex;
  align-items: center;
}
.m-right {
  display: flex;
  flex-direction: column;
  margin-left: 10px;
}
.m-name {
  font-size: 16px;
  color: #303133;
  margin-bottom: 5px;
  font-weight: 500;
}
.m-date {
  font-size: 12px;
  color: #909399;
}
.m-content {
  font-size: 16px;
  color: #303133;
  line-height: 20px;
  padding: 10px 0;
}
.m-control {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #909399;
}
.m-like {
  display: flex;
  align-items: center;
  margin-right: 20px;
  cursor: pointer;
}
.m-like:hover,
.m-like:active {
  color: #409eff;
}
.comment-reply {
  display: flex;
  align-items: center;
  cursor: pointer;
}
.comment-reply:hover {
  color: #333;
}
.m-reply {
  margin: 10px 0;
  border-left: 2px solid #dcdfe6;
}
.m-item {
  margin: 0 10px;
  padding: 10px 0;
  border-bottom: 1px dashed #e4e7ed;
}
.m-reply-content {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #303133;
}
.m-from-name {
  color: #409eff;
}
.m-to-name {
  color: #409eff;
  margin-left: 5px;
  margin-right: 5px;
}
.m-reply-bottom {
  display: flex;
  align-items: center;
  margin-top: 6px;
  font-size: 12px;
  color: #909399;
}
.m-reply-text {
  display: flex;
  align-items: center;
  margin-left: 10px;
  cursor: pointer;
}
.m-reply-text:hover {
  color: #333;
}
.m-new-replay {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #909399;
  padding: 10px;
  cursor: pointer;
}
.m-new-replay:hover {
  color: #303133;
}

.fade-enter-active,
fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.input-wrapper {
  padding: 10px;
}
.btn-control {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding-top: 10px;
}
</style>
