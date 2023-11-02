import { resolve } from 'path'
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import { createSvgIconsPlugin } from 'vite-plugin-svg-icons'
import vueSetupExtend from 'vite-plugin-vue-setup-extend'
import Components from 'unplugin-vue-components/vite'
import { VantResolver } from 'unplugin-vue-components/resolvers'

// 参考：https://cn.vitejsproxy.dev/config/
export default defineConfig({
	base: './',
	resolve: {
		// 配置别名
		alias: {
			'@': resolve(__dirname, './src'),
			'vue-i18n': 'vue-i18n/dist/vue-i18n.cjs.js'
		}
	},
	plugins: [
		vue(),
		vueSetupExtend(),
		createSvgIconsPlugin({
			iconDirs: [resolve(__dirname, 'src/icons/svg')],
			symbolId: 'icon-[dir]-[name]'
		}),
		Components({
			dts: true,
			resolvers: [VantResolver()],
			types: []
		})
	],
	server: {
		host: '0.0.0.0',
		port: 3001, // 端口号
		open: false // 是否自动打开浏览器
	}
})
