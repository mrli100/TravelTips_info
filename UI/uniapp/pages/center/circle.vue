<template>
	<view class="container">
		<view v-if="isEmpty" class="empty-box">
			<image :src="insetUrl+'6.png'" />
			<view class="text">空..</view>
		</view>
		<view class="list" v-else>
			<view class="list-item df" v-for="(item,idx) in list" :key="idx">
				<view class="del df" :data-idx="idx" @click="userDelCircle">
					<image src="/static/img/sc.png"></image>
				</view>
				<view class="img">
					<lazy-load :src="item.avatar" border-radius="100rpx"></lazy-load>
				</view>
				<view class="name">{{ item.name }}</view>
				<view class="doc">
					<text>{{ item.dynamic_count }} 篇内容</text>
					<text>{{ item.user_count }} 位圈友</text>
				</view>
				<view class="btn df">
					<view class="btn-item bg1" :data-url="'circle/add?id='+item.id" @click="navigateToFun">
						编辑
					</view>
					<view class="btn-item bg2" :data-url="'center/examine?id='+item.id+'&name='+item.name"
						@click="navigateToFun">
						审帖
					</view>
				</view>
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
				list: [],
				isEmpty: false,
				tips_title: '',
			}
		},
		onLoad() {
			this.userCircleList();
		},
		methods: {
			userCircleList() {
				let that = this;
				that.isEmpty = false;
				util.request(api.appCenterApi+'/usercircle/user_circle_list').then(function(res) {
					if (res.data.length) {
						that.list = res.data;
					} else {
						that.isEmpty = true;
					}
				})
			},
			userDelCircle(e) {
				let that = this;
				let idx = e.currentTarget.dataset.idx;
				uni.showModal({
					title: '确认要删除这个圈子吗？',
					success: function(res) {
						if (res.confirm) {
							util.request(api.appCenterApi+'/usercircle/user_del_circle', {
								circle_id: that.list[idx].id
							}, 'POST').then(function(res) {
								that.list.splice(idx, 1);
								if (!that.list.length) that.isEmpty = true;
								that.tips_title = res.msg;
								that.$refs.tipsPopup.open();
								setTimeout(function() {
									that.$refs.tipsPopup.close();
								}, 1500);
							})
						}
					}
				});
			},
			navigateToFun(e) {
				let url = e.currentTarget.dataset.url;
				uni.navigateTo({
					url: '/pages/' + url
				})
			},
		},
	}
</script>

<style>
	@import url("/static/css/center/circle.css");
</style>