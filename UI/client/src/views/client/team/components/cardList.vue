<template>
	<div>
		<van-card
			v-for="(item, index) in list"
			:key="index"
			class="Van_card"
			thumb="https://img1.baidu.com/it/u=3370980328,2295504291&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500"
		>
			<!-- 标题 -->
			<template #title>
				<div class="title">咖自助餐厅 A区</div>
			</template>
			<!-- 时间 -->
			<template #price-top>
				<div class="desc">07:30 至 9:30 开放</div>
			</template>
			<template #num>
				<!-- 标签 -->
				<div class="tags">
					<van-tag type="primary">预约</van-tag>
					<van-tag type="success">送餐</van-tag>
					<van-tag type="danger">现场</van-tag>
				</div>
				<!-- 人数 -->
				<div>
					<span class="dot"></span>
					{{ index + 1 }}人
				</div>
			</template>
			<template #tag>
				<div v-if="index + 1 <= 3" class="tag" :class="index == 0 ? 'one' : index == 1 ? 'two' : 'three'">咖选 No.{{ index + 1 }}</div>
				<div v-if="index + 1 <= 3" class="rightTag">{{ index + 1 }}</div>
			</template>
		</van-card>
	</div>
</template>

<script setup lang="ts">
import item from 'element-plus/es/components/space/src/item'
import { ref, onMounted, reactive } from 'vue'

const props = defineProps({
	list: Array
})

const processPrice = price => {
	// return 1
	// console.log(price.toString())
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
	::v-deep .van-card__num {
		width: 100%;
		display: flex;
		justify-content: space-between;
	}
	::v-deep .van-card__thumb {
		flex: 3;
	}
	::v-deep .van-card__content {
		flex: 6;
		margin-left: 10px;
	}
	::v-deep .van-card__tag {
		position: static !important;
	}
}
</style>
