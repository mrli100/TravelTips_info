<template>
	<van-row>
		<van-col span="24">
			<van-tabs v-model:active="active" @click-tab="onClickTab">
				<van-tab title="攻略">
					<travelPage />
				</van-tab>
				<van-tab title="路线">
					<routerPage />
				</van-tab>
			</van-tabs>
		</van-col>
	</van-row>
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { showToast } from 'vant'
import tabbar from '@/view/tabbar/index.vue'
//底部菜单
//攻略页面
import travelPage from '@/view/travle/index.vue'
import routerPage from '@/view/router/index.vue'
showToast("aaaaaaaaaaaaaaaaa")
//变量定义
let active = ref(0)
const onClickTab = ({ title }) => {
	showToast(title)
}
</script>
<style lang="less" scoped></style>
