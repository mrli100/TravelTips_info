<template>
	<div>
		<van-card v-for="(item, index) in list" :key="index" class="Van_card"
			thumb="https://img1.baidu.com/it/u=3370980328,2295504291&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500">
			<!-- 标题 -->
			<template #title>
				<div class="title">大西北旅行大环线</div>
				<div class="title">准备大环线旅游鞋</div>
			</template>
			<!-- 时间 -->
			<template #price-top>
				<div class="desc">2023-12-01 至 2023-12-07 </div>
			</template>
			<template #num>
				<!-- 标签 -->
				<div class="tags">
					<van-tag type="primary">预约</van-tag>
					<van-tag type="success">报名</van-tag>
					<van-tag type="success">私信</van-tag>
				</div>
				<!-- 人数 -->
				<div>
					<span class="dot"></span>
					{{ index + 1 }}人
				</div>
			</template>
			<template #tag>
				<!-- <div v-if="index + 1 <= 3" class="tag" :class="index == 0 ? 'one' : index == 1 ? 'two' : 'three'">咖选 No.{{ index +1 }}</div> -->
				<!-- <div v-if="index + 1 <= 3" class="rightTag">{{ index + 1 }}</div> -->
			</template>
		</van-card>
	</div>
</template>

<script setup lang="ts">
const props = defineProps({
	list: Array
})

const processPrice = price => {
	console.log(price.toString().replace('x', ''))
	return price
}
</script>
<style lang="less" scoped>
.Van_card {
	.title {
		font-size: 15px;
	}

	.desc {
		color: #908888;
	}

	.dot {
		display: inline-block;
		width: 6px;
		height: 6px;
		background: #10d610;
		border-radius: 100%;
		vertical-align: middle;
	}

	.tags {
		span {
			margin-right: 5px;
		}
	}

	.tag {
		padding: 2px 5px;
		font-size: 11px;
		color: #fff;
		border-radius: 20% 6% 6% 5%;
		position: absolute;
		top: -5px;
		left: -2px;
	}

	.rightTag {
		position: absolute;
		top: -7px;
		right: -8px;
		background: #ff5a57;
		width: 20px;
		height: 20px;
		border-radius: 100%;
		text-align: center;
		line-height: 20px;
		color: #fff;
	}

	.one {
		background: #ff5a57;
	}

	.two {
		background: #ff9800;
	}

	.three {
		background: #ffd502;
	}

	:deep(.van-card__num) {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}

	:deep(.van-card__thumb) {
		flex: 3;
	}

	:deep(.van-card__content) {
		flex: 6;
		margin-left: 10px;
	}

	:deep(.van-card__tag) {
		position: static !important;
	}
}
</style>
