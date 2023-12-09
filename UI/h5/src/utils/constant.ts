import appPackage from "../../package.json";

/**
 * 常量
 */
export default {
  // 版本号
  version: appPackage.version,

  // API地址
  apiUrl: import.meta.env.VITE_API_URL,

  // 文件上传地址
  uploadUrl: import.meta.env.VITE_API_URL + "/sys/file/upload",

  // 环境变量
  env: {
    MODE: import.meta.env.MODE,
    PROD: import.meta.env.PROD,
    DEV: import.meta.env.DEV,
    SSR: import.meta.env.SSR,
  },
  nowTime: (date) => {
    let month = date.getMonth() + 1;
    month = String(month).padStart(2, "0");
    let day = date.getDate();
    day = String(month).padStart(2, "0");
    return date.getFullYear() + "-" + month + "-" + day;
  },
};
