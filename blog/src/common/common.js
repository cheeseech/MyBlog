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
  }
};
