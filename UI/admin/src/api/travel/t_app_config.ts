import service from '@/utils/request'

export const useT_app_configApi = (id: number) => {
	return service.get('/travel/t_app_config/' + id)
}

export const useT_app_configApiPage = (parms: any) => {
	return service.get('/travel/t_app_config/page', { params: parms });
};

export const useT_app_configQuery = (parms: any) => {
	return service.get('/travel/t_app_config/query', { params: parms });
};

export const useT_app_configSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/travel/t_app_config', dataForm)
	} else {
		return service.post('/travel/t_app_config', dataForm)
	}
}
