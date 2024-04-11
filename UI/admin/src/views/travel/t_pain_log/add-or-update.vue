<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
	    				<el-form-item label="主键" prop="id">
					<el-input v-model="dataForm.id" placeholder="主键"></el-input>
				</el-form-item>
				<el-form-item label="QQ号" prop="uid">
					<el-input v-model="dataForm.uid" placeholder="QQ号"></el-input>
				</el-form-item>
				<el-form-item label="访问时间" prop="times">
					<el-input v-model="dataForm.times" placeholder="访问时间"></el-input>
				</el-form-item>
				<el-form-item label="访问日期" prop="timeData">
					<el-input v-model="dataForm.timeData" placeholder="访问日期"></el-input>
				</el-form-item>
				<el-form-item label="hide_from" prop="hideFrom">
					<el-input v-model="dataForm.hideFrom" placeholder="hide_from"></el-input>
				</el-form-item>
				<el-form-item label="is_hide_visit" prop="isHideVisit">
					<el-input v-model="dataForm.isHideVisit" placeholder="is_hide_visit"></el-input>
				</el-form-item>
				<el-form-item label="QQ昵称" prop="nickName">
					<el-input v-model="dataForm.nickName" placeholder="QQ昵称"></el-input>
				</el-form-item>
				<el-form-item label="黄钻" prop="yellow">
					<el-input v-model="dataForm.yellow" placeholder="黄钻"></el-input>
				</el-form-item>
				<el-form-item label="是否VIP" prop="supervip">
					<el-input v-model="dataForm.supervip" placeholder="是否VIP"></el-input>
				</el-form-item>
				<el-form-item label="是否年费" prop="annualfee">
					<el-input v-model="dataForm.annualfee" placeholder="是否年费"></el-input>
				</el-form-item>
				<el-form-item label="访问内容" prop="photoes">
					<el-input v-model="dataForm.photoes" placeholder="访问内容"></el-input>
				</el-form-item>
				<el-form-item label="uins" prop="uins">
					<el-input v-model="dataForm.uins" placeholder="uins"></el-input>
				</el-form-item>
				<el-form-item label="创建人" prop="creator">
					<el-input v-model="dataForm.creator" placeholder="创建人"></el-input>
				</el-form-item>
				<el-form-item label="创建时间" prop="createTime">
					<el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
				</el-form-item>
				<el-form-item label="更新人" prop="updater">
					<el-input v-model="dataForm.updater" placeholder="更新人"></el-input>
				</el-form-item>
				<el-form-item label="更新时间" prop="updateTime">
					<el-input v-model="dataForm.updateTime" placeholder="更新时间"></el-input>
				</el-form-item>
				<el-form-item label="租户ID" prop="tenantId">
					<el-input v-model="dataForm.tenantId" placeholder="租户ID"></el-input>
				</el-form-item>
				<el-form-item label="版本号" prop="version">
					<el-input v-model="dataForm.version" placeholder="版本号"></el-input>
				</el-form-item>
				<el-form-item label="删除标识  0：正常   1：已删除" prop="deleted">
					<el-input v-model="dataForm.deleted" placeholder="删除标识  0：正常   1：已删除"></el-input>
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
import { useT_pain_logApi, useT_pain_logSubmitApi } from '@/api/travel/t_pain_log'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	uid: '',
	times: '',
	timeData: '',
	hideFrom: '',
	isHideVisit: '',
	nickName: '',
	yellow: '',
	supervip: '',
	annualfee: '',
	photoes: '',
	uins: '',
	creator: '',
	createTime: '',
	updater: '',
	updateTime: '',
	tenantId: '',
	version: '',
	deleted: ''})

const init = (id?: number) => {
	visible.value = true
	dataForm.id = ''

	// 重置表单数据
	if (dataFormRef.value) {
		dataFormRef.value.resetFields()
	}

	if (id) {
		getT_pain_log(id)
	}
}

const getT_pain_log = (id: number) => {
	useT_pain_logApi(id).then(res => {
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

		useT_pain_logSubmitApi(dataForm).then(() => {
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
