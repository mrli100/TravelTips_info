<template>
	<van-row class="user-settings">
		<van-col span="24" class="settings">
			<van-icon name="setting-o" size="1.6rem" />
		</van-col>
	</van-row>
	<van-row class="user-info">
		<van-col span="24" class="avatar">
			<van-image round width="6rem" height="6rem" :src="userStore.user.avatar" />
		</van-col>
		<van-col span="24" class="info">
			<van-col span="24" class="introduce">
				<span>{{ userStore.user.username }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
				<span>ID: {{ userStore.user.id }}</span>
			</van-col>
			<van-col span="24" class="introduce">
				<span>简介：我去旅行是因为我决定了要去，并不是因为对风景的兴趣。</span>
			</van-col>
			<van-col span="24" class="introduce">
				<var-badge class="label" type="info" value="旅游达人" />
				<var-badge class="label" type="info" value="科技博主" />
			</van-col>
		</van-col>
	</van-row>

	<van-divider />
	<van-row class="user-link">
		<van-col span="8">
			<van-row>
				<van-col span="24">
					<span>12</span>
				</van-col>
				<van-col span="24">
					<span>关注</span>
				</van-col>
			</van-row>
		</van-col>
		<van-col span="8">
			<van-row>
				<van-col span="24">
					<span>1288</span>
				</van-col>
				<van-col span="24">
					<span>粉丝</span>
				</van-col>
			</van-row>
		</van-col>
		<van-col span="8">
			<van-row>
				<van-col span="24">
					<span>6665</span>
				</van-col>
				<van-col span="24">
					<span>获赞和收藏</span>
				</van-col>
			</van-row>
		</van-col>
	</van-row>
	<van-divider />
	<van-cell-group class="user-group" inset>
		<van-cell title="我的点赞" icon="records" is-link to="/OrderList" /></van-cell-group>
	<van-cell-group inset>
		<van-cell title="我的行程" icon="exchange" is-link />
		<van-cell title="我的路线" icon="gold-coin" is-link />
		<van-cell title="退出登录" icon="gold-coin" is-link />
	</van-cell-group>
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
</template>

<script  setup lang="ts">
import tabbar from "@/views/tabbar/index.vue";
import { useUserStore } from '@/store/modules/user'
const userStore = useUserStore()

console.log(JSON.stringify(userStore), userStore)

const logout = () => {
	userStore.logoutAction().then(() => {
		// router.push({ path: '/home' })

		// 刷新页面
		location.reload()
	})
}
</script>

<style lang="less" scoped>
.user-info {
	.avatar {
		text-align: center;
		margin-top: 1px;
	}

	.info {
		padding-left: 10px;
		text-align: center;

		.introduce {
			margin-top: 0.5rem;

			.var--box {
				margin-right: 0.4rem;
			}
		}
	}

}


.user-link {
	text-align: center;
}

.user-settings {
	text-align: right;
	margin: 0.5rem;
}
</style>
