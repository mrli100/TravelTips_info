<template>
  <van-row>
    <van-col span="24">
      <div ref="menuElement">
        <van-dropdown-menu>
          <van-dropdown-item v-model="OrderIndexVal" :options="OrderIndex" />
        </van-dropdown-menu>
      </div>
    </van-col>
  </van-row>
  <!-- 瀑布流 -->
  <van-row>
    <van-col>
      <div class="content">
        <vue-grid-waterfall :data-list="dataList" :columns="2" @getMoreData="getMoreData" :loading="isLoading"
          :height="waterFallHeight" :width="waterFallWidth">
          <template #slot-scope="{ slotProps }">
            <div class="item" :style="{ height: slotProps.data.height, background: slotProps.data.color }">{{
              slotProps.data.color
            }}</div>
          </template>
        </vue-grid-waterfall>
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
    <van-tree-select v-model:active-id="activeId" v-model:main-active-index="activeIndex" :items="items" />
  </van-action-sheet>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { showToast } from 'vant'
import tabbar from "@/views/tabbar/index.vue";
//** 排序选择 */
const OrderIndexVal = ref(0);
const OrderIndex = [
  { text: "最新排序", value: 0 },
  { text: "点赞排序", value: 1 },
  { text: "查看排序", value: 2 }
];

//** 瀑布流  */
//瀑布流的高宽
let waterFallHeight = ref(100);
let waterFallWidth = ref(100);
//定义组件
const menuElement = ref(null)
const dataList = ref<any>([]);
//获取随机颜色
const getRandomColor = () => {
  const getColor: any = (color: any) => {
    return (color += '0123456789abcdef'[Math.floor(Math.random() * 16)]) && (color.length == 6) ? color : getColor(color);
  };
  return '#' + getColor('')
}
const getMoreData = () => {
  isLoading.value = true;
  getData()
}

const isLoading = ref(true);

//获取数据
const getData = () => {
  for (let i = 0; i < 100; i++) {
    dataList.value.push({ height: 100 + Math.random() * 250 + 'px', color: getRandomColor() })
  }
  setTimeout(() => {
    isLoading.value = false;
  })
}
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
  }
];
//** 初始化方法  */
onMounted(() => {
  //获取窗口高度-剪掉其它组件高度
  let waterHeight = window.innerHeight - menuElement.value.offsetHeight - 50 - 44
  waterFallHeight.value = waterHeight
  waterFallWidth.value = window.innerWidth
  getData()

})

//地区选择
let areaShow = ref(false);
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
//强制修改滚动列表
:deep(.van-list) {
  height: 30vh;
  overflow-y: scroll;
}

//强制修改滚动列表
:deep(.grid-content) {
  overflow-y: scroll;
}
</style>
