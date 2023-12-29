import service from '@/utils/request'

export const useSysUserInfoApi = (id: number) => {
	return service.get('/travel/sysUserInfo/' + id)
}

export const useSysUserInfoSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/travel/sysUserInfo', dataForm)
	} else {
		return service.post('/travel/sysUserInfo', dataForm)
	}
}

export const updateSysUserApi = (dataForm: any) => {
	return service.put('/travel/sysUserInfo/update', dataForm)
}
