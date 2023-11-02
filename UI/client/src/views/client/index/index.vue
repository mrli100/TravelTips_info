<template>
	<div class="container">
		<VanCellGroup title="一个集成最新技术栈、完整干净的移动端模板" inset>
			<VanCell center title="🌗 暗黑模式">
				<template #right-icon>
					<VanSwitch size="18px" />
				</template>
			</VanCell>

			<VanCell title="💿 mock 指南" to="mock" is-link />

			<VanCell title="📊 charts 演示" to="charts" is-link />

			<VanCell center>
				<template #title>
					<span class="custom-title">🎨 欢迎补充</span>
					<VanTag type="primary">PR</VanTag>
				</template>
			</VanCell>
			<tabbar>底部聊天</tabbar>
		</VanCellGroup>
	</div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import tabbar from '@/layout/components/client/tabbar/index.vue'

// 登录类型
const loginType = ref('account')
</script>
