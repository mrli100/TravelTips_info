<template>
	<van-form @submit="onSubmit">
		<van-cell-group inset>
			<van-field v-model="loginForm.username" name="用户名" label="用户名" placeholder="用户名"
				:rules="[{ required: true, message: '请填写用户名' }]" />
			<van-field v-model="loginForm.password" type="password" name="密码" label="密码" placeholder="密码"
				:rules="[{ required: true, message: '请填写密码' }]" />
		</van-cell-group>
		<div style="margin: 16px;">
			<van-button round block type="primary" native-type="submit">
				提交
			</van-button>
		</div>
	</van-form>


	<!-- 	<el-form ref="loginFormRef" :model="loginForm" :rules="loginRules" @keyup.enter="onLogin">
		<div class="login-title">登录</div>
		<el-form-item prop="username">
			<el-input v-model="loginForm.username" :prefix-icon="User" :placeholder="'用户名'"></el-input>
		</el-form-item>
		<el-form-item prop="password">
			<el-input v-model="loginForm.password" :prefix-icon="Lock" show-password :placeholder="'密码'"></el-input>
		</el-form-item>
		<el-form-item v-if="captchaVisible" prop="captcha" class="login-captcha">
			<el-input v-model="loginForm.captcha" :placeholder="'验证码'" :prefix-icon="Key"></el-input>
			<img :src="captchaBase64" @click="onCaptcha" />
		</el-form-item>
		<el-form-item class="login-button">
			<el-button type="primary" @click="onLogin()">登录</el-button>
		</el-form-item>
	</el-form> -->
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useUserStore } from '@/store/modules/user'
import { useCaptchaApi, useCaptchaEnabledApi } from '@/api/auth'
import { useRouter } from 'vue-router'
import constant from '@/utils/constant'

const userStore = useUserStore()
const router = useRouter()
const loginFormRef = ref()
const captchaBase64 = ref()

//** vant的登录 */
const loginForm = reactive({
	username: constant.env.PROD ? '' : 'mrlib100',
	password: constant.env.PROD ? '' : 'admin@123',
	key: '',
	captcha: ''
})
const onSubmit = (values: any) => {
	console.log('submit', values);
	// 用户登录
	userStore
		.accountLoginAction(loginForm)
		.then(() => {
			console.log('登录成功')
			router.push({ path: '/' })
		})
		.catch(() => {
			if (captchaVisible.value) {
				onCaptcha()
			}
		})
};


// 是否显示验证码
const captchaVisible = ref(false)

onMounted(() => {
	onCaptchaEnabled()
})

const onCaptchaEnabled = async () => {
	// const { data } = await useCaptchaEnabledApi()
	// captchaVisible.value = data

	// if (data) {
	// 	await onCaptcha()
	// }
}

const onCaptcha = async () => {
	// const { data } = await useCaptchaApi()
	// if (data.enabled) {
	// 	captchaVisible.value = true
	// }
	// loginForm.key = data.key
	// captchaBase64.value = data.image
}

</script>

<style lang="scss" scoped>
.login-title {
	display: flex;
	justify-content: center;
	margin-bottom: 35px;
	font-size: 24px;
	color: #444;
	letter-spacing: 4px;
}

.login-captcha {
	:deep(.el-input) {
		width: 200px;
	}
}

.login-captcha img {
	width: 150px;
	height: 40px;
	margin: 5px 0 0 10px;
	cursor: pointer;
}

.login-button {
	:deep(.el-button--primary) {
		margin-top: 10px;
		width: 100%;
		height: 45px;
		font-size: 18px;
		letter-spacing: 8px;
	}
}
</style>
