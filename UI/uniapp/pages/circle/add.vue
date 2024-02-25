<template>
	<view class="container">
		<!-- Box -->
		<view class="box">
			<view v-if="is_jfrq" class="table-box df">
				<view class="table-item df" :class="{'item-active':current==i}" v-for="(item,i) in type" :key="i"
					@click="current=i">
					<text>{{item}}</text>
				</view>
			</view>
			<view class="img-box" v-if="avatar">
				<image :src="avatar"></image>
				<view class="del df" @click="avatar = ''">＋</view>
			</view>
			<view class="add df" v-else @click="addImg">
				<text>＋</text>
				<text class="tis">上传圈子头像</text>
			</view>
			<view class="table-tit" style="margin-top: 30rpx;">名称（最多8个字）<text>*</text></view>
			<view class="table-input">
				<input type="text" v-model="name" placeholder="这个圈子叫什么" maxlength="8" />
			</view>
			<block v-if="current==1">
				<view class="table-tit">积分<text>*</text></view>
				<view class="table-input">
					<input type="number" v-model="integral" placeholder="加圈所需要积分" maxlength="10" />
				</view>
			</block>
			<view class="table-tit">介绍（200字内）<text>*</text></view>
			<view class="table-input">
				<textarea v-model="intro" placeholder="介绍一下这个圈子吧" cursor-spacing="60" :auto-height="true"
					maxlength="200"></textarea>
			</view>
		</view>
		<view class="btn-box df">
			<view class="btn-item df" :class="{'hbg':btn_type==1 && is_jf_value}" @click="saveQun">
				<text>{{btn_type==1?'创建':'保存'}}</text>
				<view v-if="btn_type==1 && is_jf_value">/ {{ is_jf_value }} 积分</view>
			</view>
		</view>
		<view class="tips df">
			<text>{{btn_type==1?'创建':'保存'}}即表示您已认真阅读并同意</text>
			<view @click="toRichText(1)">《圈主协议》</view>
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
				id: 0,
				is_jf_value: 0,
				is_jfrq: 0,
				type: ['公开圈子', '私密圈子'],
				current: 0,
				avatar: '',
				name: '',
				intro: '',
				integral: '',
				btn_type: 1,
				tips_title: '',
			}
		},
		onLoad(option) {
			let that = this;
			that.btn_type = 1;
			if (option.id && option.id > 0) {
				that.id = option.id;
				that.btn_type = 2;
				that.getCircleInfo();
			}
			uni.setNavigationBarTitle({
				title: that.btn_type == 1 ? '创建圈子' : '编辑圈子'
			})
			util.request(api.configUrl).then(function(res) {
				uni.setStorageSync('config', res.data);
				that.is_jf_value = res.data.app?.usercircle?.is_jf_value;
				that.is_jfrq = res.data.app?.usercircle?.is_jfrq;
			})
		},
		methods: {
			getCircleInfo() {
				let that = this;
				util.request(api.appCenterApi+'/usercircle/get_circle_info', {
					id: that.id
				}).then(function(res) {
					that.id = res.data.id;
					that.current = res.data.type;
					that.avatar = res.data.avatar;
					that.name = res.data.name;
					that.intro = res.data.intro;
					that.integral = res.data.integral;
				});
			},
			saveQun() {
				let that = this;
				let integral_usable_num = uni.getStorageSync('user_info').integral_usable_num;
				if (that.btn_type == 1 && that.is_jf_value > integral_usable_num) {
					that.tips_title = '积分不足，当前账户可用积分 ' + integral_usable_num + ' 创建圈子需 ' + that.is_jf_value + ' 积分';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					return;
				}
				if (!that.avatar || !that.name || !that.intro) {
					if (!that.intro) that.tips_title = '请填写圈子简介';
					if (!that.name) that.tips_title = '请填写圈子名称';
					if (!that.avatar) that.tips_title = '请上传圈子头像';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					return;
				}
				util.request(api.appCenterApi+'/usercircle/save_circle', {
					id: that.id,
					type: that.current,
					avatar: that.avatar,
					name: that.name,
					intro: that.intro,
					integral: that.integral
				}, 'POST').then(function(res) {
					that.tips_title = that.id ? '保存成功 🎉' : '提交成功 🎉';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					setTimeout(function() {
						app.globalData.dw = 1;
						uni.redirectTo({
							url: '/pages/center/circle',
						});
					}, 1000);
				});
			},
			addImg() {
				let that = this;
				uni.chooseImage({
					count: 1,
					success: (res) => {
						uni.showLoading({
							title: '上传中...',
							mask: true
						});
						wx.cropImage({
							src: res.tempFilePaths[0],
							cropScale: '1:1',
							success(tempFilePath) {
								let imgUrl = tempFilePath.tempFilePath;
								util.lnkUploadFile(imgUrl).then(function(fileImg) {
									that.avatar = fileImg.data.url;
									uni.hideLoading();
								});
							},
							complete() {
								uni.hideLoading();
							}
						})
					},
					fail: (err) => {
						console.log('chooseImage fail', err)
					}
				})
			},
			toRichText(id) {
				uni.navigateTo({
					url: '/pages/rich-text/index?id=' + id
				})
			},
		},
	}
</script>

<style>
	@import url("/static/css/circle/add.css");
</style>