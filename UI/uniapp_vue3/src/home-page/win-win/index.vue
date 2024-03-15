<script lang="ts" setup>
import TnSlideShow from 'tnuiv3p-tn-slideshow/index.vue'
import TnSuspendButton from 'tnuiv3p-tn-suspend-button/index.vue'
import { useWinWin } from './composables'

const { slideImages } = useWinWin()
</script>

<template>
  <TnNavbar
    fixed
    bg-color="transparent"
    :bottom-shadow="false"
    :placeholder="false"
  />
  <view class="win-win-page">
    <TnSlideShow :data="slideImages" />
    <TnSuspendButton bg-color="tn-gradient__orange">
      <TnButton only-button open-type="contact">
        <TnIcon name="wechat-fill" color="#fff" size="70" />
      </TnButton>
    </TnSuspendButton>
  </view>
</template>

<style lang="scss" scoped>
@import './styles/index.scss';
</style>
