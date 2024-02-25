<template>
	<view class="container">
		<!-- Box -->
		<view class="box">
			<view class="table-tit">反馈类型<text>*</text></view>
			<view class="table-box df">
				<view class="table-item df" :class="{'item-active':current==i}" v-for="(item,i) in type" :key="i"
					@click="current=i">
					<text>{{item}}</text>
				</view>
			</view>
			<view class="table-tit">反馈内容<text>*</text></view>
			<view class="table-textarea">
				<textarea v-model="ct" placeholder="在这里填写" auto-height="true" maxlength="200"></textarea>
				<scroll-view scroll-x="true" class="scroll">
					<view class="scroll_box">
						<view class="img-box" v-for="(v,i) in imgs" :key="id">
							<image class="pic" :src="v" mode="aspectFill"></image>
							<view class="del df" @click="delImg(i)">＋</view>
						</view>
						<view v-if="imgs.length<10" @click="addImg" class="add df">＋</view>
					</view>
				</scroll-view>
			</view>
			<view class="table-tit">联系电话</view>
			<view class="table-input">
				<input v-model="contact" type="number" placeholder="方便即时与您取得联系" maxlength="11" />
			</view>
		</view>
		<button class="btn-box df" @click="saveFeedback">提交反馈</button>
		<view class="tips-box">提交反馈后，我们会在24h内为您处理您的建议和意见。</view>
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
				type: ['产品建议', '功能故障', '其他问题'],
				current: 0,
				ct: '',
				contact: '',
				imgs: [],
				tips_title: '',
			}
		},
		onLoad() {},
		methods: {
			saveFeedback() {
				let that = this;
				if (!that.ct) {
					that.tips_title = '请描述您的问题';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					return
				}
				util.request(api.saveFeedbackUrl, {
					contact: that.contact,
					type: that.type[that.current],
					content: that.ct,
					imgs: that.imgs,
				}, "POST").then(function(res) {
					that.contact = '';
					that.current = 0;
					that.ct = '';
					that.imgs = [];
					that.tips_title = res.msg;
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
				});
			},
			addImg() {
				let that = this;
				let count = 9 - that.imgs.length;
				uni.chooseImage({
					count: count,
					success: (res) => {
						console.log('res', res)
						uni.showLoading({
							title: '上传中...',
							mask: true
						});
						var filePath = res.tempFilePaths;
						for (let v of filePath) {
							util.lnkUploadFile(v).then(function(fileImg) {
								that.imgs.push(fileImg.data.url)
								uni.hideLoading();
							});
						}
					},
					fail: (err) => {
						console.log('chooseImage fail', err)
					}
				})
			},
			delImg(idx) {
				this.imgs.splice(idx, 1);
			},
		},
	}
</script>

<style>
	@import url("/static/css/setting/feedback.css");
</style>