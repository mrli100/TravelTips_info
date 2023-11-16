<template>
  <RouterView />
  <!-- 开启底部安全区适配 -->
  <van-number-keyboard safe-area-inset-bottom />
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router'
</script>
