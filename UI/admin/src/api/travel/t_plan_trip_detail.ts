import service from '@/utils/request'

export const useT_plan_trip_detailApi = (id: number) => {
	return service.get('/travel/t_plan_trip_detail/' + id)
}

export const useT_plan_trip_detailApiPage = (parms: any) => {
	return service.get('/travel/t_plan_trip_detail/page', { params: parms })
}

export const useT_plan_trip_detailQuery = (parms: any) => {
	return service.get('/travel/t_plan_trip_detail/query', { params: parms })
}

export const useT_plan_trip_detailSubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/travel/t_plan_trip_detail', dataForm)
	} else {
		return service.post('/travel/t_plan_trip_detail', dataForm)
	}
}
