<template>
	<el-dialog v-model="visible" :title="!dataForm.id ? '新增' : '修改'" :close-on-click-modal="false">
		<el-form ref="dataFormRef" :model="dataForm" :rules="dataRules" label-width="100px" @keyup.enter="submitHandle()">
	    				<el-form-item label="计划id" prop="planId">
					<el-input v-model="dataForm.planId" placeholder="计划id"></el-input>
				</el-form-item>
				<el-form-item label="行程天数" prop="tripDay">
					<el-input v-model="dataForm.tripDay" placeholder="行程天数"></el-input>
				</el-form-item>
				<el-form-item label="景点名称" prop="tripName">
					<el-input v-model="dataForm.tripName" placeholder="景点名称"></el-input>
				</el-form-item>
				<el-form-item label="景点类型" prop="tripType">
					<el-input v-model="dataForm.tripType" placeholder="景点类型"></el-input>
				</el-form-item>
				<el-form-item label="简介" prop="description">
					<el-input v-model="dataForm.description" placeholder="简介"></el-input>
				</el-form-item>
				<el-form-item label="行程提醒" prop="tripDayNotice">
					<el-input v-model="dataForm.tripDayNotice" placeholder="行程提醒"></el-input>
				</el-form-item>
				<el-form-item label="到达时间" prop="reachTime">
					<el-input v-model="dataForm.reachTime" placeholder="到达时间"></el-input>
				</el-form-item>
				<el-form-item label="停留时间" prop="stopTime">
					<el-input v-model="dataForm.stopTime" placeholder="停留时间"></el-input>
				</el-form-item>
				<el-form-item label="预计消费" prop="estimateCost">
					<el-input v-model="dataForm.estimateCost" placeholder="预计消费"></el-input>
				</el-form-item>
				<el-form-item label="备注" prop="notes">
					<el-input v-model="dataForm.notes" placeholder="备注"></el-input>
				</el-form-item>
				<el-form-item label="介绍图片" prop="bgImage">
					<el-input v-model="dataForm.bgImage" placeholder="介绍图片"></el-input>
				</el-form-item>
				<el-form-item label="地址" prop="address">
					<el-input v-model="dataForm.address" placeholder="地址"></el-input>
				</el-form-item>
				<el-form-item label="坐标" prop="location">
					<el-input v-model="dataForm.location" placeholder="坐标"></el-input>
				</el-form-item>
				<el-form-item label="显示顺序" prop="displayIndex">
					<el-input v-model="dataForm.displayIndex" placeholder="显示顺序"></el-input>
				</el-form-item>
				<el-form-item label="上个距离" prop="distance">
					<el-input v-model="dataForm.distance" placeholder="上个距离"></el-input>
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
import { useT_plan_trip_detailApi, useT_plan_trip_detailSubmitApi } from '@/api/travel/t_plan_trip_detail'

const emit = defineEmits(['refreshDataList'])

const visible = ref(false)
const dataFormRef = ref()

const dataForm = reactive({
	id: '',
	planId: '',
	tripDay: '',
	tripName: '',
	tripType: '',
	description: '',
	tripDayNotice: '',
	reachTime: '',
	stopTime: '',
	estimateCost: '',
	notes: '',
	bgImage: '',
	address: '',
	location: '',
	displayIndex: '',
	distance: '',
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
		getT_plan_trip_detail(id)
	}
}

const getT_plan_trip_detail = (id: number) => {
	useT_plan_trip_detailApi(id).then(res => {
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

		useT_plan_trip_detailSubmitApi(dataForm).then(() => {
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
