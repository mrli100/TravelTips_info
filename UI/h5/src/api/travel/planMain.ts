import service from "@/utils/request";

export const usePlanMainApi = (id: number) => {
  return service.get("/travel/planMain/" + id);
};

export const usePlanMainApiPage = (parms) => {
  return service.get("/travel/planMain/page", { params: parms });
};

export const usePlanMainApiQuery = (parms) => {
  return service.get("/travel/planMain/query", { params: parms });
};

export const usePlanMainSubmitApi = (dataForm: any) => {
  if (dataForm.id) {
    return service.put("/travel/planMain", dataForm);
  } else {
    return service.post("/travel/planMain", dataForm);
  }
};
