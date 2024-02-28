# bunny-kona-crypto
### 开发文档
[UTS 语法](https://uniapp.dcloud.net.cn/tutorial/syntax-uts.html)
[UTS API插件](https://uniapp.dcloud.net.cn/plugin/uts-plugin.html)
[UTS 组件插件](https://uniapp.dcloud.net.cn/plugin/uts-component.html)
[Hello UTS](https://gitcode.net/dcloud/hello-uts)


本插件基于 [kona-crypto](https://github.com/Tencent/TencentKonaSMSuite/blob/master/kona-crypto/README_cn.md) 

需要写好引入代码后再打包自定义基座，才会被正确打包，否则将被hb摇掉。

> 市面上有很多 sm 算法的实现，他们的 ky、iv、密文编码、填充格式、数据转换格式等实现有很多种方式，有些尽管其格式保持一致也可能存在不能相互加解密的情况。
>
> 所以如果有涉及到密文传输、跨设备加解密，请尽量使用相同的加解密工具或者自行测试不同库之间的兼容性。
>
> 本插件 sm4 中，密文、iv、key 均是 hex 格式。
>
> 本插件开发初衷是为端到端加密 im 工作，不考虑与其他 sm 加解密库兼容。不过可以修改源码使其与你的业务逻辑兼容。
>
> 本插件与 js版、java版（js封装）的 [sm-srypto](https://www.npmjs.com/package/sm-crypto) 不兼容？？1.0.5版本新修改了，应该可以与之适配。
>
> 与 [bouncycastle](https://www.bouncycastle.org/) 兼容。



按需导入类、方法

```ts
import { TencentKonaInit, SM4_DEF, SM4_GCM, SM2,SM2_UTIL, SM4_ECB, SM3 } from "@/uni_modules/sm-crypto"
```

开始前，全局注册，直接执行即可。

```ts
TencentKonaInit()
```

#### SM2

```ts
//传入公私钥（16进制字符串），生成一个sm2加密对象
const sm2 : SM2 = new SM2("048F42C8781C4B7F3AA9A826EF511FB1255DC387961719D9971F76110290738844FED8951584F9F8E3469A2C3B88D1CBE7A1B47C486197E86DC3F715A1935DAC11", "305F898831192537DB9CCCAC00B35E4CE75074E2AAD2DE2D775F549082840D20")

//传入空字符串，将会自动生成一个拥有新公私钥的sm2对象
const sm2 : SM2 = new SM2("","")

//获取公私钥，将得到16进制字符串
console.log("sm2 pubkey", sm2.publicKey)
console.log("sm2 prikey", sm2.privateKey)

//使用公钥加密字符串，将得到16进制密文字符串
const sm2_en_str = sm2.encrypt_pubKey(sm2_msg_str, "BC")
console.log("sm2 公钥加密测试1 04开头的BC密文 C1||C3||C2", sm2_en_str)

const sm2_en_str = sm2.encrypt_pubKey(sm2_msg_str, "KONA")
console.log("sm2 公钥加密测试1 KONA 的密文 C1||C3||C2，与其他库不兼容", sm2_en_str)
//如希望使用别的库解密，请删除开头04。注意kona不能直接解密bc密文，需要转换成kona密文

//使用私钥解密，注意请传入 KONA 密文。将得到明文（UTF_8编码）
console.log("sm2 私钥解密测试1", sm2.decrypt_priKey(sm2_en_str))

//KONA 密文转换 BC 密文
const bcC1C2C3 = SM2_UTIL.cctxtToBcC1C2C3(sm2_en_str)
const bcC1C3C2 = SM2_UTIL.cctxtToBcC1C3C2(sm2_en_str)

//kona密文签名，第二个参数需要传入空字符串，将使用sm2默认id进行签名，自定义id还没有适配
const sm2_ver : string = sm2.signature(sm2_en_str, "")
console.log("sm2 消息签名测试1", sm2_ver)
//验签，返回一个布尔值
console.log("sm2 消息签名校验 测试1", sm2.sign_verified(sm2_ver, sm2_en_str))
```

#### SM3

```uts
const sm3 = new SM3()
//传入 byte[] 
sm3.checkDigest("pppppppp".toByteArray())
sm3.checkDigest("aaaaaNI好".toByteArray())
console.log("消息摘要", sm3.getDigest());
```

#### SM4

sm4的密文有多种格式，具体如下

- SM4/CBC/NoPadding：使用CBC分组操作模式，不使用填充。明文或密文的长度必须是16字节的整数倍。
- SM4/CBC/PKCS7Padding：使用CBC分组操作模式，且使用PKCS#7填充。明文或密文的长度可以不是16字节的整数倍。
- SM4/CTR/NoPadding：使用CTR分组操作模式，不使用填充。明文或密文的长度可以不是16字节的整数倍。
- SM4/ECB/NoPadding：使用ECB分组操作模式，不使用填充。明文或密文的长度必须是16字节的整数倍。
- SM4/ECB/PKCS7Padding：使用ECB分组操作模式，且使用PKCS#7填充。明文或密文的长度可以不是16字节的整数倍。
- SM4/GCM/NoPadding：使用GCM分组操作模式，不使用填充。明文或密文的长度可以不是16字节的整数倍。

##### SM4 ECB

```ts
// SM4 ECB
type SM4_ECB_TYPE = "SM4/ECB/PKCS7Padding" | "SM4/ECB/NoPadding"

//第一个参数 key任意长度的hex字符串
//第二个参数由 SM4_ECB_TYPE 进行限制
//自动生成一个hex key
const sm4_ecb : SM4_ECB = new SM4_ECB("", "SM4/ECB/PKCS7Padding")

//亦可以使用任意长度的 hex key进行初始化
const sm4_ecb : SM4_ECB = new SM4_ECB("aaa", "SM4/ECB/PKCS7Padding")

const str_sm4_ecb : string = sm4_ecb.encrypt("你好呀!@@@@@@@ hkasjdlfjas;dfj 呵呵哈哈哈成功了啊啊啊啊")

//获取hex字符串加密key
console.log("sm4 ECB key", sm4_ecb.key)
//16进制密文字符串
console.log("sm4 ECB 加密", str_sm4_ecb)
//明文
console.log("sm4 ECB 解密", sm4_ecb.decrypt(str_sm4_ecb))
```

##### SM4 CBC\CTR

```ts
type SM4_DEF_TYPE = "SM4/CBC/PKCS7Padding" | "SM4/CTR/NoPadding"
//第一个参数：iv 必须是16bit的16进制字符串（hex），传入空字符串将自动生成
//第二个参数：key 任意长度hex字符串，传入空字符串将自动生成
//第三个参数：由 SM4_DEF_TYPE 指定
const sm4_def : SM4_DEF = new SM4_DEF("000102030405060708090A0B0C0D0E0e", "", "SM4/CTR/NoPadding")
const sm4_def : SM4_DEF = new SM4_DEF("", "", "SM4/CTR/NoPadding")

//获取普通字符串key
console.log("sm4 def key", sm4_def.key)

//获取hex字符串iv
console.log("sm4 def iv", sm4_def.iv)

const str : string = sm4_def.encrypt("你好呀!@@@@@@@ hkasjdlfjas;dfj 呵呵哈哈哈成功了啊啊啊啊")
//hex字符串密文
console.log("加密", str)
//明文
console.log("解密", sm4_def.decrypt(str))
```

##### SM4 GCM

```ts
//第一个参数 iv 12bit 的16进制字符串
//第二个参数 key 16bit 的hex字符串
const sm4_gcm = new SM4_GCM("6F7862454952327634724858", "9iRgDypeRHtIrDar")

//hex字符串
console.log("sm4 def key", sm4_gcm.key)
//hex字符串
console.log("sm4 def iv", sm4_gcm.iv)
const cStr = sm4_gcm.encrypt("hhhhhh呵呵哈哈哈")
//hex字符串密文
console.log("GCM 加密测试", cStr)
//明文
console.log("GCM 解密测试", sm4_gcm.decrypt(cStr))
```

