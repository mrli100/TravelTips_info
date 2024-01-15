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
						<var-cell :key="item" v-for="item in list">
							<var-cell title="这是单元格" description="描述">
								<template #extra>
									<van-icon name="edit" size="1.5rem" @click="editClick" />
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
import { showToast } from 'vant';
import tabbar from "@/views/tabbar/index.vue";
const onClickLeft = () => history.back();
//**静态变量 */
//查询列表
const list = ref([])
const loading = ref(false)
const finished = ref(false)
const refreshing = ref(false)

//返回组件
const backComponent = ref(null)

//** 初始化方法 */
onMounted(() => {
	//获取窗口高度
	let windowsHeight = window.innerHeight
	let lisRowtHeight = windowsHeight - backComponent.value.offsetHeight
	//最后的50是tabbar
	lisRowtHeight = lisRowtHeight - 50
	document.getElementsByClassName('van-list')[0].style.height = lisRowtHeight + 'px'
})

const editClick = () => {
	console.log('编辑')
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