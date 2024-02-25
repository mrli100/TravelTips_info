<template>
	<view class="container">
		<!-- Nav Box -->
		<view class="nav-box" :style="{paddingTop: statusBarHeight+'px',background:navbarTrans==1?'#fff':'#101010'}">
			<view class="nav-item df">
				<view data-url="search/index" @click="navigateToFun" class="search df"
					:style="{background:navbarTrans==1?'#f5f5f5':'#212121'}">
					<image src="/static/img/ss.png"></image>
					<text>搜索</text>
				</view>
			</view>
		</view>
		<!-- Put Box -->
		<view class="put-box" :style="{paddingTop: statusBarHeight+54+'px'}">
			<block v-for="(item,idx) in putarr" :key="idx">
				<view class="put-item df">
					<block v-for="(v,i) in item" :key="i">
						<view class="put-view df" :data-url="'circle/details?id='+v.id" @click="navigateToFun"
							:style="{background:v.highlight?'#FFA676':'#212121'}">
							<view class="cu-img-group" v-if="v.user_count">
								<view v-for="(img,k) in v.user" :key="k" class="cu-img">
									<image :src="img" mode="aspectFill"></image>
								</view>
								<view class="cu-img cu-item" :class="{'cu-active':v.highlight}">{{v.user_count}}人</view>
							</view>
							<view class="cu-name" :style="{color:v.highlight?'#000':'#ccc'}">{{v.name}}</view>
						</view>
					</block>
					<view style="flex-shrink: 0;width: 10px;color: #101010;">LNK</view>
				</view>
			</block>
		</view>
		<!-- Content Box -->
		<view class="content">
			<block v-for="(item,idx) in list" :key="idx">
				<circle-card :item="item" :idx="idx"></circle-card>
			</block>
			<uni-load-more v-if="loadStatus != 'no-more'" :status="loadStatus"></uni-load-more>
			<view v-else class="no-more">没有更多了️</view>
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
				statusBarHeight: app.globalData.statusBarHeight,
				scrollTop: 0,
				navbarTrans: 0,
				putarr: [],
				list: [],
				page: 1,
				isEmpty: false,
				loadStatus: 'more',
			}
		},
		onPullDownRefresh() {
			this.list = [];
			this.page = 1;
			this.getTopCircle();
			this.getCircleList();
			uni.stopPullDownRefresh();
		},
		async onLoad() {
			util.showShareMenu();
			await this.$onLaunched;
			this.getTopCircle();
			this.getCircleList();
		},
		onShow() {
			util.getMessageCount();
		},
		methods: {
			getCircleList() {
				let that = this;
				that.loadStatus = 'loading';
				that.isEmpty = false;
				util.request(api.getCircleListUrl, {
					page: that.page
				}).then(function(res) {
					if (res.data.data.length > 0) {
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
			getTopCircle() {
				let that = this;
				util.request(api.getTopCircleUrl).then(function(res) {
					that.putarr = res.data;
				});
			},
			navigateToFun(e) {
				let url = e.currentTarget.dataset.url;
				uni.navigateTo({
					url: '/pages/' + url
				})
			},
		},
		onReachBottom() {
			if (this.list.length) {
				this.page = this.page + 1;
				this.getCircleList();
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
			let frontColor = "#ffffff";
			var trans = (e.scrollTop > 200 ? 200 : e.scrollTop) / 200;
			if (trans >= 1) {
				frontColor = "#000000";
			}
			this.navbarTrans = trans;
			uni.setNavigationBarColor({
				frontColor: frontColor,
				backgroundColor: "#ffffff",
				animation: {
					duration: 400,
					timingFunc: 'easeIn'
				}
			})
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
	@import url("/static/css/tabbar/circle.css");
</style>