import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import { createRouterScroller } from 'vue-router-better-scroller'


const app = createApp(App)
app.use(router)
// 增强了 Vue Router v4 的滚动行为
app.use(createRouterScroller({
  selectors: {
    'window': true,
    'body': true,
    '.scrollable': true,
  },
}))
app.mount('#app')
