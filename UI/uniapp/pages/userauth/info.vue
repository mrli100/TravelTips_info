<template>
	<view class="YmContent">
		<view class="auth_1">
			<view class="auth_41">
				<text class="auth_2">*请提交以下信息进行认证</text>
			</view>
			<view class="auth_42">
				<view v-if="!config.app.userauth['auth_'+options.type+'_gr']" class="auth_7">
					<text class="auth_8">组织名称</text>
					<input v-model="dataFrom.entname" :placeholder="'请输入组织名称'" class="full_name" />
				</view>
				<view v-if="!config.app.userauth['auth_'+options.type+'_gr']" class="auth_10">
				</view>
				<view v-if="!config.app.userauth['auth_'+options.type+'_gr']" class="auth_7">
					<text class="auth_8">组织机构代码</text>
					<input v-model="dataFrom.orgcade" :placeholder="'请输入组织机构代码'" class="full_name" />
				</view>
				<view v-if="!config.app.userauth['auth_'+options.type+'_gr']" class="auth_10">
				</view>
				<view class="auth_7">
					<text class="auth_8">姓名</text>
					<input v-model="dataFrom.name" placeholder="请输入姓名" class="full_name" />
				</view>
				<view class="auth_10">
				</view>
				<view class="auth_7">
					<text class="auth_8">身份证号码</text>
					<input v-model="dataFrom.cade" placeholder="请输入身份证号码" class="full_name" />
				</view>
				<view class="auth_10">
				</view>
				<view class="auth_7">
					<text class="auth_8">手机号码</text>
					<input v-model="dataFrom.phone" placeholder="请输入手机号码" class="full_name" />
				</view>
				<view class="auth_10">
				</view>
			</view>
			<view class="auth_43">
				<view class="auth_27">
					<text class="auth_28">本人声明</text>
				</view>
				<radio-group v-model="dataFrom.sta"  @change="radioChange" class="type">
					<radio value="1" class="auth_31" />
					<text class="auth_32">以上信息真实有效，代表真实意思表达</text>
				</radio-group>
			</view>
			<button v-on:click="btSubmit()" class="btSubmit">下一步</button>
		</view>
		<uni-popup ref="pay_popup" type="bottom">
			<pay :options="options" :orderurl="orderurl" :title="pay_title" :paysuccess="pay_success" :payfail="pay_fail"/>
		</uni-popup>
	</view>
</template>

<script>
const api = require('@/config/api');
const util = require('@/utils/util');
import pay from '@/components/pay/pay.vue';
export default {
  components: {
	pay
  },
  data() {
    return {
		pay_title: '待支付金额',
		orderurl: api.appCenterApi+'/userauth/order',
		stopurl: '',
		config: {},
		dataFrom: {
			entname: '',
			orgcade: '',
			name: '',
			cade: '',
			phone: '',
			sta: ''
		},
		options: {}
	};
  },
  async onLoad(option) {
	let that = this;
	that.config = uni.getStorageSync('config');
	await this.$onLaunched;
	if (option.type) {
		uni.setNavigationBarTitle({
			title: that.config.app.userauth['auth_'+option.type+'_c']
		});
		that.options = {
			type: option.type,// 1
			info: this.dataFrom,
			total_fee: that.config.app.userauth['auth_'+option.type+'_f']
		}
	} else {
		uni.showToast({
			title: "出现错误",
			icon: 'none'
		})
	}
  },
  methods: {
	  //支付成功后回调
	pay_success(res){
		//console.log(res);
		if(res.errMsg=="requestPayment:ok"){//支付成功时，要传入订单号到后台改变订单状态
			uni.showToast({
				title: "支付完成",
				icon: 'none'
			})
			uni.switchTab({
				url: '/pages/tabbar/center'
			})

			
		}
	},
	//支付失败后回调
	pay_fail(res){
		console.log(res);
	},
	radioChange: function(evt) {
		this.dataFrom.statement=evt.detail.value
	},
	
    btSubmit(){

		if(!this.config.app.userauth['auth_'+this.options.type+'_gr'])
		{
			if (this.dataFrom.entname==null) {
				uni.showToast({
					title: "请输入组织名称",
					icon: 'none'
				})
				return;
			}
			if (this.dataFrom.orgcade==null) {
				uni.showToast({
					title: "请输入组织机构代码",
					icon: 'none'
				})
				return;
			}
		}
		if (this.dataFrom.name==null) {
			uni.showToast({
				title: "请输入名字",
				icon: 'none'
			})
			return;
		}
		var idCardMsg = util.identityIDCard(this.dataFrom.cade);
		if (!idCardMsg.isPass) {
			uni.showToast({
				title: idCardMsg.errorMsg,
				icon: 'none'
			})
			return;
		}
		var phone = /^1[3456789]\d{9}$/;
		if (!phone.test(this.dataFrom.phone)) {
			uni.showToast({
				title: "手机号格式错误",
				icon: 'none'
			})
			return;
		}
		if (this.dataFrom.sta==null) {
			uni.showToast({
				title: "请勾选申明",
				icon: 'none'
			})
			return;
		}
		this.$refs.pay_popup.open('bottom');
    }
  },
};


</script>

<style lang="scss" scoped>
html,body,#app {height: 100%;width: 100%;}
.YmContent {height: 100%;width: 100%;}
uni-page-body,#app {height: 100%;}
button::after {border: none;width: auto;}
input{outline: none;border: none;list-style: none;width: auto;}
.list_item{float: left;}
.ym_hide{display: none;}
.ym_show{display: block;}
.slide-image {  width: 100%;  height:100%;}
.list_horizontal {width: auto; display:inline-block; }
 .auth_1{white-space:normal;padding:0upx;clear:both;float:left;background-color:#ffffff; border-radius:0upx;font-size:24upx;}
 .auth_1 .auth_41{white-space:normal;width:100%;height:76upx;padding:0upx;clear:both;margin-top:0upx;margin-left:0upx;float:left;background-color:#f7f7f7; border-bottom-color:#e1e1e1; border-bottom-width:1upx; border-bottom-style:solid; border-radius:0upx;font-size:24upx;}
 .auth_1 .auth_41 .auth_2{white-space:normal;width:645upx;height:25upx;padding:0upx;margin-top:22upx;margin-left:36upx;float:left; border-radius:0upx; color:#6464c8;font-size:22upx; line-height:25upx;}
 .auth_1 .auth_42{white-space:normal;padding:0upx;clear:both;margin-top:0upx;margin-left:0upx;float:left; border-radius:0upx;font-size:24upx;}
 .auth_1 .auth_42 .auth_7{white-space:normal;width:100%;height:28upx;padding:0upx;clear:both;margin-top:29upx;margin-left:37upx;float:left; border-radius:0upx;font-size:24upx; line-height:28upx;}
 .auth_1 .auth_42 .auth_7 .auth_8{white-space:normal;width:152upx;height:28upx;padding:0upx;margin-top:0upx;margin-left:0upx;float:left; border-radius:0upx; color:#646464;font-size:24upx; line-height:28upx;}
 .auth_1 .auth_42 .auth_7 .full_name{white-space:normal;width:462upx;height:28upx;min-height:23upx;padding:0upx;margin-top:0upx;margin-left:15upx;float:left; border-radius:0upx; color:#c8c8c8;font-size:24upx; line-height:28upx;}
 .auth_1 .auth_42 .auth_10{white-space:normal;width:710upx;height:2upx;padding:0upx;clear:both;margin-top:34upx;margin-left:38upx;float:left; border-radius:0upx;font-size:24upx; line-height:2upx;}
 .auth_1 .auth_43{white-space:normal;width:100%;height:147upx;padding:0upx;clear:both;margin-top:0upx;margin-left:0upx;float:left;background-color:#eeeff4; border-radius:0upx;font-size:24upx;}
 .auth_1 .auth_43 .auth_27{white-space:normal;width:138upx;height:31upx;padding:0upx;clear:both;margin-top:27upx;margin-left:28upx;float:left; border-radius:0upx;font-size:24upx; line-height:31upx;}
 .auth_1 .auth_43 .auth_27 .auth_28{white-space:normal;width:109upx;height:24upx;padding:0upx;margin-top:6upx;margin-left:0upx;float:left; border-radius:0upx; color:#6464c8;font-size:23upx; line-height:24upx;}
 .auth_1 .auth_43 .auth_27 .auth_29{white-space:normal;width:28upx;height:28upx;padding:0upx;margin-top:0upx;margin-left:0upx;float:left; border-radius:0upx;font-size:24upx; line-height:28upx;}
 .auth_1 .auth_43 .type{white-space:normal;height:54upx;padding:0upx;clear:both;margin-top:18upx;margin-left:29upx;float:left; border-radius:0upx;font-size:24upx; line-height:54upx;}
 .auth_1 .auth_43 .type .auth_31{white-space:normal;width:auto;height:48upx;padding:0upx;margin-top:4upx;margin-left:0upx;float:left; border-radius:0upx;font-size:24upx; line-height:48upx;}
 .auth_1 .auth_43 .type .auth_32{white-space:normal;width:auto;height:27upx;padding:0upx;margin-top:14upx;margin-left:15upx;float:left; border-radius:0upx; color:#646464;font-size:24upx; line-height:27upx;}
 .auth_1 .auth_43 .type .auth_33{white-space:normal;width:auto;height:51upx;padding:0upx;margin-top:2upx;margin-left:42upx;float:left; border-radius:0upx;font-size:24upx; line-height:51upx;}
 .auth_1 .auth_43 .type .auth_34{white-space:normal;width:auto;height:27upx;padding:0upx;margin-top:14upx;margin-left:14upx;float:left; border-radius:0upx; color:#646464;font-size:24upx; line-height:27upx;}
 .auth_1 .auth_35{white-space:normal;width:518upx;height:54upx;padding:0upx;clear:both;margin-top:15upx;margin-left:46upx;float:left; border-radius:0upx;font-size:24upx; line-height:54upx;}
 .auth_1 .auth_35 .auth_36{white-space:normal;width:auto;height:35upx;padding:0upx;margin-top:9upx;margin-left:0upx;float:left; border-radius:0upx;font-size:24upx; line-height:35upx;}
 .auth_1 .auth_35 .auth_37{white-space:normal;width:auto;height:24upx;padding:0upx;margin-top:19upx;margin-left:5upx;float:left; border-radius:0upx; color:#6464c8;font-size:24upx; line-height:24upx;}
 .auth_1 .auth_35 .auth_38{white-space:normal;width:auto;height:26upx;padding:0upx;margin-top:19upx;margin-left:15upx;float:left; border-radius:0upx; color:#3e96da;font-size:22upx; line-height:26upx;}
 .auth_1 .btSubmit{white-space:normal;width:674upx;height:84upx;padding:0upx;clear:both;margin-top:84upx;margin-left:37upx;float:left;background-color:#f39360;text-align:center;justify-content:center; border-radius:30upx; cursor:pointer; color:#ffffff;font-size:24upx; line-height:84upx;}
 .auth_1 .auth_40{white-space:normal;width:231upx;height:26upx;padding:0upx;clear:both;margin-top:615upx;margin-left:261upx;float:left; border-radius:0upx;font-size:24upx; line-height:26upx;}

</style>