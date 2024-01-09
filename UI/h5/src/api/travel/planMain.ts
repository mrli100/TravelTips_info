import service from '@/utils/request'

export const usePlanMainApi = (id: number) => {
	return service.get('/travel/planMain/' + id)
}

export const usePlanMainSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/travel/planMain', dataForm)
	} else {
		return service.post('/travel/planMain', dataForm)
	}
}