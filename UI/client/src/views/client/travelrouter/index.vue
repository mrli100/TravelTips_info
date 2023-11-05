<template>
	<!-- 搜索内容 -->
	<van-row>
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
	</van-row>
	<!-- 轮播图 -->
	<van-row>
		<van-col span="24">
			<van-swipe :autoplay="3000" lazy-render indicator-color="red">
				<van-swipe-item v-for="image in images" :key="image">
					<img :src="image" />
				</van-swipe-item>
			</van-swipe>
		</van-col>
	</van-row>
	<!-- 公共消息 -->
	<van-row>
		<van-col span="24">
			<van-notice-bar left-icon="volume-o" text="身体在流浪，灵魂在觉醒" />
		</van-col>
	</van-row>
	<!-- 选项卡 -->
	<van-row>
		<van-col span="24">
			<van-tabs v-model:active="active" sticky>
				<van-tab v-for="index in 10" :key="index" :title="'选项 ' + index" />
			</van-tabs>
		</van-col>
	</van-row>
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
	<!-- 底部导航 -->
	<van-row>
		<van-col span="24">
			<van-action-sheet v-model:show="areaShow" title="标题"><div class="content">内容</div></van-action-sheet>
		</van-col>
	</van-row>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { showToast } from 'vant'
import tabbar from '@/layout/components/client/tabbar/index.vue'
//变量定义
const value = ''
const images = ['https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg', 'https://fastly.jsdelivr.net/npm/@vant/assets/apple-2.jpeg']
let active = 0
//地区选择
let areaShow = false
//查询列表
const list = ref([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)

const onClickLocation = () => {
	showToast('你选择了位置')
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
