<template>
	<div>
		<var-card title="本草纲目"
			subtitle="如果华佗再世,崇洋都被医治,外邦来学汉字,激发我民族意识。马钱子、决明子、苍耳子，还有莲子；黄药子、苦豆子、川楝子，我要面子。用我的方式，改写一部历史。没什么别的事，跟着我念几个字。山药当归枸杞 GO，山药 当归 枸杞 GO，看我抓一把中药，服下一帖骄傲~"
			src="https://travel-tips-1251586900.cos.ap-nanjing.myqcloud.com/user%2Favatar%2F202305111683775022387140.jpg"
			description="报名人数5/5 人均费用：3000元/人" layout="row" v-for="(item, index) in list" :key="index" class="Van_card" outline>
			<template #extra>
				<var-button type="warning" size="mini">收藏</var-button>
				<var-button type="primary" size="mini">加入</var-button>
			</template>
		</var-card>
	</div>
</template>

<script setup lang="ts">
const props = defineProps({
	list: Array
})
</script>
<style lang="less" scoped>
.Van_card {
	margin-bottom: 5px;

	button {
		margin-left: 5px;
	}
}
</style>
