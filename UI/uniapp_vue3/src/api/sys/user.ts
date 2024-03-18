import { http } from "@/utils/http"

export const useUserInfoApi = () => {
	return http(
		{
			method: 'GET',
			url: 'v1/user/info'
		});
};

export const useUserTravelInfoApi = () => {
	return http(
		{
			method: 'GET',
			url: 'travelV1/user/travelInfo'
		});
};

export const updatePasswordApi = (data : any) => {
	return http({
		method: 'PUT',
		url: `/sys/user/password`,
		data,
	})
};

export const useUserApi = (id : number) => {
	return http(
		{
			method: 'GET',
			url: "/sys/user/" + id
		});
};

export const useUserSubmitApi = (dataForm : any) => {
	if (dataForm.id) {
		return http({
			method: 'PUT',
			url: `/sys/user`,
			data: dataForm,
		})
	} else {
		return http({
			method: 'POST',
			url: `/sys/user`,
			data: dataForm,
		})
	}
};