import Vue from "vue";
import Router from "vue-router";
const home = () =>import(/* webpackChunkName: "blog_home" */ "@/components/Home");
const blogIndex = () =>import(/* webpackChunkName: "blog_home" */ "@/components/BlogIndex");
const blogTags = () =>import(/* webpackChunkName: "blog_tags" */ "@/components/BlogTags");
const blogArchives = () =>import(/* webpackChunkName: "blog_archives" */ "@/components/BlogArchives");
const blogCategory = () =>import(/* webpackChunkName: "blog_category" */ "@/components/BlogCategory");
const blogAbout = () =>import(/* webpackChunkName: "blog_about" */ "@/components/BlogAbout");
const articleInfo = () =>import(/* webpackChunkName: "blog_article" */ "@/components/ArticleInfo");
const adminHome = () =>import(/* webpackChunkName: "admin_home" */ "@/components/admin/Admin");
const login = () =>import(/* webpackChunkName: "login" */ "@/components/Login");
const userManager = () =>import(/* webpackChunkName: "user_manager" */ "@/components/admin/UserManager");
const tagsManager = () =>import(/* webpackChunkName: "tags_manager" */ "@/components/admin/TagsManager");
const categoriesManager = () =>import(/* webpackChunkName: "category_manager" */ "@/components/admin/CategoryManager");

Vue.use(Router);

export default new Router({
  // mode: 'history',
  routes: [
    {
      path: "/",
      component: home,
      children: [
        {
          path: "",
          component: blogIndex
        },
        {
          path: "index",
          name: "首页",
          component: blogIndex,
          meta: {
            progress: {
              func: [
                {call: 'color', modifier: 'temp', argument: '#ffb000'},
                {call: 'fail', modifier: 'temp', argument: '#6e0000'},
                {call: 'location', modifier: 'temp', argument: 'top'},
                {call: 'transition', modifier: 'temp', argument: {speed: '1.5s', opacity: '0.6s', termination: 400}}
              ]
            }
          }
        },
        {
          path: "article",
          name: "文章",
          component: articleInfo,
          meta: {
            progress: {
              func: [
                {call: 'color', modifier: 'temp', argument: '#ffb000'},
                {call: 'fail', modifier: 'temp', argument: '#6e0000'},
                {call: 'location', modifier: 'temp', argument: 'top'},
                {call: 'transition', modifier: 'temp', argument: {speed: '1.5s', opacity: '0.6s', termination: 400}}
              ]
            }
          }
        },
        {
          path: "category",
          name: "专栏",
          component: blogCategory,
          meta: {
            progress: {
              func: [
                {call: 'color', modifier: 'temp', argument: '#ffb000'},
                {call: 'fail', modifier: 'temp', argument: '#6e0000'},
                {call: 'location', modifier: 'temp', argument: 'top'},
                {call: 'transition', modifier: 'temp', argument: {speed: '1.5s', opacity: '0.6s', termination: 400}}
              ]
            }
          }
        },
        {
          path: "tags",
          name: "标签",
          component: blogTags,
          meta: {
            progress: {
              func: [
                {call: 'color', modifier: 'temp', argument: '#ffb000'},
                {call: 'fail', modifier: 'temp', argument: '#6e0000'},
                {call: 'location', modifier: 'temp', argument: 'top'},
                {call: 'transition', modifier: 'temp', argument: {speed: '1.5s', opacity: '0.6s', termination: 400}}
              ]
            }
          }
        },
        {
          path: "archives",
          name: "归档",
          component: blogArchives,
          meta: {
            progress: {
              func: [
                {call: 'color', modifier: 'temp', argument: '#ffb000'},
                {call: 'fail', modifier: 'temp', argument: '#6e0000'},
                {call: 'location', modifier: 'temp', argument: 'top'},
                {call: 'transition', modifier: 'temp', argument: {speed: '1.5s', opacity: '0.6s', termination: 400}}
              ]
            }
          }
        },
        {
          path: "about",
          name: "关于我",
          component: blogAbout,
          meta: {
            progress: {
              func: [
                {call: 'color', modifier: 'temp', argument: '#ffb000'},
                {call: 'fail', modifier: 'temp', argument: '#6e0000'},
                {call: 'location', modifier: 'temp', argument: 'top'},
                {call: 'transition', modifier: 'temp', argument: {speed: '1.5s', opacity: '0.6s', termination: 400}}
              ]
            }
          }
        }
      ]
    },
    {
      path: "/admin",
      name: "管理",
      component: adminHome,
      children: [
        {
          path: "user",
          name: "用户管理",
          component:userManager
        },
        {
          path: "tags",
          name: "标签管理",
          component:tagsManager
        },
        {
          path:"category",
          name: "专栏管理",
          component:categoriesManager
        }
      ]
    },
    {
      path: "/doLogin",
      name: "登录",
      component: login
    }
  ]
});
