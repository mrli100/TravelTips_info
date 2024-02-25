<template>
	<view>
		<view class="circle-card">
			<view class="circle-top df" @click="toCircle">
				<view>
					<view class="top-name df">
						<image src="/static/img/qz.png"></image>
						<text>{{ item.name }}</text>
					</view>
					<view class="circle-num">
						<text>{{ item.dynamic_count }} 篇内容</text>
						<text>{{ item.user_count }} 人在讨论</text>
					</view>
				</view>
				<lazy-load :src="item.avatar" width="120rpx" height="120rpx" border-radius="60rpx"></lazy-load>
				<view class="circle-new" v-if="item.is_new"></view>
			</view>
			<view class="circle-center" :data-id="v.id" @click="toDynamic" v-for="(v,i) in item.dynamic" :key="i">
				<view class="circle-left" :style="{width: v.dynamic_img?'calc(100% - 180rpx)':'100%'}">
					<view class="circle-user df" :data-id="v.user_id" @click.stop="toUser">
						<lazy-load :src="v.user.avatar" width="48rpx" height="48rpx" border-radius="48rpx"></lazy-load>
						<view class="name ohto">{{ v.user.name }}</view>
						<text v-if="v.user.career">@{{ v.user.career }}</text>
					</view>
					<view class="circle-content ohto2">{{ v.content }}</view>
					<view class="circle-num">
						<text>评论 {{ v.dynamic_comment }}</text>
						<text>赞 {{ v.dynamic_like }}</text>
					</view>
				</view>
				<block v-if="v.dynamic_img">
					<lazy-load :src="v.img" width="160rpx" height="160rpx" border-radius="8rpx"></lazy-load>
					<view class="img-count df">
						<image src="/static/img/i.png"></image>
						<text>{{ v.dynamic_img }}</text>
					</view>
				</block>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			item: {
				type: Object,
				require: true
			},
			idx: {
				type: Number,
				default: 0
			},
		},
		data() {
			return {}
		},
		methods: {
			toCircle() {
				uni.navigateTo({
					url: '/pages/circle/details?id=' + this.item.id
				})
			},
			toDynamic(e) {
				uni.navigateTo({
					url: '/pages/dynamic/details?id=' + e.currentTarget.dataset.id
				})
			},
			toUser(e) {
				uni.navigateTo({
					url: '/pages/user/details?id=' + e.currentTarget.dataset.id
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.circle-card {
		width: 100%;
		margin-bottom: 60rpx;
	}

	.circle-card .circle-top {
		width: 100%;
		justify-content: space-between;
		position: relative;
	}

	.circle-top .top-name image {
		width: 32rpx;
		height: 32rpx;
	}

	.circle-top .top-name text {
		margin-left: 10rpx;
		font-size: 32rpx;
		font-weight: bold;
	}

	.circle-num text {
		margin-right: 20rpx;
		font-size: 20rpx;
		color: #999;
	}

	.circle-top .circle-new {
		position: absolute;
		right: 0;
		bottom: 0;
		width: 25rpx;
		height: 25rpx;
		border-radius: 50%;
		background: #FFA676;
		border: 4rpx solid #fff;
	}

	.circle-card .circle-center {
		margin-top: 30rpx;
		width: calc(100% - 40rpx);
		padding: 20rpx;
		border-radius: 8rpx;
		background: #f8f8f8;
		display: flex;
		justify-content: space-between;
		position: relative;
	}

	.circle-center .img-count {
		position: absolute;
		top: 120rpx;
		right: 40rpx;
		justify-content: center;
		width: 60rpx;
		height: 40rpx;
		color: #fff;
		font-size: 20rpx;
		font-weight: bold;
		background: rgba(0, 0, 0, .5);
		border-radius: 8rpx;
	}

	.circle-center .img-count image {
		margin-right: 6rpx;
		width: 24rpx;
		height: 24rpx;
	}

	.circle-center .circle-left {
		width: calc(100% - 180rpx);
	}

	.circle-user .name {
		margin: 0 12rpx;
		max-width: 300rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.circle-user text {
		color: #999;
		font-size: 24rpx;
	}

	.circle-content {
		margin-top: 20rpx;
		width: 100%;
		color: #333;
		font-size: 26rpx;
		font-weight: 500;
		white-space: pre-line;
	}
</style>