<template>
	<van-row class="user-info">
		<van-col span="24">
			<van-icon name="pending-payment"></van-icon>
			待付款
		</van-col>
	</van-row>
	<van-row class="user-link">
		<van-col span="6">
			<van-icon name="pending-payment"></van-icon>
			待付款
		</van-col>
		<van-col span="6">
			<van-icon name="pending-orders"></van-icon>
			待接单
		</van-col>
		<van-col span="6">
			<van-icon name="pending-deliver"></van-icon>
			待发货
		</van-col>
		<van-col span="6">
			<van-icon name="logistics"></van-icon>
			待收货
		</van-col>
	</van-row>
	<van-cell-group class="user-group">
		<van-cell title="全部订单" icon="records" is-link to="/OrderList" /></van-cell-group>
	<van-cell-group>
		<van-cell title="我的积分" icon="exchange" is-link />
		<van-cell title="我的优惠卷" icon="gold-coin" is-link />
		<van-cell title="我收到的礼物" icon="gift" is-link />
	</van-cell-group>
	<van-row>
		<van-col span="24">
			<tabbar>底部聊天</tabbar>
		</van-col>
	</van-row>
</template>

<script  setup lang="ts">
import tabbar from "@/views/tabbar/index.vue";
</script>

<style lang="stylus" scoped>
.user-poster
  width 100%
  height auto
  display block

.user-link
  text-align center
  font-size 12px
  padding 15px 0
  background-color #fff

  .van-icon
    display block
    margin-bottom 4px
    font-size 24px

.user-group
  margin-bottom 0.3rem
</style>