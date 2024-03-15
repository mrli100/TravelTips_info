import { getCurrentInstance, ref } from 'vue'
import type { Product } from '@/types/api/product'
import type { NavbarRectInfo } from '@tuniao/tnui-vue3-uniapp/components/navbar'
import { generateRandomFloat, generateRandomNumber } from '@/utils/local-mock'
import { useScrollTransparentNavbar } from '@/hooks'

export const useProductDetail = () => {
  const instance = getCurrentInstance()
  const product = ref<Product>()

  // 生成产品数据
  const generateProductData = (): Product => {
    return {
      id: generateRandomNumber(1, 100).toString(),
      title: '图鸟模板',
      desc: '图鸟模板，是一套你值得拥有的模板',
      images: [
        'https://resource.tuniaokj.com/images/shop/bag1.jpg',
        'https://resource.tuniaokj.com/images/shop/bag2.jpg',
        'https://resource.tuniaokj.com/images/shop/card.jpg',
        'https://resource.tuniaokj.com/images/shop/computer1.jpg',
        'https://resource.tuniaokj.com/images/shop/computer2.jpg',
        'https://resource.tuniaokj.com/images/shop/cup1.jpg',
        'https://resource.tuniaokj.com/images/shop/cup2.jpg',
      ].sort(() => Math.random() - 0.5),
      price: generateRandomFloat(1, 1000),
      sale: generateRandomNumber(1, 1000),
      content: '图鸟模板，是一套你值得拥有的模板',
      attributes: [
        {
          name: '前端语言',
          value: 'uniapp',
        },
        {
          name: '后端语言',
          value: '你想要的都有',
        },
        {
          name: '授权方式',
          value: '单独购买',
        },
      ],
      tags: ['热销', '推荐', '新品'].slice(0, generateRandomNumber(1, 3)),
    }
  }
  product.value = generateProductData()

  const {
    triggerElementId,
    navbarOpacity,
    init: initTransparentScroll,
    updateTargetTriggerValue,
    opacityScrollHandle,
  } = useScrollTransparentNavbar(instance)

  // 顶部导航栏初始化完成事件
  const navbarInitFinishHandle = (info: NavbarRectInfo) => {
    updateTargetTriggerValue(info.height)
  }

  return {
    triggerElementId,
    navbarOpacity,
    product,
    initTransparentScroll,
    opacityScrollHandle,
    navbarInitFinishHandle,
  }
}
