import { defineConfig } from "vite";
import { resolve } from "path";
import vue from "@vitejs/plugin-vue";

import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { VarletUIResolver } from "unplugin-vue-components/resolvers";

//依赖导入vant
import { VantResolver } from "@vant/auto-import-resolver";

// https://vitejs.dev/config/
export default defineConfig({
  base: "./",
  resolve: {
    // 配置别名
    alias: {
      "@": resolve(__dirname, "src"),
    },
  },
  plugins: [
    vue(),
    AutoImport({
      resolvers: [VantResolver(), VarletUIResolver({ autoImport: true })],
      imports: ["vue", "vue-router"],
    }),

    Components({
      resolvers: [VantResolver(), VarletUIResolver()],
    }),
  ],
  server: {
    host: "0.0.0.0",
    port: 3003, // 端口号
    open: false, // 是否自动打开浏览器
  },
});
