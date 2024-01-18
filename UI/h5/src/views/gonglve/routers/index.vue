<template>
	<van-row>
		<van-col span="24">
			<div ref="component1">
				<van-dropdown-menu>
					<van-dropdown-item v-model="value1" :options="option1" />
				</van-dropdown-menu>
			</div>
		</van-col>
	</van-row>
	<!-- 卡片列表 -->
	<van-row>
		<van-pull-refresh v-model="refreshing" @refresh="onRefresh">
			<van-list offset="400" v-model:loading="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
				<var-card v-for="(item, i) in routerListData" :key="i" :title="item.planName" :description="item.planDesc">
					<template #extra>
						<var-space>
							<var-button text type="warning">查看</var-button>
							<var-button text type="warning">收藏</var-button>
						</var-space>
					</template>
					<template #image>
						<var-swipe :loop="false" class="swipe-example" @click="showImagePreview(JSON.parse(item.bgImage))">
							<var-swipe-item v-for="(itemImg) in JSON.parse(item.bgImage)" :key="itemImg">
								<img class="swipe-example-image" :src="itemImg.trim()">
							</var-swipe-item>
							<template #indicator="{ index, length }">
								<div class="swipe-example-indicators">
									<div class="swipe-example-indicator">
										{{ (index + 1) + '/' + length }}
									</div>
								</div>
							</template>
						</var-swipe>
					</template>
				</var-card>
			</van-list>
		</van-pull-refresh>
	</van-row>
	<!-- 底部导航 -->
	<van-row>
		<div ref="component2">
			<van-col span="24">
				<tabbar>底部聊天</tabbar>
			</van-col>
		</div>
	</van-row>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { showImagePreview } from 'vant';
//** 添加API接口 */
import { usePlanMainApiQuery } from '@/api/travel/planMain'
// @ts-ignore
import tabbar from "@/views/tabbar/index.vue";
// 变量定义
const option1 = [
	{ text: '最新排序', value: 0 },
	{ text: '点赞排序', value: 1 },
	{ text: '查看排序', value: 2 }
]
const value1 = ref(0)

//** 初始化参数 */
const component1 = ref(null)
const component2 = ref(null)


//** 初始化方法  */
onMounted(() => {
	//获取窗口高度
	let windowsHeight = window.innerHeight - 45
	let lisRowtHeight = windowsHeight - component1.value.offsetHeight
	lisRowtHeight = windowsHeight - component2.value.offsetHeight
	//最后的50是tabbar
	lisRowtHeight = lisRowtHeight - 100
	// @ts-ignore
	document.getElementsByClassName('van-list')[0].style.height = lisRowtHeight + 'px'
})

//** 无线下拉列表 */
const routerListData = ref([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)
//** 查询参数 */
const parms = ref({
	page: 1,
	limit: 10
})
const onLoad = () => {
	setTimeout(() => {
		if (refreshing.value) {
			routerListData.value = []
			refreshing.value = false
		}

		usePlanMainApiQuery(parms.value).then((res: any) => {
			if (res.code == 0) {
				console.log()
				if (parms.value.page > res.data.pages) {
					finished.value = true
				} else {
					for (let item of res.data.list) {
						routerListData.value.push(item);
					}
					parms.value.page = parms.value.page += 1
				}
			}
			// console.log(res, routerListData.value)
		})
		loading.value = false
	}, 1000)
}
const onRefresh = () => {
	// 清空列表数据
	finished.value = false
	// 重新加载数据
	loading.value = true
	parms.value.page = 1
	onLoad()
}

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

//图片轮播
.swipe-example-indicators {
	position: absolute;
	display: flex;
	bottom: 10px;
	left: 95%;
	background-color: rgb(184, 186, 187);
	transform: translateX(-50%);
}
</style>
