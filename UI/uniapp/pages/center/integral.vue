<template>
	<view class="container">
		<!-- Nav Bar -->
		<view class="nav-bar"
			:style="{paddingTop: statusBarHeight+'px',background: 'rgba(255, 255, 255, '+navbarTrans+')'}">
			<view class="blck-box df" @click="navBack">
				<image src="/static/img/back.png"></image>
			</view>
		</view>
		<!-- Box -->
		<view class="bg-box">
			<image class="bg-img" :src="insetUrl+'1.png'" mode="aspectFill" />
		</view>
		<view class="num-box" @click="openTipsPopup">
			<view class="num">{{user_info.integral_usable_num}}</view>
			<view class="txt">可用积分</view>
			<image src="/static/img/ts.png"></image>
		</view>
		<view v-if="integral" class="box" style="margin-top: -85rpx;">
			<view class="box-title">打卡签到</view>
			<view class="box-doc">
				<view>⛳️<text>每日打卡签到，领 {{ integral }} 积分</text></view>
			</view>
			<view class="data-box df">
				<view class="data-item df">
					<view class="data-round df" :style="{background: '#FFA676'}">
						<image src="/static/img/wc.png"></image>
					</view>
					<view class="data-txt">已打卡</view>
				</view>
				<view class="data-item df" v-for="(v,i) in 4" :key="i">
					<view class="data-round df" :style="{background: '#FFA67610'}">
						<text>+{{ integral }}</text>
					</view>
					<view class="data-txt">待领取</view>
				</view>
			</view>
		</view>
		<view class="box">
			<view class="box-title">积分记录</view>
			<view class="box-doc df">
				<view>{{user_info.integral_all}}<text>共计</text></view>
				<view>{{user_info.integral_used}}<text>已用</text></view>
			</view>
			<view class="list-box df" v-for="(item,idx) in list" :key="idx">
				<view>
					<view class="t1">{{item.title}}</view>
					<view class="t2">{{item.create_time}}</view>
				</view>
				<view class="t0" :style="{color:item.type==1?'#000':'#999'}">
					{{item.type==1?item.number:'-'+item.number}}
				</view>
			</view>
			<uni-load-more v-if="loadStatus != 'no-more'" :status="loadStatus"></uni-load-more>
			<view v-else class="no-more">没有更多了️</view>
		</view>
		<!-- Tips Box -->
		<uni-popup ref="addPopup" type="center">
			<view class="tips-popup df">
				<view class="tips-popup-t1">积分说明</view>
				<view class="tips-popup-t2">问：积分是什么？</view>
				<view class="tips-popup-t2">答：积分是由{{ appName }}平台根据用户贡献算法获得的结果数值；</view>
				<view class="tips-popup-t2">问：我要怎么获取积分？</view>
				<view class="tips-popup-t2">答：在使用{{ appName }}期间发布动态、获赞、获评论等一系列行为都可以产生对应的积分；</view>
				<view class="tips-popup-t2">问：积分有什么用？</view>
				<view class="tips-popup-t2">答：积分可以用于加入私密圈子、创建圈子等功能使用，更多权益正在更新中。</view>
				<view class="tips-popup-btn df" @click="closeTipsPopup"> 我知道了</view>
			</view>
		</uni-popup>
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
	export default {
		components: {},
		data() {
			return {
				statusBarHeight: app.globalData.statusBarHeight,
				insetUrl: api.insetUrl,
				appName: api.appName,
				navbarTrans: 0,
				integral: 0,
				user_info: {
					integral_all: 0,
					integral_usable: 0,
					integral_usable_num: 0,
					integral_used: 0
				},
				list: [],
				page: 1,
				isEmpty: false,
				loadStatus: 'more',
				tips_title: '',
			}
		},
		onShow() {
			let that = this;
			that.user_info = uni.getStorageSync('user_info');
			if (!that.user_info.is_clockin) {
				util.request(api.configUrl).then(function(res) {
					uni.setStorageSync('config', res.data);
					that.integral = res.data.lnk_jf_qd_value;
					if(that.integral>0) that.userSignIn();
					else that.userIntegealRecord();
				})
			} else {
				that.integral = uni.getStorageSync('config').lnk_jf_qd_value;
				that.userIntegealRecord();
			}
		},
		methods: {
			userIntegealRecord() {
				let that = this;
				that.loadStatus = 'loading';
				util.request(api.userIntegealRecordUrl, {
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
			userSignIn() {
				let that = this;
				util.request(api.userSignInUrl, {}, "POST").then(function(res) {
					that.tips_title = res.msg;
					if (res.code == 200) {
						that.user_info = res.data;
						uni.setStorageSync('user_info', res.data);
					}
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					that.userIntegealRecord();
				});
			},
			openTipsPopup() {
				this.$refs.addPopup.open();
			},
			closeTipsPopup() {
				this.$refs.addPopup.close();
			},
			navBack() {
				let currentRoutes = getCurrentPages();
				if (currentRoutes.length > 1) {
					uni.navigateBack();
				} else {
					uni.switchTab({
						url: '/pages/tabbar/center'
					})
				}
			},
			onReachBottom() {
				if (this.list.length) {
					this.page = this.page + 1;
					this.userIntegealRecord();
				}
			}
		},
		onPageScroll(e) {
			this.scrollTop = e.scrollTop;
			var trans = (e.scrollTop > 88 ? 88 : e.scrollTop) / 88;
			this.navbarTrans = trans;
		},
	}
</script>

<style>
	@import url("/static/css/center/integral.css");
</style>