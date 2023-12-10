<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
	    				<el-form-item label="主键id" prop="id">
					<el-input v-model="dataForm.id" placeholder="主键id"></el-input>
				</el-form-item>
				<el-form-item label="计划名称" prop="planName">
					<el-input v-model="dataForm.planName" placeholder="计划名称"></el-input>
				</el-form-item>
				<el-form-item label="计划简介" prop="planDesc">
					<el-input v-model="dataForm.planDesc" placeholder="计划简介"></el-input>
				</el-form-item>
				<el-form-item label="计划开始时间" prop="planStartTime">
					<el-input v-model="dataForm.planStartTime" placeholder="计划开始时间"></el-input>
				</el-form-item>
				<el-form-item label="计划结束时间" prop="planEndTime">
					<el-input v-model="dataForm.planEndTime" placeholder="计划结束时间"></el-input>
				</el-form-item>
				<el-form-item label="目的地" prop="destination">
					<el-input v-model="dataForm.destination" placeholder="目的地"></el-input>
				</el-form-item>
				<el-form-item label="背景图片" prop="bgImage">
					<el-input v-model="dataForm.bgImage" placeholder="背景图片"></el-input>
				</el-form-item>
				<el-form-item label="类别" prop="category">
					<el-input v-model="dataForm.category" placeholder="类别"></el-input>
				</el-form-item>
				<el-form-item label="状态" prop="state">
					<el-input v-model="dataForm.state" placeholder="状态"></el-input>
				</el-form-item>
				<el-form-item label="显示顺序" prop="displayIndex">
					<el-input v-model="dataForm.displayIndex" placeholder="显示顺序"></el-input>
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
import { usePlanMainApi, usePlanMainSubmitApi } from '@/api/travel/planMain'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	planName: '',
	planDesc: '',
	planStartTime: '',
	planEndTime: '',
	destination: '',
	bgImage: '',
	category: '',
	state: '',
	creator: '',
	createTime: '',
	updater: '',
	updateTime: '',
	tenantId: '',
	version: '',
	deleted: '',
	displayIndex: ''})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	if (id) {
		getPlanMain(id)
	}
}

const getPlanMain = (id: number) => {
	usePlanMainApi(id).then(res => {
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

		usePlanMainSubmitApi(dataForm).then(() => {
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
