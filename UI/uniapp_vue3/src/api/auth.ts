import { http } from "@/utils/http"

export const useCaptchaEnabledApi = () => {
	return http(
		{
			method: 'GET',
			url: '/sys/auth/captcha/enabled'
		});
};

export const useCaptchaApi = () => {
	return http(
		{
			method: 'GET',
			url: '/sys/auth/captcha'
		});
};

export const useSendCodeApi = (mobile : string) => {
	return http({
		method: 'POST',
		url: "/sys/auth/send/code?mobile=" + mobile
	});
};

export const useAccountLoginApi = (data : any) => {
	return http({
		method: 'POST',
		url: '/sys/auth/login',
		data,
	});
};

export const useMobileLoginApi = (data : any) => {
	return http({
		method: 'POST',
		url: '/sys/auth/mobile',
		data,
	});
};

export const useLogoutApi = () => {
	return http({
		method: 'POST',
		url: '/sys/auth/logout'
	});
};