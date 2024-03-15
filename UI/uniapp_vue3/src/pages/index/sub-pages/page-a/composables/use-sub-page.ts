import { getCurrentInstance, onMounted, ref } from 'vue'
import { tnNavPage } from '@tuniao/tnui-vue3-uniapp/utils'
import { useSubPageProvide } from '../../../composables'
import type { NavbarRectInfo } from '@tuniao/tnui-vue3-uniapp/components/navbar'
import type { IndexPageOnLoadFunc, IndexPageOnScrollFunc } from '../../../types'
import type { Category } from '@/types/api/category'

import { useScrollTransparentNavbar } from '@/hooks'
import { ColorType } from '@/types/color'

export const useSubPage = () => {
  const instance = getCurrentInstance()
  // 顶部轮播图
  const swiperData = ref<string[]>([
    'https://resource.tuniaokj.com/images/swiper/ad2.jpg',
    'https://resource.tuniaokj.com/images/swiper/ad3.jpg',
    'https://resource.tuniaokj.com/images/swiper/ad4.jpg',
    'https://resource.tuniaokj.com/images/swiper/ad5.jpg',
  ])

  // 热门分类
  const hotCategoryData = ref<Category[]>([
    {
      id: '1',
      name: '品牌介绍',
      icon: 'cute',
      backgroundColor: {
        type: ColorType.select,
        value: '#3c7efe',
      },
      url: '/home-page/brand-introduction/index',
    },
    {
      id: '2',
      name: '合作共赢',
      icon: 'cute',
      backgroundColor: {
        type: ColorType.select,
        value: '#2be9bb',
      },
      url: '/home-page/win-win/index',
    },
    {
      id: '3',
      name: '社区话题',
      icon: 'cute',
      backgroundColor: {
        type: ColorType.select,
        value: '#e93c32',
      },
      url: '/home-page/topic-community/index',
    },
    {
      id: '4',
      name: '开源生态',
      icon: 'cute',
      backgroundColor: {
        type: ColorType.select,
        value: '#ffa929',
      },
      url: '/home-page/tuniao-ecology/index',
    },
  ])

  // 通知数据
  const noticeData = ref<string[]>([
    '图鸟官网全新上线，欢迎大家前来访问',
    '图鸟官网全新上线，欢迎大家前来访问',
    '图鸟官网全新上线，欢迎大家前来访问',
  ])

  // 广告胶囊
  const adCapsuleData = ref<string>(
    'https://resource.tuniaokj.com/images/swiper/capsule1.png'
  )

  // 热门案例
  const hotCaseData = ref<string[]>([
    'https://resource.tuniaokj.com/images/blogger/content_1.jpeg',
    'https://resource.tuniaokj.com/images/blogger/onepiece-1.jpg',
    'https://resource.tuniaokj.com/images/blogger/onepiece-2.jpg',
  ])

  const {
    triggerElementId,
    navbarOpacity,
    init: initTransparentScroll,
    updateTargetTriggerValue,
    opacityScrollHandle,
  } = useScrollTransparentNavbar(instance)

  onMounted(() => {
    initTransparentScroll()
  })

  // 顶部导航栏初始化完成事件
  const navbarInitFinishHandle = (info: NavbarRectInfo) => {
    updateTargetTriggerValue(info.height)
  }

  // 跳转到关于图鸟页面
  const navAboutPage = () => {
    tnNavPage('/tuniao/about/index')
  }

  // 跳转到搜索页面
  const navSearchPage = () => {
    tnNavPage('/home-page/search/index')
  }

  const onLoad: IndexPageOnLoadFunc = () => {
    // eslint-disable-next-line no-console
    console.log('pageA onLoad')
  }
  const onScroll: IndexPageOnScrollFunc = ({ top }) => {
    opacityScrollHandle(top)
  }

  useSubPageProvide(0, {
    onLoad,
    onScroll,
  })

  return {
    triggerElementId,
    navbarOpacity,
    swiperData,
    hotCategoryData,
    noticeData,
    adCapsuleData,
    hotCaseData,
    tnNavPage,
    navbarInitFinishHandle,
    navAboutPage,
    navSearchPage,
  }
}
