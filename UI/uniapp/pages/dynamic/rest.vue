<template>
	<view class="container">
		<view class="box df">
			<view class="img-box df">
				<image class="img" :src="insetUrl+'15.png'" />
				<view class="txt">私密内容</view>
				<view class="content">此内容创作于 {{ info.name }} 中，该圈属于私密圈子如需查看此内容请先加入圈子，
					加入圈子需要使用 <text>{{ info.integral }}</text> 积分，
					点击立即加入后系统自动扣除相应积分，您即可成为该圈成员并可查看圈内所有内容。
				</view>
				<view class="content">注：积分使用无法退回，请仔细考虑后再进行操作。</view>
			</view>
			<view>
				<button class="btn df bg1" @click="followCircle">
					<image :src="info.avatar" mode="aspectFill"></image>
					<text>立即加入</text>
				</button>
				<button class="btn df bg2" @click="toCircle">
					<text>查看圈子</text>
				</button>
			</view>
		</view>
		<!-- Tips Box -->
		<uni-popup ref="tipsPopup" type="top" mask-background-color="rgba(0, 0, 0, 0)">
			<view class="tips-box df">
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
				insetUrl: api.insetUrl,
				id: 0,
				cid: 0,
				info: {
					name: '...',
					integral: 0,
				},
				tips_title: '',
			}
		},
		onLoad(option) {
			this.id = option.id;
			this.cid = option.cid;
			this.getCircleInfo();
		},
		methods: {
			getCircleInfo() {
				let that = this;
				util.request(api.getCircleInfoUrl, {
					id: that.cid
				}).then(function(res) {
					that.info = res.data
				});
			},
			followCircle() {
				let that = this;
				util.request(api.followCircleUrl, {
					id: that.cid,
					is_follow: false,
				}, 'POST').then(function(res) {
					if (res.code == 200) {
						that.tips_title = '加入成功 🎉';
						that.$refs.tipsPopup.open();
						setTimeout(function() {
							that.$refs.tipsPopup.close();
							uni.redirectTo({
								url: '/pages/dynamic/details?id=' + that.id,
							});
						}, 1500);
					} else {
						that.tips_title = res.msg;
						that.$refs.tipsPopup.open();
						setTimeout(function() {
							that.$refs.tipsPopup.close();
						}, 2000);
					}
				});
			},
			toCircle() {
				uni.navigateTo({
					url: '/pages/circle/details?id=' + this.cid
				})
			}
		},
	}
</script>

<style>
	@import url("/static/css/dynamic/rest.css");
</style>