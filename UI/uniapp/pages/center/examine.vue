<template>
	<view class="container">
		<view class="empty-box" v-if="isEmpty">
			<image :src="insetUrl+'4.png'" mode="aspectFill" />
			<view class="text">还没有要审核的内容..</view>
		</view>
		<view class="content">
			<block v-for="(item,idx) in list" :key="idx">
				<view class="card-item">
					<view class="card-item-avatar" :data-url="'user/details?id='+item.user_id" @click="navigateToFun">
						<image :src="item.user.avatar" mode="aspectFill"></image>
					</view>
					<view class="card-item-info">
						<view class="item-info-user" :data-url="'user/details?id='+item.user_id" @click="navigateToFun">
							<view class="info-user-name ohto">{{item.user.name}}</view>
							<view class="info-user-tag" v-if="item.user.career">@{{item.user.career}}</view>
						</view>
						<view class="item-info-file" v-if="item.type!=0">
							<block v-if="item.type==1">
								<block v-if="item.img.length == 1">
									<view :data-idx="idx" data-i="0" @click="imageClick" class="info-h-w"
										:class="{'info-w-h':item.img[0].wide > item.img[0].high}">
										<lazy-load :src="item.img[0].url" border-radius="8rpx"></lazy-load>
									</view>
								</block>
								<view v-else class="info-img-box" v-for="(img,i) in item.img" :key="i" :data-idx="idx"
									:data-i="i" @click="imageClick">
									<lazy-load :src="img.url" border-radius="8rpx"></lazy-load>
								</view>
							</block>
							<block v-if="item.type==2">
								<video class="info-h-w" :class="{'info-w-h':item.video.wide > item.video.high}"
									:src="item.video.url" :poster="item.video.img">
								</video>
							</block>
						</view>
						<view class="item-info-content">{{item.content}}</view>
						<view class="df">
							<view class="info-topic df" :data-url="'circle/details?id='+item.circle_id"
								@click="navigateToFun">
								<image src="/static/img/qz.png"></image>
								<view class="name">{{item.circle_name}}</view>
							</view>
							<view v-if="item.adds" class="info-topic df" :data-i="idx" @click.stop="openLocationClick">
								<image src="/static/img/dw.png"></image>
								<view class="name ohto">{{item.adds}}</view>
							</view>
						</view>
						<view class="item-info-quantity df">
							<text class="date">{{item.create_time}}</text>
							<view class="btn df">
								<view class="bg1" :data-i="idx" @click="openRejectPopup">驳回</view>
								<view class="bg2" :data-i="idx" :data-status="1" @click="circleExamineDynamic">通过</view>
							</view>
						</view>
					</view>
				</view>
			</block>
			<uni-load-more v-if="loadStatus != 'no-more'" :status="loadStatus"></uni-load-more>
			<view v-else class="no-more">没有更多了️</view>
		</view>
		<uni-popup ref="rejectPopup" type="center">
			<view class="reject-popup">
				<view class="reject-title ohto">驳回动态：{{list[idx].content}}</view>
				<view class="reject-table">选择驳回原因<text>*</text></view>
				<block v-for="(item,i) in menu" :key="i">
					<view class="reject-item" @click="reason=item" :class="{'active':reason==item}" :data-ct="item">
						{{item}}
					</view>
				</block>
				<view class="reject-btn df" :data-status="3" @click="circleExamineDynamic"> 确认</view>
			</view>
		</uni-popup>
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
	import dynamicBox from '@/components/dynamic/box.vue';
	export default {
		components: {
			dynamicBox
		},
		data() {
			return {
				statusBarHeight: app.globalData.statusBarHeight,
				insetUrl: api.insetUrl,
				status: 1,
				id: 0,
				idx: -1,
				list: [],
				page: 1,
				reason: '',
				isEmpty: false,
				loadStatus: 'more',
				menu: uni.getStorageSync('config').lnk_jb,
				tips_title: '',
			}
		},
		onLoad(option) {
			if (option.id && option.id > 0) {
				this.id = option.id;
				uni.setNavigationBarTitle({
					title: option.name
				})
				this.circleDynamicList();
			}
		},
		methods: {
			circleDynamicList() {
				let that = this;
				util.request(api.appCenterApi+'/usercircle/circle_dynamic_list', {
					id: that.id,
					page: that.page,
				}).then(function(res) {
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
			circleExamineDynamic(e) {
				let that = this;
				let status = e.currentTarget.dataset.status;
				if (status == 1) that.idx = e.currentTarget.dataset.i;
				if (status == 3 && !that.reason) {
					that.tips_title = '驳回动态请选择驳回原因';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					return;
				}
				let img = '';
				if (that.list[that.idx].type == 1) {
					img = that.list[that.idx].img[0].url;
				} else if (that.list[that.idx].type == 2) {
					img = that.list[that.idx].video.img;
				}
				util.request(api.appCenterApi+'/usercircle/circle_examine_dynamic', {
					id: that.list[that.idx].id,
					user_id: that.list[that.idx].user_id,
					reason: that.reason,
					status: status,
					img: img,
				}, 'POST').then(function(res) {
					if (res.code == 200) {
						if (status == 3) that.$refs.rejectPopup.close();
						that.list.splice(that.idx, 1);
						if (that.list.length == 0) that.isEmpty = true;
					}
					that.tips_title = res.msg;
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
				});
			},
			imageClick(e) {
				let idx = e.currentTarget.dataset.idx;
				let i = e.currentTarget.dataset.i;
				let urls = [];
				for (let v of this.list[idx].img) {
					urls.push(v.url);
				}
				uni.previewImage({
					current: urls[i],
					urls: urls,
				});
			},
			openLocationClick(e) {
				let idx = e.currentTarget.dataset.i;
				uni.openLocation({
					latitude: parseFloat(this.list[idx].lat),
					longitude: parseFloat(this.list[idx].lng),
					name: this.list[idx].adds
				});
			},
			openRejectPopup(e) {
				this.idx = e.currentTarget.dataset.i;
				this.$refs.rejectPopup.open();
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
				this.circleDynamicList();
			}
		},
	}
</script>

<style>
	@import url("/static/css/center/examine.css");
</style>