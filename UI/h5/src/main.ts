import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import { createPinia } from "pinia";
import router from "./router";
import { createRouterScroller } from "vue-router-better-scroller";
//瀑布流
import VueGridWaterfall from "@fcli/vue-grid-waterfall";
import "@vant/touch-emulator";
//导入vant样式
import "vant/es/toast/style";
import "vant/es/dialog/style";
import "vant/es/notify/style";
import "vant/es/image-preview/style";

const app = createApp(App);
app.use(createPinia());
app.use(router);
app.use(VueGridWaterfall);
// 增强了 Vue Router v4 的滚动行为
app.use(
  createRouterScroller({
    selectors: {
      window: true,
      body: true,
      ".scrollable": true,
    },
  })
);
app.mount("#app");
