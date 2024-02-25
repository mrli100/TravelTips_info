<template>
  <view class="authentication">
    <view class="title">
      <text>专项特权</text>
      <view></view>
    </view>
    <view class="list">
      <view class="item">
        <view class="yell"></view>
        <text>专属标识</text>
      </view>
      <view class="item" v-if="config.app.userauth.is_auth_1">
        <view class="yell"></view>
        <view class="text">
          <text v-if="config.app.userauth.auth_1_w_1">{{ config.app.userauth.auth_1_w_1 }}</text>
          <text v-if="config.app.userauth.auth_1_w_2">{{ config.app.userauth.auth_1_w_2 }}</text>
		  <text v-if="config.app.userauth.auth_1_w_3">{{ config.app.userauth.auth_1_w_3 }}</text>
        </view>
      </view>
      <view class="item" v-if="config.app.userauth.is_auth_2">
        <view class="yell"></view>
        <view class="text">
          <text v-if="config.app.userauth.auth_2_w_1">{{ config.app.userauth.auth_2_w_1 }}</text>
          <text v-if="config.app.userauth.auth_2_w_2">{{ config.app.userauth.auth_2_w_2 }}</text>
		  <text v-if="config.app.userauth.auth_2_w_3">{{ config.app.userauth.auth_2_w_3 }}</text>
        </view>
      </view>
      <view class="item" v-if="config.app.userauth.is_auth_3">
        <view class="yell"></view>
        <view class="text">
          <text v-if="config.app.userauth.auth_3_w_1">{{ config.app.userauth.auth_3_w_1 }}</text>
          <text v-if="config.app.userauth.auth_3_w_2">{{ config.app.userauth.auth_3_w_2 }}</text>
		  <text v-if="config.app.userauth.auth_3_w_3">{{ config.app.userauth.auth_3_w_3 }}</text>
        </view>
      </view>
    </view>
    <view class="tip">
      <text v-if="config.app.userauth.auth_tip_1">{{ config.app.userauth.auth_tip_1 }}</text>
	  <text v-if="config.app.userauth.auth_tip_2">{{ config.app.userauth.auth_tip_2 }}</text>
    </view>
    <view class="button">
      <text v-if="config.app.userauth.is_auth_1" @click="goPage('userauth/info?type=1')">{{ config.app.userauth.auth_1_c }}</text>
      <text v-if="config.app.userauth.is_auth_2" @click="goPage('userauth/info?type=2')">{{ config.app.userauth.auth_2_c }}</text>
	  <text v-if="config.app.userauth.is_auth_3" @click="goPage('userauth/info?type=3')">{{ config.app.userauth.auth_3_c }}</text>
    </view>
  </view>
</template>

<script>
const api = require('@/config/api');
const util = require('@/utils/util');
export default {
  data() {
    return {
		config: {},
	};
  },
  async onLoad() {
	let that = this;
	util.request(api.configUrl).then(function(res) {
		uni.setStorageSync('config', res.data);
		//console.log(res.data);
		that.config = uni.getStorageSync('config');
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

<style lang="scss" scoped>
.authentication {
  width: 100%;
  background: 100% 100% url('../../static/img/shop/authentication.png');
  margin-top: -70px;
  background-size: cover;
  display: flex;
  flex-direction: column;
  align-items: center;
  .title {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding-top: 480rpx;
    > text {
      font-size: 32rpx;
      font-family: PingFangSC-Semibold, PingFang SC;
      font-weight: 600;
      color: #333333;
      line-height: 44rpx;
    }
    > view {
      width: 94rpx;
      height: 8rpx;
      background: #ff9e00;
      border-radius: 6rpx;
    }
  }
  .list {
    margin-top: 38rpx;
    .item {
      display: flex;
      margin-bottom: 38rpx;
      .yell {
        width: 25rpx;
        height: 25rpx;
        background: #ff9500;
        margin-right: 28rpx;
        transform: rotate(45deg);
      }
      > text {
        font-size: 28rpx;
        font-family: PingFangSC-Regular, PingFang SC;
        font-weight: 400;
        color: #333333;
      }
      .text {
        display: flex;
        flex-direction: column;
        > text {
          font-size: 28rpx;
          font-family: PingFangSC-Regular, PingFang SC;
          font-weight: 400;
          color: #333333;
          &:nth-child(2) {
            margin-top: 12rpx;
          }
        }
      }
    }
  }
  .tip {
    display: flex;
    align-items: center;
    flex-direction: column;
    > text {
      font-size: 24rpx;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #999999;
      line-height: 34rpx;
    }
  }
  .button {
    display: flex;
    align-items: center;
    flex-direction: column;
    text-align: center;
    margin-top: 24rpx;
    > text {
      width: 494rpx;
      height: 76rpx;
      background: #fff5e5;
      border-radius: 38rpx;
      margin-bottom: 30rpx;
      font-size: 28rpx;
      font-family: PingFangSC-Regular, PingFang SC;
      font-weight: 400;
      color: #333333;
      line-height: 76rpx;
    }
  }
}
</style>
