import { Message } from "element-ui";
export default {
  /**
   * @description: 回到顶部方法
   * @return void
   */
  backTop: function() {
    let scrollTop = 0;
    let timer = setInterval(() => {
      let ispeed = Math.floor(-scrollTop / 5);
      document.documentElement.scrollTop = document.body.scrollTop =
        scrollTop + ispeed;
      if (scrollTop === 0) {
        clearInterval(timer);
      }
    }, 16);
  },

  /**
     * @description: 后台选择日期数据时显示消息提示
     * @param {Map} response
     * @return void
     */
   showMessage(response) {
    if (response.status == 200) {
      // 查找成功消息提示
      Message({
        type: "success",
        dangerouslyUseHTMLString: true,
        message: "<strong>选择成功！</strong>"
      });
    } else {
      // 查找失败消息提示
      Message({
        type: "error",
        dangerouslyUseHTMLString: true,
        message: "<strong>选择失败！请重新选择。</strong>"
      });
    }
  }
};
