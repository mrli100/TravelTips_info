<template>
  <view class="section_1 flex-col">
	<view class="block_1 flex-col"></view>
	<view v-if="data.is_auth">
		<view class="text-wrapper_1">
		  <text class="text_3">
			{{data.info}}
		  </text>
		</view>

		<button class="button_1 flex-col" @click="goPage('userauth/index')">
		  <text class="text_6">重新认证</text>
		</button>
		<view class="auth" style="margin-top: 30px;">
			<view class="auth-title">认证类型：</view>
			<view class="auth-value">{{data.type_name}}</view>
		</view>
		<view v-if="!data.type_gr" class="auth">
			<view class="auth-title">机构名称：</view>
			<view class="auth-value">{{data.entname}}</view>
		</view>
		<view v-if="!data.type_gr" class="auth">
			<view class="auth-title">机构代码：</view>
			<view class="auth-value">{{data.orgcade}}</view>
		</view>
		<view class="auth">
			<view class="auth-title">姓名：</view>
			<view class="auth-value">{{data.name}}</view>
		</view>
		<view class="auth">
			<view class="auth-title">身份证号：</view>
			<view class="auth-value">{{data.cade}}</view>
		</view>
		<view class="auth">
			<view class="auth-title">手机号：</view>
			<view class="auth-value">{{data.phone}}</view>
		</view>
		<view class="auth">
			<view class="auth-title">认证时间：</view>
			<view class="auth-value">{{data.auth_time}}</view>
		</view>	
	</view>	
	<view v-else>
		<view class="text-wrapper_1">
		  <text class="text_3">
			此账号的还没有进过认证。
		  </text>
		</view>

		<button class="button_1 flex-col" @click="goPage('userauth/index')">
		  <text class="text_6">立即认证</text>
		</button>
	</view>	
  </view>
</template>

<script>
const api = require('@/config/api');
const util = require('@/utils/util');
export default {
  data() {
    return {
		data: {},
	};
  },
  async onLoad() {
	let that = this;
	util.request(api.appCenterApi+'/userauth/get_auth_info').then(function(res) {
		console.log(res.data);
		that.data = res.data;
	})
  	await this.$onLaunched;

  },
  methods: {
    goPage(url){
      uni.navigateTo({
            url: '/pages/' + url
          })
    }
  },
};

</script>

<style>

.section_1 {
  position: relative;
  margin: 0 20px;
}

.block_1 {
  background-size: 263px;
  background-repeat: no-repeat;
  background-image: url(/static/img/setting/101.png);
  background-position-x: center;
  height: 150px;
  margin: 20px 0 0 0;
}
.auth{
  width: 100%;
  float: left;
  margin: 10px 0;
  font-size: 15px;
  color: rgba(103, 114, 141, 1);
}
.auth-title{
  width: 80px;
  float: left;
  text-align: right;
  color: #111111;
}
.auth-value{
  font-size: 14px;
}
.text_2 {
  overflow-wrap: break-word;
  color: rgba(58, 64, 85, 1);
  font-size: 24px;
  font-family: Source Han Sans-Bold;
  font-weight: 700;
  text-align: left;
  margin: 32px 0 0 0;
}

.text-wrapper_1 {
  overflow-wrap: break-word;
  font-size: 0;
  font-family: Source Han Sans-ExtraLight;
  font-weight: 250;
  text-align: left;
  line-height: 24px;
}

.text_3 {

  overflow-wrap: break-word;
  color: rgba(58, 64, 85, 1);
  font-size: 16px;
  font-family: Source Han Sans-ExtraLight;
  font-weight: 250;
  text-align: left;
  line-height: 24px;
}
.button_1 {
  box-shadow: 0px 4px 4px 0px rgba(224, 230, 243, 1);
  background-color: rgba(26, 26, 26, 1);
  border-radius: 5px;
  height: 47px;
  width: 275px;
  margin: 10px auto;
}

.text_6 {
  width: 300px;
  height: 27px;
  overflow-wrap: break-word;
  color: rgba(255, 255, 255, 1);
  font-size: 18px;
  font-family: Source Han Sans-Bold;
  font-weight: 700;
  text-align: center;
  white-space: nowrap;
  line-height: 27px;
  margin: 10px 0 0 -12px;
}

</style>