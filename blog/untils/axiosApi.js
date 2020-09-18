import axios from "axios";
import { Message } from "element-ui";
import router from "@/router/index";

let base = " ";
export const getRequest = (url, params) => {
  return axios({
    method: "get",
    url: `${base}${url}`,
    data: params,
    transformResponse: [
      function(data) {
        data = JSON.parse(data);
        return data;
      }
    ],
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }
  });
};

export const postRequest = (url, params) => {
  return axios({
    method: "POST",
    url: `${base}${url}`,
    data: params,
    transformRequest: [
      function(data) {
        let ret = "";
        for (let it in data) {
          ret +=
            encodeURIComponent(it) + "=" + encodeURIComponent(data[it]) + "&";
        }
        return ret;
      }
    ],
    headers: {
      "Content-Type": "application/x-www-form-urlencoded"
    }
  });
};

axios.interceptors.response.use(
  response => {
    let { data } = response;
    return data;
  },
  error => {
    var code = error.response.status;
    if (code === 401) {
      Message.error("尚未登录或登录过期，请先登录！");

      router.beforeEach((to,from,next) => {
        next({
          path:'/doLogin',
          query:{
            redirect:to.fullPath
          }
        });
      })
      // console.log(router)
      // router.push({ 
      //   path: "/doLogin",
      //   query:{
      //     redirect:router.fullPath
      //   }
      //  });
    }
  }
);
