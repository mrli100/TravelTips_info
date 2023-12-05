import service from '@/utils/request'

export const useSys_user_infoApi = (id: number) => {
	return service.get('/maku/sys_user_info/' + id)
}

export const useSys_user_infoSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/maku/sys_user_info', dataForm)
	} else {
		return service.post('/maku/sys_user_info', dataForm)
	}
}