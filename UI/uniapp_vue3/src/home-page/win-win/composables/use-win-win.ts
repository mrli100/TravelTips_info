import { ref } from 'vue'

export const useWinWin = () => {
  // 幻灯片图片
  const slideImages = ref<string[]>([
    'https://resource.tuniaokj.com/images/vue3-template/template2-website/web1.jpg',
    'https://resource.tuniaokj.com/images/vue3-template/template2-website/web2.jpg',
    'https://resource.tuniaokj.com/images/vue3-template/template2-website/web3.jpg',
    'https://resource.tuniaokj.com/images/vue3-template/template2-website/web4.jpg',
    'https://resource.tuniaokj.com/images/vue3-template/template2-website/web5.jpg',
    'https://resource.tuniaokj.com/images/vue3-template/template2-website/web6.jpg',
  ])

  return {
    slideImages,
  }
}
