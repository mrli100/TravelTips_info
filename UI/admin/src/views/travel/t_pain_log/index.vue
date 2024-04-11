<template>
	<el-card>
		<el-form :inline="true" :model="state.queryForm" @keyup.enter="getDataList()">
			<el-form-item>
				<el-button @click="getDataList()">查询</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:t_pain_log:save'" type="primary" @click="addOrUpdateHandle()">新增</el-button>
			</el-form-item>
			<el-form-item>
				<el-button v-auth="'travel:t_pain_log:delete'" type="danger" @click="deleteBatchHandle()">删除</el-button>
			</el-form-item>
		</el-form>
		<el-table v-loading="state.dataListLoading" :data="state.dataList" border style="width: 100%"
			@selection-change="selectionChangeHandle">
			<el-table-column type="selection" header-align="center" align="center" width="50"></el-table-column>
			<el-table-column prop="id" label="主键" header-align="center" align="center"></el-table-column>
			<el-table-column prop="uid" label="QQ号" header-align="center" align="center"></el-table-column>
			<el-table-column prop="times" label="访问时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="timeData" label="访问日期" header-align="center" align="center"></el-table-column>
			<el-table-column prop="hideFrom" label="hide_from" header-align="center" align="center"></el-table-column>
			<el-table-column prop="isHideVisit" label="is_hide_visit" header-align="center" align="center"></el-table-column>
			<el-table-column prop="nickName" label="QQ昵称" header-align="center" align="center"></el-table-column>
			<el-table-column prop="yellow" label="黄钻" header-align="center" align="center"></el-table-column>
			<el-table-column prop="supervip" label="是否VIP" header-align="center" align="center"></el-table-column>
			<el-table-column prop="annualfee" label="是否年费" header-align="center" align="center"></el-table-column>
			<el-table-column prop="photoes" label="访问内容" header-align="center" align="center"></el-table-column>
			<el-table-column prop="uins" label="uins" header-align="center" align="center"></el-table-column>
			<el-table-column prop="creator" label="创建人" header-align="center" align="center"></el-table-column>
			<el-table-column prop="createTime" label="创建时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="updater" label="更新人" header-align="center" align="center"></el-table-column>
			<el-table-column prop="updateTime" label="更新时间" header-align="center" align="center"></el-table-column>
			<el-table-column prop="tenantId" label="租户ID" header-align="center" align="center"></el-table-column>
			<el-table-column prop="version" label="版本号" header-align="center" align="center"></el-table-column>
			<el-table-column prop="deleted" label="删除标识  0：正常   1：已删除" header-align="center" align="center"></el-table-column>
			<el-table-column label="操作" fixed="right" header-align="center" align="center" width="150">
				<template #default="scope">
					<el-button v-auth="'travel:t_pain_log:update'" type="primary" link
						@click="addOrUpdateHandle(scope.row.id)">修改</el-button>
					<el-button v-auth="'travel:t_pain_log:delete'" type="primary" link
						@click="deleteBatchHandle(scope.row.id)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-pagination :current-page="state.page" :page-sizes="state.pageSizes" :page-size="state.limit"
			:total="state.total" layout="total, sizes, prev, pager, next, jumper" @size-change="sizeChangeHandle"
			@current-change="currentChangeHandle">
		</el-pagination>

		<!-- 弹窗, 新增 / 修改 -->
		<add-or-update ref="addOrUpdateRef" @refreshDataList="getDataList"></add-or-update>
	</el-card>
</template>

<script setup lang="ts" name="TravelT_pain_logIndex">
import { useCrud } from '@/hooks'
import { reactive, ref } from 'vue'
import { IHooksOptions } from '@/hooks/interface'
import AddOrUpdate from './add-or-update.vue'

const state: IHooksOptions = reactive({
	dataListUrl: '/travel/t_pain_log/page',
	deleteUrl: '/travel/t_pain_log',
	queryForm: {
	}
})

const addOrUpdateRef = ref()
const addOrUpdateHandle = (id?: number) => {
	addOrUpdateRef.value.init(id)
}

const { getDataList, selectionChangeHandle, sizeChangeHandle, currentChangeHandle, deleteBatchHandle } = useCrud(state)
</script>
