<template>
	<van-row>
		<van-col span="24">
			<div ref="backComponent">
				<van-nav-bar title="草稿箱" left-text="返回" left-arrow @click-left="onClickLeft" />
			</div>
		</van-col>
	</van-row>
	<!-- 列表和下拉刷新 -->
	<div ref="listComponent">
		<van-row>
			<van-col span="24">
				<van-pull-refresh v-model="refreshing" @refresh="onRefresh">
					<van-list offset="400" v-model:loading="loading" :finished="finished" finished-text="没有更多了" @load="onLoad">
						<var-cell :key="item" v-for="item in routerListData">
							<var-cell :title="item.planName" :description="item.planDesc">
								<template #extra>
									<van-icon name="edit" size="1.5rem" @click="editClick(item)" />
								</template>
								<template #icon>
									<var-badge type="info" value="路线" class="label" />
								</template>
							</var-cell>
						</var-cell>
					</van-list>
				</van-pull-refresh>
			</van-col>
		</van-row>
	</div>
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
</template>
<script  setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
//** 添加API接口 */
import { usePlanMainApiQuery } from '@/api/travel/planMain'
// @ts-ignore
import tabbar from "@/views/tabbar/index.vue";
const onClickLeft = () => history.back();
//**静态变量 */
//返回组件
const backComponent = ref(null)
const router = useRouter()

//** 初始化方法 */
onMounted(() => {
	//获取窗口高度
	let windowsHeight = window.innerHeight
	let lisRowtHeight = windowsHeight - backComponent.value.offsetHeight
	//最后的50是tabbar
	lisRowtHeight = lisRowtHeight - 50
	// @ts-ignore
	document.getElementsByClassName('van-list')[0].style.height = lisRowtHeight + 'px'
})

const editClick = (item) => {
	router.push({ path: '/gonglve/routers/detail/edit', query: { id: item.id } })
}

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
	onLoad()
}
</script>
<style lang="less" scoped>
:deep(.label) {
	margin-right: 5px;
	margin-left: -10px;
}

:deep(.van-list) {
	height: 100px;
	overflow-y: scroll;
}
</style>