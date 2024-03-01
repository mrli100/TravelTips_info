<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
	    				<el-form-item label="应用标识" prop="type">
					<el-input v-model="dataForm.type" placeholder="应用标识"></el-input>
				</el-form-item>
				<el-form-item label="配置名称" prop="name">
					<el-input v-model="dataForm.name" placeholder="配置名称"></el-input>
				</el-form-item>
				<el-form-item label="标识" prop="keyname">
					<el-input v-model="dataForm.keyname" placeholder="标识"></el-input>
				</el-form-item>
				<el-form-item label="值" prop="value">
					<el-input v-model="dataForm.value" placeholder="值"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="status">
					<el-input v-model="dataForm.status" placeholder="状态"></el-input>
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
import { useT_app_configApi, useT_app_configSubmitApi } from '@/api/travel/t_app_config'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	type: '',
	name: '',
	keyname: '',
	value: '',
	status: '',
	tenantId: '',
	version: '',
	deleted: '',
	creator: '',
	createTime: '',
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
		getT_app_config(id)
	}
}

const getT_app_config = (id: number) => {
	useT_app_configApi(id).then(res => {
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

		useT_app_configSubmitApi(dataForm).then(() => {
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
