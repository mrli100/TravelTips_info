import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import App from './App.vue'
import { createPinia } from 'pinia'
import { directive } from './utils/directive'
import { router } from './router'
import { i18n } from './i18n'
import 'virtual:svg-icons-register'
import MakuFormDesign from 'maku-form-design'
import 'maku-form-design/lib/style.css'

import '@/icons/iconfont/iconfont'
import 'element-plus/dist/index.css'
import '@/styles/index.scss'

import 'xe-utils'
import VXETable from 'vxe-table'
import 'vxe-table/lib/style.css'

import FastTableColumn from '@/components/fast-table-column'
import FastRadioGroup from '@/components/fast-radio-group'
import FastSelect from '@/components/fast-select'
import FastUser from '@/components/fast-user'
import SvgIcon from '@/components/svg-icon'

VXETable.setup({
	zIndex: 3000,
	select: {
		transfer: true
	}
})

// 2. 引入组件样式
import 'vant/lib/index.css'

// Vant 桌面端适配
import '@vant/touch-emulator'

/* --------------------------------
Vant 中有个别组件是以函数的形式提供的，
包括 Toast，Dialog，Notify 和 ImagePreview 组件。
在使用函数组件时，unplugin-vue-components
无法自动引入对应的样式，因此需要手动引入样式。
------------------------------------- */
import 'vant/es/toast/style'
import 'vant/es/dialog/style'
import 'vant/es/notify/style'
import 'vant/es/image-preview/style'

const app = createApp(App)
app.use(createPinia())

// 表单设计器
app.use(MakuFormDesign)

// 注册 自定义指令
directive(app)
app.use(router)
app.use(i18n)
app.use(FastTableColumn)
app.use(FastRadioGroup)
app.use(FastSelect)
app.use(FastUser)
app.use(SvgIcon)
app.use(ElementPlus)
app.use(VXETable)

// 增强了 Vue Router v4 的滚动行为

app.mount('#app')
