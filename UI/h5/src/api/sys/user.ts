import service from "@/utils/request";

export const useUserInfoApi = () => {
  return service.get("v1/user/info");
};

export const updatePasswordApi = (data: any) => {
  return service.put("/sys/user/password", data);
};

export const useUserApi = (id: number) => {
  return service.get("/sys/user/" + id);
};

export const useUserSubmitApi = (dataForm: any) => {
  if (dataForm.id) {
    return service.put("/sys/user", dataForm);
  } else {
    return service.post("/sys/user", dataForm);
  }
};
