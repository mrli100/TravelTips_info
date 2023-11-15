// https://router.vuejs.org/zh/
import { createRouter, createWebHistory } from 'vue-router'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

// 导入路由组件
NProgress.configure({ showSpinner: true, parent: '#app' })

// 定义路由，每个路由都需要映射到一个组件
const routes = [
	{
		path: '/',
		name: 'main',
		component: () => import('../views/index.vue'),
	},
	{
		path: '/tabbar',
		name: 'tabbar',
		component: () => import('@/views/tabbar/index.vue'),
	},
]

// 创建路由实例并传递 `routes` 配置
const router = createRouter({
	history: createWebHistory(),
	routes,
})

router.beforeEach((_to, _from, next) => {
	// start progress bar
	NProgress.start()
	next()
})

router.afterEach(() => {
	// finish progress bar
	NProgress.done()
})

// 导出路由实例，并在 `main.ts` 挂载
export default router
