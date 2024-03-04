<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
	    				<el-form-item label="用户id" prop="userId">
					<el-input v-model="dataForm.userId" placeholder="用户id"></el-input>
				</el-form-item>
				<el-form-item label="签名" prop="signature">
					<el-input v-model="dataForm.signature" placeholder="签名"></el-input>
				</el-form-item>
				<el-form-item label="标签" prop="tags">
					<el-input v-model="dataForm.tags" placeholder="标签"></el-input>
				</el-form-item>
				<el-form-item label="扩展配置" prop="extendedconfig">
					<el-input v-model="dataForm.extendedconfig" placeholder="扩展配置"></el-input>
				</el-form-item>
				<el-form-item label="出生年月" prop="birthday">
					<el-date-picker type="date" placeholder="出生年月" v-model="dataForm.birthday"></el-date-picker>
				</el-form-item>
				<el-form-item label="职业" prop="career">
					<el-input v-model="dataForm.career" placeholder="职业"></el-input>
				</el-form-item>
				<el-form-item label="微信openid" prop="weixinOpenid">
					<el-input v-model="dataForm.weixinOpenid" placeholder="微信openid"></el-input>
				</el-form-item>
				<el-form-item label="身份证" prop="idcard">
					<el-input v-model="dataForm.idcard" placeholder="身份证"></el-input>
				</el-form-item>
				<el-form-item label="登录地址" prop="addressIp">
					<el-input v-model="dataForm.addressIp" placeholder="登录地址"></el-input>
				</el-form-item>
				<el-form-item label="纬度" prop="addressLat">
					<el-input v-model="dataForm.addressLat" placeholder="纬度"></el-input>
				</el-form-item>
				<el-form-item label="经度" prop="addressLng">
					<el-input v-model="dataForm.addressLng" placeholder="经度"></el-input>
				</el-form-item>
				<el-form-item label="年龄" prop="age">
					<el-input v-model="dataForm.age" placeholder="年龄"></el-input>
				</el-form-item>
		</el-form>
		<template #footer>
			<el-button @click="visible = false">取消</el-button>
			<el-button type="primary" @click="submitHandle()">确定</el-button>
		</template>
	</el-dialog>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus/es'
import { useSysUserInfoApi, useSysUserInfoSubmitApi } from '@/api/travel/sysUserInfo'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	userId: '',
	signature: '',
	tags: '',
	tenantId: '',
	extendedconfig: '',
	version: '',
	birthday: '',
	career: '',
	deleted: '',
	weixinOpenid: '',
	idcard: '',
	addressIp: '',
	creator: '',
	addressLat: '',
	createTime: '',
	addressLng: '',
	age: '',
	updater: '',
	updateTime: ''})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	if (id) {
		getSysUserInfo(id)
	}
}

const getSysUserInfo = (id: number) => {
	useSysUserInfoApi(id).then(res => {
		Object.assign(dataForm, res.data)
	})
}

const dataRules = ref({
})

// 表单提交
const submitHandle = () => {
	dataFormRef.value.validate((valid: boolean) => {
		if (!valid) {
			return false
		}

		useSysUserInfoSubmitApi(dataForm).then(() => {
			ElMessage.success({
				message: '操作成功',
				duration: 500,
				onClose: () => {
					visible.value = false
					emit('refreshDataList')
				}
			})
		})
	})
}

defineExpose({
	init
})
</script>
