<template>
	<van-row>
		<van-col span="24">
			<van-dropdown-menu>
				<van-dropdown-item v-model="value1" :options="option1" />
			</van-dropdown-menu>
		</van-col>
	</van-row>
	<!-- 搜索内容 -->
	<!-- <van-row>
		<van-col span="24">
			<van-nav-bar fixed @click-left="onClickLocation">
				<template #left>
					<van-icon name="location-o" size="18">北京</van-icon>
				</template>
				<template #title>
					<van-search shape="round" v-model="value" placeholder="请输入搜索关键词" />
				</template>
			</van-nav-bar>
		</van-col>
	</van-row> -->
	<!-- 底部导航 -->
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
	<!-- 地区选择 -->
	<van-action-sheet v-model:show="areaShow" title="请选择城市">
		<van-tree-select v-model:active-id="activeId" v-model:main-active-index="activeIndex" :items="items" />
	</van-action-sheet>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { showToast } from 'vant'
import tabbar from '@/layout/components/client/tabbar/index.vue'
// 变量定义
const option1 = [
	{ text: '最新排序', value: 0 },
	{ text: '点赞排序', value: 1 },
	{ text: '查看排序', value: 2 }
]
const value1 = ref(0)
//地区选择
let areaShow = ref(false)
//地区选择代码
const activeId = ref(1)
const activeIndex = ref(0)
const items = [
	{
		text: '浙江',
		children: [
			{ text: '杭州', id: 1 },
			{ text: '温州', id: 2 },
			{ text: '宁波', id: 3, disabled: true }
		]
	},
	{
		text: '江苏',
		children: [
			{ text: '南京', id: 4 },
			{ text: '无锡', id: 5 },
			{ text: '徐州', id: 6 }
		]
	},
	{ text: '福建', disabled: true }
]

const onClickLocation = () => {
	if (areaShow.value == false) {
		areaShow.value = true
	} else {
		areaShow.value = false
	}
}
</script>
<style lang="less" scoped>
//瀑布流样式
.card {
	background-color: red;
}

//强制修改滚动列表
:deep(.van-list) {
	height: 30vh;
	overflow-y: scroll;
}
</style>
