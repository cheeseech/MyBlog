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
  },

  /**
   * @description: 进行删除操作时显示的消息提示
   * @param {*} successMessage
   * @param {*} errorMessage
   * @param {*} response
   * @return {void}
   */
  deleteMessage(successMessage, errorMessage, response) {
    if (response.status == 204) {
      // 删除成功消息提示
      Message({
        type: "success",
        dangerouslyUseHTMLString: true,
        message: "<strong>" + successMessage + "</strong>"
      });
    } else if (response.status == 500) {
      // 未知错误消息提示
      Message({
        type: "error",
        dangerouslyUseHTMLString: true,
        message: "<strong>未知错误！请联系管理员。</strong>"
      });
    } else {
      // 删除失败消息提示
      Message({
        type: "error",
        dangerouslyUseHTMLString: true,
        message: "<strong>" + errorMessage + "</strong>"
      });
    }
  },

  /**
   * @description: 进行插入和更新时显示的消息提示
   * @param {*} successMessage
   * @param {*} errorMessage
   * @param {*} response
   * @return {void}
   */
  insertUpdateMessage(successMessage, errorMessage, response) {
    if (response.status == 201) {
      // 操作成功消息提示
      Message({
        type: "success",
        dangerouslyUseHTMLString: true,
        message: "<strong>" + successMessage + "</strong>"
      });
    } else if (response.status == 500) {
      // 未知错误消息提示
      Message({
        type: "error",
        dangerouslyUseHTMLString: true,
        message: "<strong>未知错误！请联系管理员。</strong>"
      });
    } else {
      // 操作失败消息提示
      Message({
        type: "error",
        dangerouslyUseHTMLString: true,
        message: "<strong>" + errorMessage + "</strong>"
      });
    }
  }
};
