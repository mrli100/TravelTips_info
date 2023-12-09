import service from "@/utils/request";

// 附件上传接口
export function attachUpload(data) {
  // 创建一个 FormData 对象来传递文件
  const formData = new FormData();
  formData.append("file", data.file); // 假设你的文件在 data 对象的 file 属性中
  return service.post("/sys/file/upload_v2", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}
