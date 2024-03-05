<template>
	<view class="container">
		<!-- Nav Box -->
		<view class="nav-box" :style="{paddingTop: statusBarHeight+'px'}">
			<view class="nav-item df">
				<view class="title-label df" @click="typeClick" :data-i="i" v-for="(item,i) in typearr" :key="i">
					<text
						:style="{color:typeidx==i?'#000':'#999',fontSize:typeidx==i && scrollTop<88 ?'36rpx':'26rpx'}">
						{{item}}
					</text>
					<view v-if="typeidx==i" class="active"></view>
				</view>
			</view>
		</view>
		<!-- Content Box -->
		<view class="content-box" :style="{paddingTop: statusBarHeight+44+'px'}">
			<view v-if="config.lnk_jf_qd_value>0 && !is_clockin" class="qiandao">
				<image src="/static/img/qd.svg"/>
				每日打卡签到，领{{config.lnk_jf_qd_value}}积分
				<button data-url="center/integral" @click="navigateToFun">立即打卡</button>
			</view>
			<view class="empty-box" v-if="isEmpty">
				<image v-if="typeidx==0" :src="insetUrl+'5.png'" mode="aspectFill" />
				<image v-else :src="insetUrl+'4.png'" mode="aspectFill" />
				<view v-if="typeidx==0" class="text">没有关注的好朋友..</view>
				<view v-else class="text df">
					<view>没有内容，</view>
					<view data-url="dynamic/add" @click="navigateToFun" style="color: #FFA676;">去发一个</view>
				</view>
			</view>
			<block v-else v-for="(item,idx) in list" :key="item.id">
				<!-- #ifdef MP-WEIXIN -->
				<view style="padding: 0 30rpx;" v-if="item.ad_type==2">
					<ad :unit-id="item.ad_value"></ad>
				</view>
				<!-- #endif -->

				<dynamicBox :item="item" :idx="idx" @likeback="likeclick"></dynamicBox>
				<view style="border-bottom: 1px solid #efefef;margin: 0 30rpx 20rpx 30rpx;"></view>
			</block>
		</view>
		<uni-load-more v-if="loadStatus != 'no-more'" :status="loadStatus"></uni-load-more>
		<view v-else class="no-more">没有更多了️</view>
	</view>
</template>

<script>
	const app = getApp();
	const api = require('@/config/api');
	const util = require('@/utils/util');
	import dynamicBox from '@/components/dynamic/box.vue';
	export default {
		components: {
			dynamicBox
		},
		data() {
			return {
				statusBarHeight: app.globalData.statusBarHeight,
				insetUrl: api.insetUrl,
				scrollTop: 0,
				typearr: ['关注', '推荐'],
				typeidx: 1,
				list: [],
				page: 1,
				isEmpty: false,
				isFun: true,
				loadStatus: 'more',
				config:[],
				is_clockin: true,
				
				
			}
		},
		onPullDownRefresh() {
			this.list = [];
			this.page = 1;
			this.recommendDynamic();
			uni.stopPullDownRefresh();
		},
		async onLoad() {
			util.showShareMenu();
			await this.$onLaunched;
			this.recommendDynamic();
		},
		onShow() {
			this.config = uni.getStorageSync('config');
			let userInfo = uni.getStorageSync('user_info');
			if (userInfo != '' && userInfo != null && typeof(userInfo) != "undefined") {
				this.is_clockin = userInfo.is_clockin;
				this.getMessageCount();
			}

			// if (uni.getStorageSync('user_info') && uni.getStorageSync('user_info').city != '未知') {
			// 	this.typearr[2] = uni.getStorageSync('user_info').city;
			// } else if (uni.getStorageSync('config').lnk_city) {
			// 	this.typearr[2] = uni.getStorageSync('config').lnk_city;
			// }
		},
		methods: {
			recommendDynamic() {
				let that = this;
				that.loadStatus = 'loading';
				that.isEmpty = false;
				util.request(api.recommendDynamicUrl, {
					type: that.typeidx,
					page: that.page,
					//city: this.typearr[2]
				}).then(function(res) {
					that.isFun = true;
					if (res.data && res.data.data.length) {
						that.list = that.list.concat(res.data.data);
						that.page = res.data.current_page;
						that.loadStatus = 'more';
					} else if (that.page == 1) {
						that.isEmpty = true;
						that.loadStatus = 'more';
					} else {
						that.loadStatus = 'no-more';
					}
				});
			},
			typeClick(e) {
				if (this.isFun) {
					this.typeidx = e.currentTarget.dataset.i;
					this.isFun = false;
					this.list = [];
					this.page = 1;
					this.recommendDynamic();
				}
			},
			likeclick(e) {
				this.list[e.idx].is_like = e.is_like;
				this.list[e.idx].like_count = e.like_count;
			},
			navigateToFun(e) {
				let url = e.currentTarget.dataset.url;
				util.navigateTo({
					url: '/pages/' + url
				})
			},
			getMessageCount() {
				util.request(api.messageCountUrl).then(function(res) {
					if (res.data) {
						uni.setTabBarBadge({
							index: 3,
							text: res.data.toString()
						})
					} else {
						uni.removeTabBarBadge({
							index: 3
						})
					}
				})
			}
		},
		onReachBottom() {
			if (this.list.length) {
				this.page = this.page + 1;
				this.recommendDynamic();
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
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
	@import url("/static/css/tabbar/index.css");
	
	.qiandao{
		background-color: #FFA676;
		height: 110rpx;
		margin: 10rpx 30rpx;
		border-radius: 10rpx;
		display: flex;
		font-size: 26rpx;
		line-height: 110rpx;
		font-weight: bold;
		color: #333;
	}
	.qiandao image{
		width: 60rpx;
		height: 60rpx;
		margin: 25rpx 40rpx;
		margin: 25rpx 10rpx 25rpx 40rpx;
	}
	.qiandao button{
		height: 60rpx;
		font-size: 28rpx;
		width: 180rpx;
		line-height: 60rpx;
		margin: 25rpx;
		position: absolute;
		right: 6%;
		font-weight: bold;
		border-radius: 60rpx;
		color: #333;
	}
	
</style>