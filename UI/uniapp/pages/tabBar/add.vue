<template>
	<view class="container">
		<!-- Swiper Box -->
		<view class="swiper-box" :data-url="'dynamic/details?id='+info.dynamic_id" @click="navigateToFun">
			<lazy-load :src="info.img" width="100%" height="100vh"></lazy-load>
			<view class="swiper-tips df" :style="{top: statusBarHeight+88+'px'}">
				<view class="wave"></view>
				<view class="bubble">
					<view class="user df">
						<image :src="info.user_avatar"></image>
						<view class="ohto">{{ info.user_name }}</view>
					</view>
					<view class="content ohto2">{{ info.content }}</view>
				</view>
			</view>
		</view>
		<!-- Add Box -->
		<view class="add-box">
			<view v-if="config.app.snstczs.is_open" class="add-tc df">
				<view class="tc-l">
					<view>房屋出售</view>
				</view>
				<view class="tc-l">
					<view>房屋出租</view>
				</view>
				<view class="tc-l">
					<view>商铺租售/转让</view>
				</view>
				<view class="tc-l">
					<view>发招聘</view>
				</view>
			</view>
			<view class="add-dt df">
				<view class="add-item df" data-url="dynamic/add" @click.stop="navigateToFun">
					<image :src="insetUrl+'12.png'" />
					<view class="tit1">发动态</view>
					<view class="tit2">分享灵感和日常，记录美好生活</view>
				</view>
				<view class="add-line"></view>
				<view class="add-item df" data-url="circle/add" @click.stop="navigateCircleToFun">
					<image :src="insetUrl+'13.png'" />
					<view class="tit1">建圈子</view>
					<view class="tit2">创建自己的圈子，找到志同道合的朋友们</view>
				</view>
				<view class="angle-mark"></view>
			</view>
		</view>
	</view>
</template>

<script>
	const app = getApp();
	const api = require('@/config/api');
	const util = require('@/utils/util');
	export default {
		components: {},
		data() {
			return {
				user_info:{},
				config:{},
				statusBarHeight: app.globalData.statusBarHeight,
				insetUrl: api.insetUrl,
				info: {
					user: {
						name: '...'
					},
					content: '...'
				}
			}
		},
		async onLoad() {
			this.user_info = uni.getStorageSync('user_info');
			this.config = uni.getStorageSync('config');
			util.showShareMenu();
			await this.$onLaunched;
			this.randDynamic();
		},
		onShow() {
			util.getMessageCount();
		},
		methods: {
			randDynamic() {
				let that = this;
				util.request(api.randDynamicUrl).then(function(res) {
					that.info = res.data;
				});
			},
			navigateToFun(e) {
				
				let url = e.currentTarget.dataset.url;
				util.navigateTo({
					url: '/pages/' + url
				})
			},
			navigateCircleToFun(e) {
				//console.log(this.config);
				if(this.config.app?.usercircle?.is_open){
					let url = e.currentTarget.dataset.url;
					util.navigateTo({
						url: '/pages/' + url
					})
				}else{
					uni.showToast({
					  title: '功能未启用',
					  icon: 'error'
					})
					return;
				}
			},

		},
		onShareAppMessage: function() {},
		onShareTimeline(res) {
			return {
				title: app.globalData.shareTitle
			}
		},
	}
</script>

<style>
	@import url("/static/css/tabbar/add.css");
	.add-dt{
		margin-bottom: 30rpx;
	}
	.add-tc{
		border-bottom: 3rpx dashed #c9c9c991;
		margin: 30rpx;
		flex-wrap: wrap;
		padding-bottom: 20rpx;
		justify-content: space-around;
		align-items: stretch;
	}
	.tc-l{
		margin: 20rpx 0;
		font-size: 24rpx;
		padding: 20rpx;
		border-radius: 10rpx;
		background-color: #e7e7e791;
	}
</style>