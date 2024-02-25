<template>
	<view class="container">
		<!-- Nav Box -->
		<view class="nav-bar" :style="{paddingTop: statusBarHeight+'px'}">
			<view class="nav-search df">
				<view class="nav-back df" @click="navBack">
					<image src="/static/img/back.png"></image>
				</view>
				<view class="search-box df">
					<input v-model="keyword" @confirm="searchConfirm" placeholder="输入搜索关键字" confirm-type="search"
						:focus="true" />
				</view>
			</view>
			<view class="bar-box df">
				<block v-for="(item,idx) in bararr" :key="idx">
					<view v-if="idx>0" class="bar-line"></view>
					<view @click="barClick" :data-i="idx" class="bar-item" :class="{'bar-active':idx==baridx}">
						{{item.name}}
						<text v-if="item.count">{{item.count}}</text>
					</view>
				</block>
			</view>
		</view>
		<!-- Content Box -->
		<view class="content" :style="{marginTop: statusBarHeight+88+'px'}">
			<view v-if="isEmpty" class="empty-box">
				<image :src="insetUrl+'11.png'" />
				<view class="text">空，换个词试试～</view>
			</view>
			<view v-else class="content-box">
				<block v-for="(item,idx) in list" :key="item.id">
					<block v-if="baridx==0">
						<dynamicBox :item="item" :idx="idx" @likeback="likeclick"></dynamicBox>
					</block>
					<block v-if="baridx==1">
						<circle-card :item="item" :idx="idx"></circle-card>
					</block>
					<view v-if="baridx==2" class="user-box df" :data-url="'user/details?id='+item.id"
						@click="navigateToFun">
						<image :src="item.avatar" mode="aspectFill"></image>
						<view class="user-item">
							<view class="t1 ohto">{{ item.name }}</view>
							<view class="t2" v-if="item.career">@{{ item.career }}</view>
						</view>
						<view class="user-btn">{{ item.is_follow?'已关注':'关注' }}</view>
					</view>
				</block>
				<block v-if="!isEmpty">
					<uni-load-more v-if="loadStatus != 'no-more'" :status="loadStatus"></uni-load-more>
					<view v-else class="no-more">没有更多了</view>
				</block>
			</view>
		</view>
		<!-- Tips Box -->
		<uni-popup ref="tipsPopup" type="top" mask-background-color="rgba(0, 0, 0, 0)">
			<view class="tips-box df" :style="{marginTop: statusBarHeight+44+'px'}">
				<view class="tips-item">{{tips_title}}</view>
			</view>
		</uni-popup>
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
				keyword: '',
				bararr: [{
					name: '动态',
					count: 0
				}, {
					name: '圈子',
					count: 0
				}, {
					name: '用户',
					count: 0
				}],
				baridx: 0,
				list: [],
				page: 1,
				isPage: false,
				isEmpty: false,
				isFun: true,
				loadStatus: 'more',
				tips_title: '',
			}
		},
		onLoad() {},
		methods: {
			searchConfirm() {
				let that = this;
				if (that.keyword) {
					if (!that.isPage) {
						that.list = [];
						that.page = 1;
						that.searchCount();
					}
					that.loadStatus = 'loading';
					that.isPage = false;
					that.isEmpty = false;
					util.request(api.searchUrl, {
						page: that.page,
						type: that.baridx,
						keyword: that.keyword,
					}).then(function(res) {
						that.isFun = true;
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
				} else {
					that.isFun = true;
					that.tips_title = '输入关键词搜索';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 2000);
				}
			},
			likeclick(e) {
				this.list[e.idx].is_like = e.is_like;
				this.list[e.idx].like_count = e.like_count;
			},
			searchCount() {
				let that = this;
				util.request(api.searchCountUrl, {
					keyword: that.keyword
				}).then(function(res) {
					that.bararr[0].count = res.data[0];
					that.bararr[1].count = res.data[1];
					that.bararr[2].count = res.data[2];
				});
			},
			barClick(e) {
				if (this.isFun) {
					this.baridx = e.currentTarget.dataset.i;
					this.isFun = false;
					this.list = [];
					this.page = 1;
					this.searchConfirm(true);
				}
			},
			navigateToFun(e) {
				let url = e.currentTarget.dataset.url;
				uni.navigateTo({
					url: '/pages/' + url
				})
			},
			navBack() {
				let currentRoutes = getCurrentPages();
				if (currentRoutes.length > 1) {
					uni.navigateBack();
				} else {
					uni.switchTab({
						url: '/pages/tabbar/index'
					})
				}
			}
		},
		onReachBottom() {
			if (this.list.length) {
				this.isPage = true;
				this.page = this.page + 1;
				this.searchConfirm();
			}
		},
	}
</script>

<style>
	@import url("/static/css/search/index.css");
</style>