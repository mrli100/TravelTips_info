import service from '@/utils/request'

export const useT_pain_logApi = (id: number) => {
	return service.get('/travel/t_pain_log/' + id)
}

export const useT_pain_logApiPage = (parms: any) => {
	return service.get('/travel/t_pain_log/page', { params: parms })
}

export const useT_pain_logQuery = (parms: any) => {
	return service.get('/travel/t_pain_log/query', { params: parms })
}

export const useT_pain_logSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/travel/t_pain_log', dataForm)
	} else {
		return service.post('/travel/t_pain_log', dataForm)
	}
}
