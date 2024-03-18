import { defineStore } from "pinia";
import {
	useAccountLoginApi,
	useMobileLoginApi,
	useLogoutApi,
} from "@/api/auth";
import { useUserInfoApi, useUserTravelInfoApi } from "@/api/sys/user";
import storage from "@/utils/storage";
// import { useAuthorityListApi } from "@/api/sys/menu";

export const useUserStore = defineStore("userStore", {
	state: () => ({
		// 用户信息
		user: {
			id: "",
			superAdmin: 0,
			userId: "",
			username: "",
			avatar: "",
			realName: "",
			mobile: "",
			tags: "",
			orgId: "",
			signature: "",
		},
		// 用户旅游信息
		userTravel: {
			id: "",
			//草稿箱
			drafts: "",
		},
		// 权限列表
		authorityList: [],
		// 访问token
		token: storage.getToken(),
		// 刷新token
		refreshToken: storage.getRefreshToken(),
	}),
	actions: {
		setUser(val : any) {
			this.user = val;
		},
		setUserTravel(val : any) {
			this.userTravel = val;
		},
		setToken(val : any) {
			this.token = val;
			storage.setToken(val);
		},
		setRefreshToken(val : any) {
			this.refreshToken = val;
			storage.setRefreshToken(val);
		},
		// 账号登录
		async accountLoginAction(loginForm : any) {
			const { data } = await useAccountLoginApi(loginForm);
			this.setToken(data.access_token);
			this.setRefreshToken(data.refresh_token);
		},
		// 手机号登录
		async mobileLoginAction(loginForm : any) {
			const { data } = await useMobileLoginApi(loginForm);
			this.setToken(data.access_token);
			this.setRefreshToken(data.refresh_token);
		},
		// 获取用户信息
		async getUserInfoAction() {
			const { data } = await useUserInfoApi();
			this.setUser(data);
		},
		// 获取用户攻略信息
		async getUserTravelInfoAction() {
			const { data } = await useUserTravelInfoApi();
			this.setUserTravel(data);
		},
		// 获取权限
		async getAuthorityListAction() {
			// const { data } = await useAuthorityListApi();
			this.authorityList = data || [];
		},
		// 用户退出
		async logoutAction() {
			await useLogoutApi();
			// 移除 token
			this.setToken(null);
			this.setRefreshToken(null);
		},
	},
});