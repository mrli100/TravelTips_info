<template>
	<view class="container" :style="{height:screenHeight-statusBarHeight+'px',marginTop:statusBarHeight+'px'}">
		<view class="box df">
			<image :src="insetUrl+'18.png'"></image>
			<view class="title">账号封禁</view>
			<view class="doc">
				当前账号已被封禁，如需要查看封禁原因或申解封请联系客服为您处理。
			</view>
			<button class="btn bg1" open-type="contact">联系客服</button>
			<navigator class="btn bg2" open-type="exit" target="miniProgram">退出系统</navigator>
		</view>
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
				screenHeight: app.globalData.screenHeight,
				statusBarHeight: app.globalData.statusBarHeight,
			}
		},
		onLoad(options) {},
		methods: {}
	}
</script>

<style>
	@import url("/static/css/setting/err.css");
</style>