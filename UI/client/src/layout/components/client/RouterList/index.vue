<template>
	<van-pull-refresh v-model="refreshing" @refresh="onRefresh" .class="refreshDiv">
		<van-list v-model:loading="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
			<van-cell v-for="item in list" :key="item" :title="item" />
		</van-list>
	</van-pull-refresh>
</template>
<script setup lang="ts">
import { ref } from 'vue'
import { showToast } from 'vant'

//查询列表
const list = ref([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)

const onClickLocation = () => {
	showToast('你选择了位置')
}
const onLoad = () => {
	console.log(refreshing.value)
	setTimeout(() => {
		if (refreshing.value) {
			list.value = []
			refreshing.value = false
		}

		for (let i = 0; i < 20; i++) {
			list.value.push(list.value.length + 1)
			showToast('加载' + (list.value.length + 1))
		}
		loading.value = false
		if (list.value.length >= 100) {
			finished.value = true
		}
	}, 1000)
}
const onRefresh = () => {
	// 清空列表数据
	finished.value = false

	// 重新加载数据
	// 将 loading 设置为 true，表示处于加载状态
	loading.value = true
	onLoad()
}
</script>
<style lang="less" scoped></style>
