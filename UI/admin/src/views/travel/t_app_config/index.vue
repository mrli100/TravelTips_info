<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
					<el-form-item>
			  <el-input v-model="state.queryForm.type" placeholder="应用标识"></el-input>
			</el-form-item>
			<el-form-item>
			  <el-input v-model="state.queryForm.name" placeholder="配置名称"></el-input>
			</el-form-item>
			<el-form-item>
			  <el-input v-model="state.queryForm.keyname" placeholder="标识"></el-input>
			</el-form-item>
			<el-form-item>
			  <el-input v-model="state.queryForm.value" placeholder="值"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:t_app_config:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:t_app_config:delete'" type="danger" @click="deleteBatchHandle()">删除</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%" @selection-change="selectionChangeHandle">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
			<el-table-column prop="type" label="应用标识" header-align="center" align="center"></el-table-column>
			<el-table-column prop="name" label="配置名称" header-align="center" align="center"></el-table-column>
			<el-table-column prop="keyname" label="标识" header-align="center" align="center"></el-table-column>
			<el-table-column prop="value" label="值" header-align="center" width="720px" align="center"></el-table-column>
			<el-table-column prop="status" label="状态" header-align="center" align="center"></el-table-column>
			<el-table-column prop="creator" label="创建者" header-align="center" align="center"></el-table-column>
			<el-table-column prop="createTime" label="创建时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="updater" label="更新者" header-align="center" align="center"></el-table-column>
			<el-table-column prop="updateTime" label="更新时间" header-align="center" align="center"></el-table-column>
			<el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
				<template #default="scope">
					<el-button v-auth="'travel:t_app_config:update'" type="primary" link @click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'travel:t_app_config:delete'" type="primary" link @click="deleteBatchHandle(scope.row.id)">删除</el-button>
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

<script setup lang="ts" name="TravelT_app_configIndex">
	import {useCrud} from '@/hooks'
	import {reactive, ref} from 'vue'
	import {IHooksOptions} from '@/hooks/interface'
	import AddOrUpdate from './add-or-update.vue'

	const state: IHooksOptions = reactive({
	dataListUrl: '/travel/t_app_config/page',
	deleteUrl: '/travel/t_app_config',
	queryForm: {
		type: '', 
		name: '', 
		keyname: '', 
		value: ''
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteBatchHandle } = useCrud(state)
</script>
