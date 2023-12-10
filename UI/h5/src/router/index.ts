// https://router.vuejs.org/zh/
import { createRouter, createWebHistory } from "vue-router";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import { useUserStore } from "@/store/modules/user.ts";

// 导入路由组件
NProgress.configure({ showSpinner: true, parent: "#app" });

// 定义路由，每个路由都需要映射到一个组件
const routes = [
  {
    path: "/",
    name: "main",
    component: () => import("@/views/home/index.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/login/index.vue"),
  },
  {
    path: "/team",
    name: "team",
    component: () => import("@/views/team/index.vue"),
  },
  {
    path: "/gonglve", //攻略
    name: "gonglve",
    component: () => import("@/views/gonglve/index.vue"),
  },
  {
    path: "/gonglve/routers/add", //路线添加
    name: "routers_add",
    component: () => import("@/views/gonglve/routers/add/index.vue"),
  },
  {
    path: "/user",
    name: "user",
    component: () => import("@/views/user/index.vue"),
  },
  {
    path: "/user/detail",
    name: "user_detail",
    component: () => import("@/views/user/detail/index.vue"),
  },
];

// 创建路由实例并传递 `routes` 配置
const router = createRouter({
  history: createWebHistory(),
  routes,
});

//需要登录列表
const useLoginList = ["/user", "/gonglve/routers/add", "/user/detail"];

router.beforeEach(async (_to, _from, next) => {
  // start progress bar
  NProgress.start();
  const userStore = useUserStore();
  // token存在的情况
  if (userStore.token) {
    if (!userStore.user.id) {
      try {
        await userStore.getUserInfoAction();
        await userStore.getAuthorityListAction();
      } catch (error) {
        // 请求异常，则跳转到登录页
        userStore?.setToken("");
        next("/login");
        return Promise.reject(error);
      }
    }
    next();
  } else {
    // 没有token的情况下，可以进入白名单
    if (useLoginList.indexOf(_to.path) > -1) {
      next("/login");
    } else {
      next();
    }
  }
  console.log(userStore);
});

router.afterEach(() => {
  // finish progress bar
  NProgress.done();
});

// 导出路由实例，并在 `main.ts` 挂载
export default router;
