<template>
	<div>
		<van-card v-for="item in list" :key="item" desc="描述信息" title="咖自助餐厅 A区" thumb="https://fastly.jsdelivr.net/npm/@vant/assets/ipad.jpeg">
			<template #tags>
				<van-tag type="primary">预约</van-tag>
				<van-tag type="success">送餐</van-tag>
				<van-tag type="danger">现场</van-tag>
			</template>
			<template #footer>
				<van-button size="mini">按钮</van-button>
			</template>
		</van-card>
	</div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
const props = defineProps({
	list: Array
})
</script>
<style lang="less" scoped></style>
