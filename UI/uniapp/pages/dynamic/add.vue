<template>
	<view class="container df">
		<view class="textarea-box">
			<textarea v-model="ct" maxlength="2000" auto-height="true" placeholder="分享灵感和作品"></textarea>
		</view>
		<scroll-view scroll-x="true" class="scroll">
			<view class="scroll_box">
				<view v-if="type==0" class="add df" @click="openFile">＋</view>
				<block v-if="type==1">
					<view class="img-box" v-for="(item,idx) in pic_arr" :key="id">
						<image class="pic" :src="item.url" mode="aspectFill"></image>
						<view class="del df" @click="delImg(idx)">＋</view>
						<view class="sort-box df" v-if="pic_arr.length>1">
							<view class="sort-box-item df" :style="{color:idx>0?'#fff':'#cecece'}"
								@click.stop="onSort(idx,0)">←
							</view>
							<view class="sort-box-item df" :style="{color:idx<pic_arr.length-1?'#fff':'#cecece'}"
								@click.stop="onSort(idx,1)">→</view>
						</view>
					</view>
					<view @click="addImg(1)" class="add df">＋</view>
				</block>
				<block v-if="type==2">
					<view class="img-box">
						<video class="pic" :src="video.url"></video>
						<view class="del df" @click="delVideoImg(1)">＋</view>
					</view>
					<view v-if="video.img" class="img-box">
						<image class="pic" :src="video.img" mode="aspectFill"></image>
						<view class="del df" @click="delVideoImg(2)">＋</view>
					</view>
					<view v-else @click="addImg(2)" class="add df">
						＋<view>视频封面，建议与视频尺寸保持一致</view>
					</view>
				</block>
			</view>
		</scroll-view>
		<view class="topic-box df">
			<view class="topic-item df" @click="openCircle">
				<image src="/static/img/qz.png"></image>
				<view class="name">{{circle?circle:'选择圈子'}}</view>
			</view>
			<view class="topic-item df" @click="locationClick">
				<image src="/static/img/dw.png"></image>
				<view class="name ohto">{{adds.name?adds.name:'添加位置'}}</view>
			</view>
		</view>
		<view class="footer">
			<view class="footer_box df">
				<view class="tips df" @click="openTipsPopup">
					<view class="tips-item df">
						<image src="/static/img/ts.png"></image>
					</view>
					<text>小贴士</text>
				</view>
				<view class="btn df" @click="saveDynamic">
					<text>发布动态</text>
					<view v-if="integral">/ +{{ integral }} 积分</view>
				</view>
			</view>
		</view>
		<!-- Tips Popup -->
		<uni-popup ref="filePopup" type="center">
			<view class="file-box df">
				<view class="file-item df" @click="addVideo">
					<image :src="insetUrl+'video.png'" />
					<text>添加视频</text>
				</view>
				<view class="file-line"></view>
				<view class="file-item df" @click="addImg(1)">
					<image :src="insetUrl+'image.png'" />
					<text>添加图片</text>
				</view>
			</view>
		</uni-popup>
		<uni-popup ref="circlePopup" type="bottom" :safe-area="false">
			<view class="circle-popup">
				<view v-if="!circlearr.length">
					<view class="empty-box">
						<image :src="insetUrl+'6.png'" mode="aspectFill" />
						<view class="text df">
							<view>没有圈子，</view>
							<view @click="toCircle" style="color: #FFA676;">去关注一个</view>
						</view>
					</view>
				</view>
				<block v-else>
					<view class="circle-box">
						<view class="circle-wrap">
							<block v-for="(v,i) in circlearr" :key="i">
								<view class="circle-item df" @click="circleClick" :data-i="i"
									:class="{'active':circle_id==v.id}">
									<image :src="v.avatar" mode="aspectFill"></image>
									<text>{{ v.name }}</text>
								</view>
							</block>
						</view>
					</view>
					<view class="circle-btn df">
						<view class="btn1" @click="closeCircle(1)">暂不选择</view>
						<view class="btn2" @click="closeCircle(0)">确定</view>
					</view>
				</block>
			</view>
		</uni-popup>
		<uni-popup ref="addPopup" type="center">
			<view class="tips-popup df">
				<view class="tips-popup-t1">发布小贴士</view>
				<view class="tips-popup-t2">我们鼓励向上、真实、原创的内容，含以下内容的动态将不会被推荐：</view>
				<view class="tips-popup-t2">1. 含有不文明语言、过度性感图片；</view>
				<view class="tips-popup-t2">2. 含有投资股票、基金、涉政等信息；</view>
				<view class="tips-popup-t2">3. 冒充他人身份或搬运他人作品；</view>
				<view class="tips-popup-t2">4. 通过有奖方式诱导他人收藏、评论、转发、关注；</view>
				<view class="tips-popup-t2">5. 为刻意搏眼球，在内容、图片等处使用夸张表达。</view>
				<view class="tips-popup-btn df" @click="closeTipsPopup"> 我知道了</view>
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
	export default {
		components: {},
		data() {
			return {
				insetUrl: api.insetUrl,
				id: 0,
				integral: 0,
				ct: '',
				type: 0,
				pic_arr: [],
				video: {
					high: 0,
					wide: 0,
					url: '',
					img: '',
				},
				circle: '',
				circle_id: 0,
				circlearr: [],
				adds: {},
				tips_title: '',
				isaddVideo: false,
			}
		},
		onLoad(options) {
			let that = this;
			if (options.id && options.id > 0) {
				that.id = options.id;
				that.getDynamicInfo();
			}
			that.dynamicCircle();
			util.request(api.configUrl).then(function(res) {
				uni.setStorageSync('config', res.data);
				that.integral = res.data.lnk_jf_ft;
			})
		},
		methods: {
			getDynamicInfo() {
				let that = this;
				util.request(api.getDynamicInfoUrl, {
					id: that.id
				}).then(function(res) {
					that.id = res.data.id;
					that.type = res.data.type;
					that.ct = res.data.content;
					if (that.type == 1) {
						that.pic_arr = res.data.imgs;
					} else if (that.type == 2) {
						that.video.high = res.data.video.high
						that.video.wide = res.data.video.wide
						that.video.url = res.data.video.url
						that.video.img = res.data.video.img
					}
					that.circle_id = res.data.circle_id;
					that.circle = res.data.circle_name;
					that.adds.name = res.data.adds;
					that.adds.latitude = res.data.lat;
					that.adds.longitude = res.data.lng;
				});
			},
			dynamicCircle() {
				let that = this;
				util.request(api.dynamicCircleUrl).then(function(res) {
					that.circlearr = res.data;
				});
			},
			saveDynamic() {
				let that = this;
				if(that.isaddVideo){
					that.tips_title = '视频上传中';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					return;
				}
				if(that.video.url && !that.pic_arr){
					that.tips_title = '未上传视频封面图';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
				}
				
				if (!that.ct) {
					that.tips_title = '动态内容不能为空哦';
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
					return;
				}
				util.request(api.saveDynamicUrl, {
					id: that.id,
					type: that.type,
					content: that.ct,
					circle_id: that.circle_id,
					circle: that.circle,
					adds: that.adds,
					imgs: that.pic_arr,
					video: that.video,
				}, 'POST').then(function(res) {
					that.tips_title = res.msg;
					if (res.code == 200) {
						setTimeout(function() {
							app.globalData.dw = 1;
							uni.switchTab({
								url: '/pages/tabbar/center'
							});
						}, 1000);
					}
					that.$refs.tipsPopup.open();
					setTimeout(function() {
						that.$refs.tipsPopup.close();
					}, 1500);
				});
			},
			addImg(type) {
				this.$refs.filePopup.close();
				let that = this;
				let count = 20 - that.pic_arr.length;
				if (type == 2) {
					count = 1;
				}
				uni.chooseImage({
					count: count,
					success: (res) => {
						uni.showLoading({
							title: '上传中...',
							mask: true
						});
						var filePath = res.tempFilePaths;
						if (type == 1) {
							that.type = 1;
							that.getImageInfo(filePath).then(function(imgArr) {
								for (let i in imgArr) {
									util.lnkUploadFile(imgArr[i].url).then(function(fileImg) {
										console.log(fileImg);
										if(fileImg.error == 87014){
											uni.hideLoading();
											uni.showToast({
												title: '图片不合规',
												duration: 2000,
												icon: 'error'
											});
										}else{
											imgArr[i].url = fileImg.data.url;
											if (imgArr.length == parseInt(i) + 1) {
												that.pic_arr = that.pic_arr.concat(imgArr);
												uni.hideLoading();
											}
										}
									});
								}
							});
						} else if (type == 2) {
							util.lnkUploadFile(filePath[0]).then(function(fileImg) {
								that.video.img = fileImg.data.url;
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
				this.pic_arr.splice(idx, 1);
				if (this.pic_arr.length <= 0) this.type = 0;
			},
			onSort(idx, type) {
				if (type == 0) {
					this.pic_arr.splice(idx - 1, 0, this.pic_arr[idx]);
					this.pic_arr.splice(idx + 1, 1);
				} else if (type == 1) {
					this.pic_arr.splice(idx + 2, 0, this.pic_arr[idx]);
					this.pic_arr.splice(idx, 1);
				}
			},
			addVideo() {
				this.$refs.filePopup.close();
				let that = this;
				uni.chooseMedia({
					count: 1,
					mediaType: ['video'],
					success: (res) => {
						that.isaddVideo = true;
						uni.showLoading({
							title: '上传中...',
							mask: true
						});
						that.type = 2;
						var tempFiles = res.tempFiles[0];
						if (tempFiles.tempFilePath) {
							util.lnkUploadFile(tempFiles.tempFilePath, 0).then(function(fileVideo) {
								that.video.high = tempFiles.height;
								that.video.wide = tempFiles.width;
								that.video.url = fileVideo.data.url;
								if (tempFiles.thumbTempFilePath) {
									util.lnkUploadFile(tempFiles.thumbTempFilePath).then(function(
										fileImg) {
										if(fileImg.error == 87014){
											uni.hideLoading();
											that.video.high = 0;
											that.video.wide = 0;
											that.video.url = '';		
											uni.showToast({
												title: '视频不合规',
												duration: 2000,
												icon: 'error'
											});
											
											return ;
										}else{
											that.video.img = fileImg.data.url;
										}
										
									});
								}
								that.isaddVideo = false;
							});
						}
						uni.hideLoading();
					},
					fail: (err) => {
						console.log('chooseMedia fail', err)
					}
				})
			},
			delVideoImg(type) {
				if (type == 1) {
					this.type = 0;
					this.video = {
						img: ''
					};
				} else if (type == 2) {
					this.$nextTick(() => {
						this.video.img = '';
					})
				}
			},
			getImageInfo(imgs) {
				return new Promise(function(resolve, reject) {
					let imgArr = [];
					for (let v of imgs) {
						uni.getImageInfo({
							src: v,
							success: function(res) {
								let item = {};
								item.url = res.path;
								item.wide = res.width;
								item.high = res.height;
								imgArr.push(item);
								if (imgs.length == imgArr.length) {
									resolve(imgArr);
								}
							}
						});
					}
				});
			},
			locationClick() {
				let that = this;
				uni.chooseLocation({
					success: function(res) {
						that.adds = res;
					}
				});
			},
			circleClick(e) {
				let i = e.currentTarget.dataset.i;
				this.circle_id = this.circlearr[i].id;
				this.circle = this.circlearr[i].name;
			},
			toCircle() {
				uni.switchTab({
					url: '/pages/tabbar/circle'
				})
			},
			openFile() {
				this.$refs.filePopup.open();
			},
			openCircle() {
				this.$refs.circlePopup.open();
			},
			closeCircle(type) {
				this.$refs.circlePopup.close();
				if (type == 1) {
					this.circle_id = 0;
					this.circle = '';
				}
			},
			openTipsPopup() {
				this.$refs.addPopup.open();
			},
			closeTipsPopup() {
				this.$refs.addPopup.close();
			},
		},
	}
</script>

<style>
	@import url("/static/css/dynamic/add.css");
</style>