<template>
  <van-row>
    <van-col span="24">
      <van-tabs v-model:active="active" @click-tab="onClickTab">
        <van-tab title="攻略"></van-tab>
        <van-tab title="路线"></van-tab>
      </van-tabs>
    </van-col>
  </van-row>
  <van-row>
    <van-col span="24">
      <tabbar>底部聊天</tabbar>
    </van-col>
  </van-row>
  <!-- 开启底部安全区适配 -->
  <van-number-keyboard safe-area-inset-bottom />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { showToast } from 'vant'
//变量定义
let active = ref(0)
const onClickTab = ({ title }) => {
  showToast(title)
}
</script>
<style scoped></style>
