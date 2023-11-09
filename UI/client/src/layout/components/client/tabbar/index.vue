<template>
	<van-tabbar v-model="active" route>
		<van-tabbar-item replace to="/client" icon="wap-home-o">首页</van-tabbar-item>
		<van-tabbar-item replace to="/team" icon="friends-o">组队</van-tabbar-item>
		<van-tabbar-item replace to="/travelrouter" icon="location-o">攻略</van-tabbar-item>
		<van-tabbar-item icon="chat-o">消息</van-tabbar-item>
		<van-tabbar-item replace to="/user" icon="manager-o">我的</van-tabbar-item>
	</van-tabbar>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { showNotify } from 'vant'

const onChange = (tab: any) => {
	// showNotify({ message: '提示' })
}

const active = ref(0)
</script>
