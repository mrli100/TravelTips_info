<template>
	<div class="container">
		<van-nav-bar fixed @click-left="onClickLocation">
			<template #left>
				<van-icon name="location-o" size="18">北京</van-icon>
			</template>
			<template #title>
				<van-search v-model="value" placeholder="请输入搜索关键词" />
			</template>
		</van-nav-bar>
		<div class="swipeDiv">
			<van-swipe :autoplay="3000" lazy-render indicator-color="red">
				<van-swipe-item v-for="image in images" :key="image">
					<img :src="image" />
				</van-swipe-item>
			</van-swipe>
			<!-- 			<van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
				<van-swipe-item>1</van-swipe-item>
				<van-swipe-item>2</van-swipe-item>
				<van-swipe-item>3</van-swipe-item>
				<van-swipe-item>4</van-swipe-item>
			</van-swipe> -->
		</div>
		<VanCellGroup title="一个集成最新技术栈、完整干净的移动端模板" inset>
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
import { showToast } from 'vant'
import tabbar from '@/layout/components/client/tabbar/index.vue'

const onClickLocation = () => {
	showToast('你选择了位置')
}
//变量定义
const value = ''
const images = ['https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg', 'https://fastly.jsdelivr.net/npm/@vant/assets/apple-2.jpeg']

// 登录类型
const loginType = ref('account')
</script>
<style lang="less" scoped>
.container {
	width: 100%;
	height: 100%;
}

.van-swipe {
	top: 5px;
	height: 200px;
	// background-color: #39a9ed;
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

//搜索框设置高度
.van-search {
	height: 46px;
}
</style>
