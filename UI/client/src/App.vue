<template>
	<el-config-provider :locale="locale" :size="size">
		<router-view />
	</el-config-provider>
	<van-nav-bar safe-area-inset-top />

	<!-- 开启底部安全区适配 -->
	<van-number-keyboard safe-area-inset-bottom />
</template>

<script setup lang="ts">
import { computed, nextTick, onMounted } from 'vue'
import { RouterView } from 'vue-router'
import { useTitle } from '@vueuse/core'
import { useI18n } from 'vue-i18n'
import { messages } from '@/i18n'
import { handleThemeStyle } from '@/utils/theme'
import { useAppStore } from '@/store/modules/app'
import 'vant/lib/index.css'
// 引入模块后自动生效
import '@vant/touch-emulator'

const appStore = useAppStore()
const { t } = useI18n()
const locale = computed(() => messages[appStore.language].el)
const size = computed(() => appStore.componentSize)

// 设置标题
useTitle(t('app.title'))

onMounted(() => {
	nextTick(() => {
		// 初始化主题样式
		handleThemeStyle(appStore.theme)
	})
})
</script>
