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
	<van-floating-bubble axis="xy" v-model:offset="floating_offset" magnetic="x" icon="plus" @click="onClick" />
	<!-- 选择发布内容 -->
	<van-action-sheet v-model:show="addshow" :actions="actions" @select="onSelect" />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { showToast } from 'vant'

import tabbar from "@/views/tabbar/index.vue";
//攻略页面
import travelPage from '@/views/gonglve/travle/index.vue'
import routerPage from '@/views/gonglve/routers/index.vue'
import router from '@/router';
//变量定义
let active = ref(0)
//气泡定位
const floating_offset = ref({
	x: window.innerWidth - 50,
	y: (window.innerHeight - 110)
})
const onClickTab = ({ title }) => {
	showToast(title)
}

const onClick = () => {
	addshow.value = true;
}

//** 选择发布内容 */
const addshow = ref(false);
const actions = [
	{ name: '发布攻略' },
	{ name: '发布线路' },
];
const onSelect = (item) => {
	addshow.value = false;
	if (item.name == '发布攻略') {

	} else if (item.name == '发布线路') {
		router.push({ path: '/gonglve/routers/add' })
	}

	showToast(item.name);
};
</script>
<style lang="less" scoped></style>
