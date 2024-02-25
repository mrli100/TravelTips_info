<template>
	<view>
		<uni-popup ref="popup" type="bottom" :mask-click="false" >
		<view class="zheshow" >
			<view class="cen_ter">
				<view class="box_At">
					<view class="box_At_text">获取您的昵称、头像、手机号</view>
					<view class="box_At_co">获取用户头像、昵称、手机号信息，主要用于完善个人资料，向用户提供更好使用体验</view>
					<view class="box_B" style="border-top:1px solid #f3f3f3 ;">
						<view class="acvter">头像</view>
						<button v-if="!active" class="acvter_all"  open-type="chooseAvatar" @chooseavatar="onChooseAvatar">
							<view class="mast">请选择头像</view>
						</button>
						<view v-if="active" class="img"><image :src="active" ></image></view>
					</view>
					<view class="box_B">
						<view class="acvter">昵称</view>
						<input class="acvter_all"  type="nickname" :value="nickname" @blur="bindblur" placeholder="请输入昵称" />
					</view>
					<view class="Brn_S">
						<view class="btn_btns" @click="colse">取消</view>
						<button  class="btn" @click="btns" >
							<view class="btn">保存</view>
						</button>
<!-- 						<button  class="btn" open-type="getPhoneNumber" @getphonenumber="btns" >
							<view class="btn">保存</view>
						</button> -->
					</view>
				</view>
			</view>
		</view>
		</uni-popup>
	</view>
</template>

<script>

	const util = require('@/utils/util');
	export default {
		props:['zheshow'],
		data() {
			return {
				on_zheshows:false,
				zheshow1:false,
				active:'',
				nickname:''
			}
		},
		watch:{
			zheshow() {
				this.$refs.popup.open('bottom')
			},
		},
		methods: {	
			colse(){
				this.$refs.popup.close()
			},
			onChooseAvatar(e) {
				let that = this
				
				var filePath = e.detail.avatarUrl;
				if (filePath) {
					uni.showLoading({
						title: '正在上传..',
						mask: true
					});
					util.lnkUploadFile(filePath,3).then(function(res) {
						console.log(res)
						that.active = res.data.url;
						uni.hideLoading();
					})
				}
				
				
				// uni.getFileSystemManager().readFile({
				// 	filePath: e.detail.avatarUrl, //选择图片返回的相对路径
				// 	encoding: "base64", 
				// 	success: (res) => {
				// 		let base64s = "data:image/jpeg;base64," + res.data
				// 		that.active = base64s
				// 		that.avatar = e.detail.avatarUrl
						
				// 	},
				// 	fail: (res) => reject(res.errMsg),	                
				// });
			},
			bindblur(e){this.nickname = e.detail.value},
			btns(e){
				// console.log(e,'e.detail.code换取手机号')
				// if (e.detail.errMsg == 'getPhoneNumber:fail user deny') {
				// 	uni.showToast({title:'已取消授权登录',icon:'none'})
				// 	this.$refs.popup.close()
				// 	return
				// }
				if(this.active==''){
					uni.showToast({title:'请选择上传头像',icon:'none'})
					return
				}
				if(this.nickname==''){
					uni.showToast({title:'请填写昵称',icon:'none'})
					return
				}
				this.$parent.loset({nickname:this.nickname,active:this.active}) // 信息传递父组件中
			}
		}
	}
</script>

<style lang="scss" scoped>
	.zheshow{
		 width: 100%;height: 100%;background-color: rgba(0,0,0,0.3);position: fixed;top: 0;left: 0;
		 display: flex;align-items: center;align-items: flex-end;
		.mast{margin-top: 6rpx;}
		.Brn_S{width: 70%;height: 100rpx;display: flex;align-items: center;justify-content: space-between;margin: 10rpx auto;}
		.btn_btns{width: 300rpx;height: 80rpx;background: antiquewhite; display: flex;align-items: center;
		 justify-content: center; border-radius: 10rpx; margin-right: 70rpx;background-color:  #fafafa;color: #39B54A;}
		.imgs{position: absolute;right: 6%;width: 32rpx;height: 32rpx;}
		.img{width: 90rpx;height: 90rpx;border-radius: 50%;margin-left: 80rpx;image{width: 100%;height: 100%;border-radius: 50%;}}
		.cen_ter{
			width: 100%;height: 600rpx;border-top-left-radius: 30rpx;border-top-right-radius: 30rpx;background-color: #FFFFFF;
			display: flex;align-items: center;justify-content: center;
			.box_At{width: 90%;height: 92%;margin-top: 20rpx;display: flex;flex-direction: column;.box_At_text{font-weight: bold;font-size: 30rpx}
			.box_At_co{font-size: 28rpx;color: #ababab;margin-top: 24rpx;}
			.box_B{width: 100%;height: 120rpx;border-bottom: 1px solid #f3f3f3;display: flex;align-items: center;
			 margin-top: 12rpx;.acvter_all{font-size: 28rpx;color: #ababab;margin-left: 80rpx;}}
			.btn{width:300rpx;margin: 35rpx auto;height: 80rpx;display: flex;align-items: center;justify-content: center;
			 background-color: #39B54A;color: #FFFFFF;border-radius: 10rpx;font-size: 30rpx;}
		}}
	}
	button {
		border-radius: 30rpx;height: 80rpx !important;padding-left: 0!important ;
		padding-right: 0!important ; background-color: rgba(0,0,0,0) !important;color: #ababab !important;font-family: PingFang SC !important;
	}
	button:after {
	    top: 0;left: 0; border: 1px solid rgba(0,0,0,0) !important; -webkit-transform: scale(.5);
		transform: scale(.5); -webkit-transform-origin: 0 0; transform-origin: 0 0; box-sizing: border-box; border-radius: 10px;
	}	
</style>
