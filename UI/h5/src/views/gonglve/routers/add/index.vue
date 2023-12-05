<template>
	<van-row>
		<van-col span="24">
			<van-nav-bar title="添加线路" left-text="返回" left-arrow @click-left="onClickLeft" />
		</van-col>
	</van-row>
	<van-form @submit="onSubmit">
		<van-cell-group inset>
			<van-field v-model="planMain.name" name="name" label="路线名称" placeholder="路线名称"
				:rules="[{ required: true, message: '请填写路线名称' }]" />
		</van-cell-group>
		<van-cell-group inset>
			<van-field v-model="planMain.content" rows="4" name="content" autosize label="路线简介" placeholder="请描述线路简介信息"
				type="textarea" maxlength="50" />
		</van-cell-group>
		<!-- 开始时间 -->
		<van-cell-group inset>
			<van-field v-model="planMain.createTime" is-link readonly name="createTime" label="开始时间" placeholder="点击选择时间"
				@click="showStartTimePicker = true" />
			<van-popup v-model:show="showStartTimePicker" position="bottom">
				<van-calendar v-model:show="showStartTimePicker" @confirm="onStartConfirm" />
			</van-popup>
		</van-cell-group>
		<!-- 结束时间 -->
		<van-cell-group inset>
			<van-field v-model="planMain.endTime" is-link readonly name="endTime" label="结束时间" placeholder="点击选择时间"
				@click="showEndTimePicker = true" />
			<van-calendar v-model:show="showEndTimePicker" @confirm="onEndConfirm" />
		</van-cell-group>

		<!-- 选择目的地 -->
		<van-cell-group inset>
			<van-field v-model="planMain.destination" is-link readonly name="destination" label="地区选择" placeholder="点击选择省市区"
				@click="showArea = true" />
			<van-popup v-model:show="showArea" position="bottom">
				<van-area :area-list="areaList" @confirm="onAreaConfirm" @cancel="showArea = false" />
			</van-popup>

		</van-cell-group>
		<!-- 保存按钮 -->
		<div style="margin: 16px;">
			<van-button round block type="primary" native-type="submit">
				保存，并添加明细
			</van-button>
		</div>
	</van-form>

	<!-- 地区选择 -->
	<van-action-sheet v-model:show="areaShow" title="请选择城市">
		<van-tree-select v-model:active-id="activeId" v-model:main-active-index="activeIndex" :items="items" />
	</van-action-sheet>
</template>

<script setup lang="ts">
//** 静态资源 */
import { ref, onMounted } from 'vue'
import { showToast } from 'vant'
//地址信息
import { areaList } from '@vant/area-data';
const onClickLeft = () => history.back();

//** 提交表单对象 */
const planMain = ref({
	"name": "旅游攻略",
	"content": "",
	"img": "",
	"destination": "",
	"type": 1,
	"createTime": "2021-01-01",
	"endTime": "2021-01-01",
	"updateTime": "2021-01-01"
})

//** 开始时间选择 */
const showStartTimePicker = ref(false)
const onStartConfirm = (date) => {
	planMain.value.createTime = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
	showStartTimePicker.value = false;
};
const showEndTimePicker = ref(false)
const onEndConfirm = (date) => {
	showEndTimePicker.value = false;
	planMain.value.endTime = `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()}`;
};
//** 地区选择 */
const showArea = ref(false);
const onAreaConfirm = ({ selectedOptions }) => {
	showArea.value = false;
	planMain.value.destination = selectedOptions.map((item) => item.text).join('/');
};


//** 初始化参数 */
onMounted(() => {
	let windowsHeight = window.innerHeight
	let windowsWidth = window.innerWidth
})

//** 表单测试 */
let form = {}
const addContextDivWidth = ref(80)
const onSubmit = (values) => {
	console.log('submit', values);
};
// 变量定义
const option1 = [
	{ text: '最新排序', value: 0 },
	{ text: '点赞排序', value: 1 },
	{ text: '查看排序', value: 2 }
]
const value1 = ref(0)
//地区选择
let areaShow = ref(false)
//地区选择代码
const activeId = ref(1)
const activeIndex = ref(0)
const items = [
	{
		text: '浙江',
		children: [
			{ text: '杭州', id: 1 },
			{ text: '温州', id: 2 },
			{ text: '宁波', id: 3, disabled: true }
		]
	},
	{
		text: '江苏',
		children: [
			{ text: '南京', id: 4 },
			{ text: '无锡', id: 5 },
			{ text: '徐州', id: 6 }
		]
	},
	{ text: '福建', disabled: true }
]

const onClickLocation = () => {
	if (areaShow.value == false) {
		areaShow.value = true
	} else {
		areaShow.value = false
	}
}
</script>
<style lang="less" scoped></style>
