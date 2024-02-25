<template>
	<!-- 自定义收银台页面模式 -->
	<view class="uni-pay">
		<view class="mobile-pay-popup">
			<view class="mobile-pay-popup-title">收银台</view>
			<view class="mobile-pay-popup-amount-box">
				<view>{{title}}：</view>
				<view class="mobile-pay-popup-amount">{{ (options.total_fee / 100).toFixed(2) }}<span style="font-size: 24rpx;">元</span></view>
			</view>
			<view class="mobile-pay-popup-provider-list">
				<uni-list>
					<!-- #ifdef MP-WEIXIN || H5 || APP -->
					<uni-list-item thumb="/static/img/pay/wxpay.png" title="微信支付" @click="createOrder({ provider: 'wxpay' })" clickable link></uni-list-item>
					<!-- #endif -->
					<!-- #ifdef MP-ALIPAY || H5 || APP -->
					<uni-list-item thumb="/static/img/pay/alipay.png" title="支付宝" @click="createOrder({ provider: 'alipay' })" clickable link></uni-list-item>
					<!-- #endif -->
				</uni-list>
			</view>
		</view>
	</view>
</template>

<script>
const util = require('@/utils/util');
export default {
	props: {
		options: {
			type: Object,
			require: true
		},
		title: {
			type: String,
			default: '待支付金额'
		},
		orderurl: {
			type: String,
			require: true
		},
		paysuccess: {
			type: Function,
			require: true
		},
		payfail: {
			type: Function,
			require: true
		},
		
	},
	data() {
		return {
		}
	},
	// 监听 - 页面每次【加载时】执行(如：前进)
	onLoad() {

	},
	// 监听 - 页面【首次渲染完成时】执行。注意如果渲染速度快，会在页面进入动画完成前触发
	onReady(){},
	// 监听 - 页面每次【显示时】执行(如：前进和返回) (页面每次出现在屏幕上都触发，包括从下级页面点返回露出当前页面)
	onShow() {},
	// 监听 - 页面每次【隐藏时】执行(如：返回)
	onHide() {},
	// 函数
	methods: {
		createOrder(e){
			//统一下单
			if(e.provider=='wxpay')
			{
				// #ifdef MP-WEIXIN
					this.options.ispay = 'wx_mini_pay';
				// #endif
				// #ifdef H5
					var ua = window.navigator.userAgent.toLowerCase();
					if (ua.match(/MicroMessenger/i) == 'micromessenger') { 
						this.options.ispay = 'wx_mp_pay';
					}else{
						this.options.ispay = 'wx_wap_pay';
					}
				// #endif
				// #ifdef APP
					this.options.ispay = 'wx_app_pay';
				// #endif
				let that = this;

				util.request(this.orderurl, {
					options: this.options
				}, 'POST').then(function (res) {
					//console.log(res);
					if(res.code==200)
					{
						let orderdata = res.data;
						orderdata.success = function (res) {
							that.paysuccess(res);
						};
						orderdata.fail = function (err) {
							that.payfail(err);
						};
						uni.requestPayment(orderdata);
					}else{
						uni.showToast({
							title: "出现错误",
							icon: 'none'
						})
					}
				});
				
				
			}else if(e.provider=='alipay'){
				
			}

		}
		
	},
	// 监听器
	watch:{
	},
	// 计算属性
	computed:{

	}
}
</script>
<style lang="scss" scoped>
	
.uni-pay {
	--bgcolor: #f3f3f3;
}

/* 手机版收银台弹窗开始 */
.mobile-pay-popup {
	min-height: 70vh;
	background-color: var(--bgcolor);
	border-radius: 30rpx 30rpx 0 0;
	overflow: hidden;

	.mobile-pay-popup-title {
		background-color: #ffffff;
		text-align: center;
		font-weight: bold;
		font-size: 40rpx;
		padding: 20rpx;
	}

	.mobile-pay-popup-amount-box {
		background-color: #ffffff;
		padding: 30rpx;

		.mobile-pay-popup-amount {
			color: #e43d33;
			font-size: 60rpx;
			margin-top: 20rpx;
		}
	}

	.mobile-pay-popup-provider-list {
		background-color: #ffffff;
		margin-top: 20rpx;
	}
}
</style>