import service from '@/utils/request'

export const use${FunctionName}Api = (id: number) => {
	return service.get('/${moduleName}/${functionName}/' + id)
}

export const use${FunctionName}ApiPage = (parms: any) => {
	return service.get('/${moduleName}/${functionName}/page', { params: parms });
};

export const use${FunctionName}Query = (parms: any) => {
	return service.get('/${moduleName}/${functionName}/query', { params: parms });
};

export const use${FunctionName}SubmitApi = (dataForm: any) => {
	if (dataForm.id) {
		return service.put('/${moduleName}/${functionName}', dataForm)
	} else {
		return service.post('/${moduleName}/${functionName}', dataForm)
	}
}
