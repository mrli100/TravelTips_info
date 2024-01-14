<template>
	<van-row>
		<van-col span="24">
			<van-dropdown-menu>
				<van-dropdown-item v-model="value1" :options="option1" />
			</van-dropdown-menu>
		</van-col>
	</van-row>
	<!-- 搜索内容 -->
	<van-row>
		<var-card v-for="(item, i) in routerListData" :key="i" :title="item.planName" :description="item.planDesc">
			<template #extra>
				<var-space>
					<var-button text type="warning">查看</var-button>
					<var-button text type="warning">收藏</var-button>
				</var-space>
			</template>
			<template #image>
				<var-swipe navigation class="swipe-example">
					<var-swipe-item v-for="(itemImg) in JSON.parse(item.bgImage)" :key="itemImg">
						<img class="swipe-example-image" :src="itemImg.trim()">
					</var-swipe-item>
				</var-swipe>
			</template>
		</var-card>
	</van-row>
	<!-- 底部导航 -->
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
//** 添加API接口 */
import { usePlanMainApiPage } from '@/api/travel/planMain'
import tabbar from "@/views/tabbar/index.vue";
// 变量定义
const option1 = [
	{ text: '最新排序', value: 0 },
	{ text: '点赞排序', value: 1 },
	{ text: '查看排序', value: 2 }
]
const value1 = ref(0)

const routerListData = ref([])

//** 初始化方法  */
onMounted(() => {
	const parms = {
		page: 1,
		limit: 10
	}
	usePlanMainApiPage(parms).then((res) => {
		if (res.code == 0) {
			routerListData.value = res.data.list
		}
	})
})
</script>
<style lang="less" scoped>
.swipe-example {
	height: 160px;
}

.swipe-example-image {
	width: 100%;
	height: 100%;
	object-fit: cover;
	pointer-events: none;
}

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
