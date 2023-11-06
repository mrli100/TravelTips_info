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
	<!-- 列表和下拉刷新 -->
	<van-row>
		<van-col span="24">
			<van-pull-refresh v-model="refreshing" @refresh="onRefresh">
				<van-list offset="400" v-model:loading="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
					<van-cell v-for="item in list" :key="item" :title="item">ASDAS</van-cell>
				</van-list>
			</van-pull-refresh>
		</van-col>
	</van-row>
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
//查询列表
const list = ref([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)
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

const onLoad = () => {
	setTimeout(() => {
		if (refreshing.value) {
			list.value = []
			refreshing.value = false
		}

		for (let i = 0; i < 10; i++) {
			list.value.push(list.value.length + 1)
			showToast('加载' + list.value.length)
		}
		loading.value = false
		if (list.value.length >= 60) {
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
<style lang="less" scoped>
.van-swipe {
	top: 5px;
	height: 150px;
	text-align: center;

	img {
		max-height: 100%;
		max-width: 100%;
	}
}

.my-swipe .van-swipe-item {
	color: #fff;
	font-size: 20px;
	line-height: 150px;
	text-align: center;
	background-color: #39a9ed;
}

:deep(.van-list) {
	height: 30vh;
	overflow-y: scroll;
}
</style>
