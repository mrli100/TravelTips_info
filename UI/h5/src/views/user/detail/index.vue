<template>
	<van-row>
		<van-col span="24">
			<van-nav-bar title="用户信息修改" left-text="返回" left-arrow @click-left="onClickLeft" />
		</van-col>
	</van-row>
	<van-form @submit="onSubmit">
		<van-cell-group title="用户信息">
			<van-cell title="头像">
				<template #right-icon>
					<van-uploader preview-full-image name="avatar" v-model="updateParm.avatar" :after-read="afterRead"
						:max-count="1" />
				</template>
			</van-cell>
			<van-cell title="昵称" is-link @click="in_realName_show = true" :value="userStore.user.realName" />
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
			<van-cell title="修改密码" is-link />
			<van-cell title="退出登录" @click="loginOut" />
		</van-cell-group>
		<van-cell-group inset>
			<!-- 			<van-button round block type="primary" native-type="submit">
				保存
			</van-button> -->
		</van-cell-group>
	</van-form>
	<!-- 輸入昵稱 -->
	<van-dialog @confirm="in_realName_confirm" v-model:show="in_realName_show" title="请输入昵称" show-cancel-button>
		<van-field v-model="in_realName" placeholder="请输入昵称" />
	</van-dialog>
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
import { updateSysUserApi } from '@/api/travel/sysUserInfo'
import { attachUpload } from '@/api/sys/attachment'
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
	//单个上传
	attachUpload(file).then((datas: any) => {
		if (datas.code == 0) {
			let dataFrom: any = {}
			dataFrom.realName = in_realName.value
			dataFrom.username = userStore.user.username
			dataFrom.mobile = userStore.user.mobile
			dataFrom.orgId = userStore.user.orgId
			dataFrom.avatar = datas.data.url
			// "realName": in_realName.value, "mobile": userStore.user.mobile, "username": userStore.user.username, "orgId": userStore.user.orgId
			updateSysUserApi(dataFrom).then(() => {
				console.log("修改成功")
			})
			// fileMapVal.value.set(file.objectUrl, datas.data.url);
		}
	})
};
//**编辑输入框内容 */
const in_realName_show = ref(false)
const in_realName = ref(userStore.user.realName)
const in_realName_confirm = () => {
	console.log(userStore.user)
	let dataFrom: any = {}
	dataFrom.realName = in_realName.value
	dataFrom.username = userStore.user.username
	dataFrom.mobile = userStore.user.mobile
	dataFrom.orgId = userStore.user.orgId
	// "realName": in_realName.value, "mobile": userStore.user.mobile, "username": userStore.user.username, "orgId": userStore.user.orgId
	updateSysUserApi(dataFrom).then(() => {
		console.log("修改成功")
	})

	console.log('in_realName_confirm', in_realName.value)
}


//** 退出登录 */
const loginOut = () => {
	userStore.logoutAction().then(() => {
		// 刷新页面
		location.reload()
	})
}
</script>

<style lang="less" scoped></style>
