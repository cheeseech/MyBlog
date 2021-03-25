import Vue from "vue";
import Router from "vue-router";
const home = () =>
  import(/* webpackChunkName: "blog_home" */ "@/views/blog/home/index");
const blogIndex = () =>
  import(/* webpackChunkName: "blog_home" */ "@/views/blog/blogIndex/index");
const blogTags = () =>
  import(/* webpackChunkName: "blog_tags" */ "@/views/blog/BlogTags");
const blogArchives = () =>
  import(/* webpackChunkName: "blog_archives" */ "@/views/blog/BlogArchives");
const blogCategory = () =>
  import(/* webpackChunkName: "blog_category" */ "@/views/blog/BlogCategory");
const blogAbout = () =>
  import(/* webpackChunkName: "blog_about" */ "@/views/blog/BlogAbout");
const articleInfo = () =>
  import(/* webpackChunkName: "blog_article" */ "@/views/blog/BlogArticle");

const adminHome = () =>
  import(/* webpackChunkName: "admin_home" */ "@/views/admin/home/index");
const welcome = () =>
  import(/* webpackChunkName:"admin_welcome"*/ "@/views/admin/AdminWelcome");
const adminUser = () =>
  import(/* webpackChunkName: "admin_user" */ "@/views/admin/adminUser");
const adminTag = () =>
  import(/* webpackChunkName: "admin_tag" */ "@/views/admin/adminTags");
const adminCategory = () =>
  import(
    /* webpackChunkName: "admin_category" */ "@/views/admin/AdminCategory"
  );
const login = () =>
  import(/* webpackChunkName: "login" */ "@/components/Login");

const publish = () =>
  import(
    /* webpackChunkName:"publish_article"*/ "@/views/admin/adminArticle/PublishArticle"
  );
const adminArticle = () =>
  import(
    /* webpackChunkName:"article_manager"*/ "@/views/admin/adminArticle/AdminArticle"
  );
const recycle = () =>
  import(/* webpackChunkName:"recycle"*/ "@/views/admin/adminArticle/Recycle");

const adminData = () =>
  import(/* webpackChunkName:"data_manager"*/ "@/views/admin/adminData/index");

// 解决ElementUI导航栏中的vue-router在3.0版本以上重复点菜单报错问题
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(Router);

export default new Router({
//   mode: 'history',
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
          component: blogIndex
        },
        {
          path: "article",
          name: "文章",
          component: articleInfo
        },
        {
          path: "category",
          name: "专栏",
          component: blogCategory
        },
        {
          path: "tags",
          name: "标签",
          component: blogTags
        },
        {
          path: "archives",
          name: "归档",
          component: blogArchives
        },
        {
          path: "about",
          name: "关于我",
          component: blogAbout
        },
        {
          path: "articleInfo/:articleId",
          name: "文章详情",
          component: articleInfo
        }
      ]
    },
    {
      path: "/admin",
      component: adminHome,
      children: [
        {
          path: "",
          component: welcome
        },
        {
          path: "welcome",
          name: "欢迎页",
          component: welcome
        },
        {
          path: "user",
          name: "用户管理",
          component: adminUser
        },
        {
          path: "tags",
          name: "标签管理",
          component: adminTag
        },
        {
          path: "category",
          name: "专栏管理",
          component: adminCategory
        },
        {
          path: "article",
          name: "管理文章",
          component: adminArticle
        },
        {
          path: "publishArticle",
          name: "发布文章",
          component: publish
        },
        {
          path: "recycle",
          name: "回收站",
          component: recycle
        },
        {
          path: "adminData",
          name: "数据管理",
          component: adminData
        }
      ]
    },
    {
      path: "/doLogin",
      name: "登录",
      component: login
    }
  ],
  // 每次做路由切换时，始终回到最顶部
  scrollBehavior(to, from, savedPosition) {
    return { x: 0, y: 0 };
  }
});


