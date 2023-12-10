<template>
	<van-row>
		<van-col span="24">
			<van-nav-bar title="添加线路" left-text="返回" left-arrow @click-left="onClickLeft" />
		</van-col>
	</van-row>
	<van-form @submit="onSubmit">
		<van-cell-group title="用户信息">
			<van-cell title="头像">
				<template #right-icon>
					<van-uploader preview-full-image v-model="updateParm.avatar" :after-read="afterRead" :max-count="1" />
				</template>
			</van-cell>
			<van-cell title="昵称" is-link to="/OrderList" :value="userStore.user.realName" />
			<van-cell title="签名" is-link to="/OrderList" :value="userStore.user.signature" />
			<van-cell title="性别">
				<template #right-icon>
					<van-radio-group v-model="checked" direction="horizontal">
						<van-radio name="0">男性</van-radio>
						<van-radio name="1">女性</van-radio>
					</van-radio-group>
				</template>
			</van-cell>
			<van-cell title="生日" is-link value="1994-07-25" />
		</van-cell-group>
		<van-cell-group title="系统设置">
			<van-cell title="登出" is-link />
		</van-cell-group>
		<van-cell-group inset>
			<van-button round block type="primary" native-type="submit">
				保存
			</van-button>
		</van-cell-group>
	</van-form>
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
</template>
<script  setup lang="ts">
import { ref, onMounted } from 'vue'
import tabbar from "@/views/tabbar/index.vue";
import { useUserStore } from '@/store/modules/user'
const onClickLeft = () => history.back();
const userStore = useUserStore()
//**静态资源 */
const checked = ref(null)
const updateParm = ref({
	avatar: []
})

// console.log(JSON.stringify(userStore), userStore)

const logout = () => {
	userStore.logoutAction().then(() => {
		// router.push({ path: '/home' })
		// 刷新页面
		location.reload()
	})
}
const onSubmit = (values) => {
	console.log('submit', values);
};
const uploadImage = (val) => {
	console.log('uploadImage', val);
};
//上传文件代码
const afterRead = (file) => {
	console.log("afterRead", file);
};
</script>

<style lang="less" scoped></style>
