<template>
	<van-row>
		<van-col span="24">
			<van-nav-bar title="添加线路" left-text="返回" right-text="发布" left-arrow @click-left="onClickLeft"
				@click-right="onClickRight" />
		</van-col>
	</van-row>
	<van-row>
		<van-col span="24">
			<div id="mapContainer" class="qqmapInfo"></div>
			<div class="mapTip">行程总共12公里</div>
		</van-col>
	</van-row>
	<!-- 日程组件 -->
	<van-row>
		<van-col span="24">
			<!-- <div style="display: flex;"> -->
			<div class="addBody addDayDiv">
				<van-sidebar v-model="active">
					<van-sidebar-item title="第一天" />
					<van-sidebar-item title="第二天" />
					<van-sidebar-item title="第三天" />
				</van-sidebar>
				<div class="addDay">
					<var-button type="primary" round>
						<var-icon name="plus" />
					</var-button>
				</div>
			</div>
			<div class="addBody addContextDiv"><van-form @submit="onSubmit">
					<van-row>
						<van-col span="24">
							<van-row justify="space-between">
								<van-col span="6">
									<van-button plain round type="primary" size="small"><van-icon name="plus" />景点</van-button>
								</van-col>
								<van-col span="6">
									<van-button plain round type="primary" size="small"><van-icon name="plus" />酒店</van-button>
								</van-col>
								<van-col span="6">
									<van-button plain round type="primary" size="small"><van-icon name="plus" />美食</van-button>
								</van-col>
								<van-col span="6">
									<van-button plain round type="primary" size="small"><van-icon name="plus" />途径</van-button>
								</van-col>
							</van-row>
						</van-col>
						<van-col span="24">
							<van-notice-bar left-icon="info-o" text="第一天需要在目的地集合，请勿迟到。集合时间在8:00，在酒店门票口，过期不候喔，迟到就自行打车过来。" />
							<van-notice-bar color="#1989fa" background="#ecf9ff" left-icon="info-o" text="当日总行程4公里，耗时3:22小时" />
						</van-col>
						<van-col span="24">
							<div style="width: 300px;">
								<van-space :align="text_align" fill class="jingdian">
									<label>08:00</label>
									<van-image width="22" height="22" :src="img_jingdian" />
									<div>{{ text }}</div>
									<van-icon name="location-o" />
								</van-space>
							</div>
						</van-col>
						<van-col span="24"><van-divider content-position="left">驾车{{kilometer}} | {{ time }}</van-divider></van-col>
					</van-row>


				</van-form></div>
			<!-- </div> -->
		</van-col>
	</van-row>
	<!-- 地区选择 -->
	<van-action-sheet v-model:show="areaShow" title="请选择城市">
		<van-tree-select v-model:active-id="activeId" v-model:main-active-index="activeIndex" :items="items" />
	</van-action-sheet>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { showToast } from 'vant'
import { text } from 'stream/consumers';
const TMap = (window as any).TMap;
const qq = (window as any).qq;
const geolocation = new qq.maps.Geolocation('EUSBZ-CK76I-67MG7-UQ2OM-VY5KF-ZQFQJ', 'webAPP');

const onClickLeft = () => history.back();
const onClickRight = () => showToast('发发发发');
let center = new TMap.LatLng(39.984104, 116.307503);
let qqmap = null;

//** 静态资源 */
import img_jingdian from '@/assets/icons/iconfont/png/jingdian.png'
const text = ref('北京市海淀区');
const text_align = ref('center')

/** 驾车时间及路程 */
const time = ref('30分钟')
const kilometer = ref('5公里')

//** 地图相关 */
const initMap = () => {
	//初始化地图
	qqmap = new TMap.Map("mapContainer", {
		zoom: 11,//设置地图缩放级别
		scale: 0.9,
		viewMode: "2D",
		center: center,//设置地图中心点坐标
		mapStyleId: "style1"
	});
	console.log(qqmap)
	// const ipLocation = new TMap.service.IPLocation();
}

//获取定位地址
const getMyLocation = () => {
	geolocation.getLocation((position) => {
		qqmap.setCenter(new TMap.LatLng(position.lat, position.lng));//坐标为天安门
	}, (e) => {
		console.log(e)
	}); //开启定位
}

//** 初始化参数 */
onMounted(() => {
	let windowsHeight = window.innerHeight
	let windowsWidth = window.innerWidth
	document.getElementsByClassName('qqmapInfo')[0].style.height = (windowsHeight / 2.3) + 'px'
	//添加内容自适应
	document.getElementsByClassName('addContextDiv')[0].style.width = (windowsWidth - 80) + 'px'
	initMap()
	getMyLocation()
})

//** 搜索相关 */
const value = ref('');
const onSearch = (val) => showToast(val);
const onClickButton = () => showToast(value.value);

//** 日程相关 */
const active = ref(0);
const dayTabWidth = ref(80)

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
<style lang="less" scoped>
.mapTip {
	position: absolute;
	z-index: 9999999;
	left: 2px;
	top: 48px;
	background: #ebcd0a;
}

.addBody {
	float: left;
}

.addDayDiv {
	width: 80px;
}

.addDay {
	width: 80px;
	text-align: center;
	font-size: 12px;
	height: 30px;
	line-height: 30px;
	// border: red 1px solid;

	.var-button {
		width: 20px;
		height: 20px;
		margin-top: 4px;
	}

	.addDayLabel {
		margin-left: 4px;
	}
}

</style>
<style lang="less">
.jingdian{
	.van-space-item:nth-child(1){
		flex: 1;
	}
	.van-space-item:nth-child(2){
		flex: 1;
	}
	.van-space-item:nth-child(3){
		flex: 6;
		width: 60%;
		div{
			white-space: nowrap;
    		text-overflow: ellipsis;
    		overflow: hidden;
		}
	}
	.van-space-item:nth-child(4){
		flex: 1;
	}
}
</style>
