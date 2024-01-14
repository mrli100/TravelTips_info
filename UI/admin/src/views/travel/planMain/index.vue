<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item>
				<el-input v-model="state.queryForm.planName" placeholder="计划名称"></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="state.queryForm.planDesc" placeholder="计划简介"></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="state.queryForm.destination" placeholder="目的地"></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="state.queryForm.state" placeholder="状态"></el-input>
			</el-form-item>
			<el-form-item>
				<el-input v-model="state.queryForm.creator" placeholder="创建人"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:planMain:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:planMain:delete'" type="danger" @click="deleteBatchHandle()">删除</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%"
			@selection-change="selectionChangeHandle">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
			<el-table-column prop="id" label="主键id" header-align="center" align="center"></el-table-column>
			<el-table-column prop="planName" label="计划名称" header-align="center" align="center"></el-table-column>
			<el-table-column prop="planDesc" label="计划简介" header-align="center" align="center"></el-table-column>
			<el-table-column prop="planStartTime" label="计划开始时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="planEndTime" label="计划结束时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="destination" label="目的地" header-align="center" align="center"></el-table-column>
			<el-table-column prop="bgImage" label="背景图片" header-align="center" align="center"></el-table-column>
			<el-table-column prop="category" label="类别" header-align="center" align="center"></el-table-column>
			<el-table-column prop="state" label="状态" header-align="center" align="center"></el-table-column>
			<el-table-column prop="creator" label="创建人" header-align="center" align="center"></el-table-column>
			<el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
				<template #default="scope">
					<el-button v-auth="'travel:planMain:update'" type="primary" link
						@click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'travel:planMain:delete'" type="primary" link
						@click="deleteBatchHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination :current-page="state.page" :page-sizes="state.pageSizes" :page-size="state.limit" :total="state.total"
			layout="total, sizes, prev, pager, next, jumper" @size-change="sizeChangeHandle"
			@current-change="currentChangeHandle">
		</el-pagination>

		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update ref="addOrUpdateRef" @refreshDataList="getDataList"></add-or-update>
	</el-card>
</template>

<script setup lang="ts" name="TravelPlanMainIndex">
import { useCrud } from '@/hooks'
import { reactive, ref } from 'vue'
import { IHooksOptions } from '@/hooks/interface'
import AddOrUpdate from './add-or-update.vue'

const state: IHooksOptions = reactive({
	dataListUrl: '/travel/planMain/page',
	deleteUrl: '/travel/planMain',
	queryForm: {
		planName: '',
		planDesc: '',
		destination: '',
		state: '',
		creator: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteBatchHandle } = useCrud(state)
</script>
