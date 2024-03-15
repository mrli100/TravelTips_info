import { ref } from 'vue'
import type { Album } from '@/types/api/album'
import { generateRandomNumber } from '@/utils/local-mock'

interface AlbumInfo {
  title: string
  desc: string
}

export const useCompanyAlbum = () => {
  // 相册信息
  const albumInfo = ref<AlbumInfo>({
    title: '图鸟随手拍',
    desc: '图鸟_凶姐 & 写真集',
  })
  // 相册图片
  const albumList = ref<Album[]>([])

  // 生成相册图片
  const generateAlbumData = () => {
    const album: Album[] = Array.from({ length: 10 }).map(() => ({
      id: generateRandomNumber(1, 100).toString(),
      title: '相册',
      image: `https://resource.tuniaokj.com/images/album/xiong${
        Math.floor(Math.random() * 10) + 1
      }.jpg`,
    }))
    albumList.value = albumList.value.concat(album)
  }
  generateAlbumData()

  return {
    albumInfo,
    albumList,
    generateAlbumData,
  }
}
