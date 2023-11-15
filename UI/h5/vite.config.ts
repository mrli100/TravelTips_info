import { defineConfig } from 'vite'
import { resolve } from 'path'
import vue from '@vitejs/plugin-vue'

//依赖导入elementui
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

//依赖导入vant
import { VantResolver } from '@vant/auto-import-resolver';

// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  resolve: {
    // 配置别名
    alias: {
      '@': resolve(__dirname, './src/*'),
    }
  },
  plugins: [vue(),
  AutoImport({
    resolvers: [ElementPlusResolver(), VantResolver()],
    imports: [
      'vue',
      'vue-router',
    ]
  }),

  Components({
    resolvers: [ElementPlusResolver(), VantResolver()],
    dts: true,
    types: []
  })],
  server: {
    host: '0.0.0.0',
    port: 3003, // 端口号
    open: false // 是否自动打开浏览器
  }
})
