import service from '@/utils/request'

export const useSysUserInfoApi = (id: number) => {
	return service.get('/travel/sysUserInfo/' + id)
}

export const useSysUserInfoApiPage = (parms: any) => {
	return service.get('/travel/sysUserInfo/page', { params: parms })
}

export const useSysUserInfoQuery = (parms: any) => {
	return service.get('/travel/sysUserInfo/query', { params: parms })
}

export const useSysUserInfoSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/travel/sysUserInfo', dataForm)
	} else {
		return service.post('/travel/sysUserInfo', dataForm)
	}
}
