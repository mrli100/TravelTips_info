<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
					<el-form-item>
			  <el-input v-model="state.queryForm.planId" placeholder="计划id"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:t_plan_trip_detail:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:t_plan_trip_detail:delete'" type="danger" @click="deleteBatchHandle()">删除</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
			<el-table-column prop="planId" label="计划id" header-align="center" align="center"></el-table-column>
			<el-table-column prop="tripDay" label="行程天数" header-align="center" align="center"></el-table-column>
			<el-table-column prop="tripName" label="景点名称" header-align="center" align="center"></el-table-column>
			<el-table-column prop="tripType" label="景点类型" header-align="center" align="center"></el-table-column>
			<el-table-column prop="description" label="简介" header-align="center" align="center"></el-table-column>
			<el-table-column prop="tripDayNotice" label="行程提醒" header-align="center" align="center"></el-table-column>
			<el-table-column prop="reachTime" label="到达时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="stopTime" label="停留时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="estimateCost" label="预计消费" header-align="center" align="center"></el-table-column>
			<el-table-column prop="notes" label="备注" header-align="center" align="center"></el-table-column>
			<el-table-column prop="bgImage" label="介绍图片" header-align="center" align="center"></el-table-column>
			<el-table-column prop="address" label="地址" header-align="center" align="center"></el-table-column>
			<el-table-column prop="location" label="坐标" header-align="center" align="center"></el-table-column>
			<el-table-column prop="displayIndex" label="显示顺序" header-align="center" align="center"></el-table-column>
			<el-table-column prop="distance" label="上个距离" header-align="center" align="center"></el-table-column>
			<el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
				<template #default="scope">
					<el-button v-auth="'travel:t_plan_trip_detail:update'" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'travel:t_plan_trip_detail:delete'" type="primary" link @click="deleteBatchHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination
			:current-page="state.page"
			:page-sizes="state.pageSizes"
			:page-size="state.limit"
			:total="state.total"
			layout="total, sizes, prev, pager, next, jumper"
			@size-change="sizeChangeHandle"
			@current-change="currentChangeHandle"
		>
		</el-pagination>

		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update ref="addOrUpdateRef" @refreshDataList="getDataList"></add-or-update>
	</el-card>
</template>

<script setup lang="ts" name="TravelT_plan_trip_detailIndex">
	import {useCrud} from '@/hooks'
	import {reactive, ref} from 'vue'
	import {IHooksOptions} from '@/hooks/interface'
	import AddOrUpdate from './add-or-update.vue'

	const state: IHooksOptions = reactive({
	dataListUrl: '/travel/t_plan_trip_detail/page',
	deleteUrl: '/travel/t_plan_trip_detail',
	queryForm: {
		planId: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteBatchHandle } = useCrud(state)
</script>
