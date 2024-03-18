import { createSSRApp } from 'vue'
import TnIcon from '@tuniao/tnui-vue3-uniapp/components/icon/src/icon.vue'
import TnNavbar from '@tuniao/tnui-vue3-uniapp/components/navbar/src/navbar.vue'
import TnButton from '@tuniao/tnui-vue3-uniapp/components/button/src/button.vue'
import App from './App.vue'
// 导入 pinia 实例
import { createPinia } from "pinia";

export function createApp() {
	const app = createSSRApp(App)

	app.component('TnIcon', TnIcon)
	app.component('TnNavbar', TnNavbar)
	app.component('TnButton', TnButton)

	// 使用 pinia
	app.use(createPinia())

	return {
		app,
	}
}