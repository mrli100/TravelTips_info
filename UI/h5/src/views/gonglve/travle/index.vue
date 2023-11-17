<template>
  <van-row>
    <van-col span="24">
      <van-dropdown-menu>
        <van-dropdown-item v-model="value1" :options="option1" />
      </van-dropdown-menu>
    </van-col>
  </van-row>
  <!-- 搜索内容 -->
  <!-- <van-row>
		<van-col span="24">
			<van-nav-bar fixed @click-left="onClickLocation">
				<template #left>
					<van-icon name="location-o" size="18">北京</van-icon>
				</template>
				<template #title>
					<van-search shape="round" v-model="value" placeholder="请输入搜索关键词" />
				</template>
			</van-nav-bar>
		</van-col>
  </van-row>-->
  <!-- 瀑布流 -->
  <van-row>
    <van-col>
      <div>
        <Waterfall
          :images="images"
          :columnCount="2"
          :columnGap="30"
          :width="1100"
          mode="CSS"
          backgroundColor="#F2F4F8"
        />
      </div>
    </van-col>
  </van-row>
  <!-- 底部导航 -->
  <van-row>
    <van-col span="24">
      <tabbar>底部聊天</tabbar>
    </van-col>
  </van-row>
  <!-- 地区选择 -->
  <van-action-sheet v-model:show="areaShow" title="请选择城市">
    <van-tree-select
      v-model:active-id="activeId"
      v-model:main-active-index="activeIndex"
      :items="items"
    />
  </van-action-sheet>
</template>

<script setup lang="ts">
import { ref, onBeforeMount } from "vue";
import Waterfall from "./Waterfall.vue";
import tabbar from "@/views/tabbar/index.vue";
// 变量定义
const option1 = [
  { text: "最新排序", value: 0 },
  { text: "点赞排序", value: 1 },
  { text: "查看排序", value: 2 }
];
const value1 = ref(0);
//图片
const images = ref<any[]>([]);

function loadImages() {
  listwaterfall.forEach(i => {
    images.value.push({
      title: `image-${i.code}`,
      src: i.pic
    });
  });
}
onBeforeMount(() => {
  // 组件已完成响应式状态设置，但未创建DOM节点
  loadImages();
});
//地区选择
let areaShow = ref(false);
//瀑布流
const listwaterfall = [
  {
    pic:
      "https://picnew9.photophoto.cn/20150702/lanseshejibeijingtupiangaoqingpsdxiazai-27756366_1.jpg"
  },
  {
    pic: "https://lmg.jj20.com/up/allimg/tp06/20120222122H618-0-lp.jpg"
  },
  {
    pic: "https://pic.616pic.com/photoone/00/03/74/618ce3d196a7d6996.jpg"
  },
  {
    pic: "https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg"
  },
  {
    pic:
      "https://www.androidonline.net/uploads/allimg/c191026/15H0DVT5240-224408.jpg"
  },
  {
    pic:
      "https://img1.baidu.com/it/u=834894649,3086306884&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422"
  },
  {
    pic: "https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg"
  },
  {
    pic: "https://lmg.jj20.com/up/allimg/tp06/20120222122H618-0-lp.jpg"
  },
  {
    pic: "https://pic.616pic.com/photoone/00/03/74/618ce3d196a7d6996.jpg"
  },
  {
    pic: "https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg"
  },
  {
    pic:
      "https://www.androidonline.net/uploads/allimg/c191026/15H0DVT5240-224408.jpg"
  },
  {
    pic:
      "https://img1.baidu.com/it/u=834894649,3086306884&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1422"
  },
  {
    pic: "https://fastly.jsdelivr.net/npm/@vant/assets/apple-1.jpeg"
  }
];
//地区选择代码
const activeId = ref(1);
const activeIndex = ref(0);
const items = [
  {
    text: "浙江",
    children: [
      { text: "杭州", id: 1 },
      { text: "温州", id: 2 },
      { text: "宁波", id: 3, disabled: true }
    ]
  },
  {
    text: "江苏",
    children: [
      { text: "南京", id: 4 },
      { text: "无锡", id: 5 },
      { text: "徐州", id: 6 }
    ]
  },
  { text: "福建", disabled: true }
];
</script>
<style lang="less" scoped>
//瀑布流样式

//强制修改滚动列表
:deep(.van-list) {
  height: 30vh;
  overflow-y: scroll;
}

.box {
}
.box .card {
  width: 40%;
  float: left;
}
.box img {
  width: 100%;
}
</style>
