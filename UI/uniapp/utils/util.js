var api = require('../config/api.js');

function navigateTo(res) {
	let userInfo = uni.getStorageSync('user_info');
	if (userInfo == '' || userInfo == null || typeof(userInfo) == "undefined") {
		// #ifdef APP || H5
		uni.navigateTo({
			url: '/pages/login/login'
		});
		// #endif
		// #ifndef APP || H5
		login().then(function(e) {
			if (e) {
				uni.navigateTo({
					url: res.url
				});
			} else {
				uni.setStorageSync('tourl', res.url)
				uni.navigateTo({
					url: '/pages/login/login'
				});
			}
		});
		// #endif

	} else {
		uni.navigateTo({
			url: res.url
		})
	}


}

function userRefreshInfo() {
	let userInfo = uni.getStorageSync('user_info');
	if (userInfo == '' || userInfo == null || typeof(userInfo) == "undefined") {

		request(api.userRefreshInfoUrl).then(function(res) {
			uni.setStorageSync('user_info', res.data.info);
			uni.setStorageSync('user_token', res.data.token);
			if (res.data.info.status == 0) {
				uni.reLaunch({
					url: '/pages/setting/err'
				});
			}
		});
	}
}


function getMessageCount() {
	let userInfo = uni.getStorageSync('user_info');
	if (userInfo == '' || userInfo == null || typeof(userInfo) == "undefined") {
		return;
	}
	request(api.messageCountUrl).then(function(res) {
		if (res.data) {
			uni.setTabBarBadge({
				index: 3,
				text: res.data.toString()
			})
		} else {
			uni.removeTabBarBadge({
				index: 3,
			})
		}
	})
}

function loginNowNew() {
	return new Promise((resolve, reject) => {
		let userInfo = uni.getStorageSync('user_info');
		if (userInfo == '' || userInfo == null || typeof(userInfo) == "undefined") {
			resolve(true);
		} else {
			if (userInfo.status == 0) {
				request(api.userRefreshInfoUrl).then(function(res) {
					uni.setStorageSync('user_info', res.data.info);
					uni.setStorageSync('user_token', res.data.token);
					if (res.data.info.status == 0) {
						uni.reLaunch({
							url: '/pages/setting/err'
						});
					} else {
						resolve(true);
					}
				});
			} else {
				resolve(true);
			}
		}
	});
}

function login() {
	return new Promise((resolve, reject) => {
		// #ifdef MP-WEIXIN
		let platform = 'weixin';
		let type = 1;
		// #endif

		uni.login({
			provider: platform,
			success: function(code) {
				request(api.loginEmpowerUrl, {
					code: code.code,
					type: type,
				}, 'POST').then(function(res) {
					console.log(res);

					if (res.error == 500003) {
						uni.setStorageSync('is_new_login', res.data);
						resolve(false);
					} else if (res.error == 500001) {
						resolve(false);
					} else {
						uni.setStorageSync('is_new_login', null);
						uni.setStorageSync('user_info', res.data.info);
						uni.setStorageSync('user_token', res.data.token);
						resolve(true);
					}
				});
			},
		});
	})
}



function Newlogin() {

	login().then(function(e) {
		if (e) {
			return true;
		} else {
			return false;
		}
	});

}



function request(url, data = {}, method = "GET") {

	// #ifdef APP
	data['platform'] = 'app';
	// #endif
	// #ifdef MP-WEIXIN
	data['platform'] = 'mp-weixin';
	// #endif
	//data['platform'] = uni.getSystemInfoSync().uniPlatform;
	let haaderObj = {
		'Content-Type': 'application/json'
	}
	//判断是否有登录信息
	let access_token = uni.getStorageSync('access_token');
	if (access_token) {
		haaderObj.Authorization = access_token;
	}
	return new Promise(function(resolve, reject) {
		uni.request({
			url,
			data,
			method,
			header: haaderObj,
			success: function(res) {
				if (res.statusCode == 200) {
					if (res.data.error == 500002) {
						// #ifdef APP || H5
						uni.navigateTo({
							url: '/pages/login/login'
						});
						// #endif
						// #ifndef APP || H5
						login().then(function(e) {
							if (e) {
								request(url, data, method).then((res) => {
									resolve(res);
								})
							} else {
								//uni.setStorageSync('tourl',res.url)
								uni.navigateTo({
									url: '/pages/login/login'
								});
							}
						});
						// #endif
						// getNewToken().then(() => {
						// 	request(url, data, method).then((res) => {
						// 		resolve(res);
						// 	})
						// })
					} else {
						resolve(res.data);
					}
				} else if (res.statusCode == 401) {
					uni.redirectTo({
						url: 'pages/login/login'
					});
				} else {
					reject(res.errMsg);
				}
			},
			fail: function(err) {
				reject(err)
			}
		})
	});
}

function lnkUploadFile(filePath, type = 1) {
	return new Promise(function(resolve, reject) {
		let upUrl = '';
		if (type == 1) {
			upUrl = api.uploadsUrl;
		} else if (type == 3) {
			upUrl = api.uploadsActiveUrl;
		} else {
			upUrl = api.uploadsVideoUrl;
		}
		let key = uni.getStorageSync('is_new_login');
		uni.uploadFile({
			url: upUrl,
			filePath: filePath,
			name: 'file',
			header: {
				'token': uni.getStorageSync('user_token')
			},
			formData: {
				key: key,
				platform: uni.getSystemInfoSync().uniPlatform
			},
			success: function(res) {
				let data = JSON.parse(res.data);
				if (res.statusCode == 200) {
					if (res.data.error == 500002) {
						reject(res.errMsg);
						// getNewToken().then(() => {
						// 	lnkUploadFile(filePath).then((res) => {
						// 		resolve(res);
						// 	})
						// })
					} else {
						resolve(data);
					}
				} else {
					reject(res.errMsg);
				}
			},
			fail: function(err) {
				reject(err)
			}
		})
	});
}

function getNewToken() {
	return new Promise((resolve, reject) => {
		uni.login({
			provider: 'weixin',
			success: function(code) {
				request(api.wxEmpowerUrl, {
					code: code.code
				}, 'POST').then(function(res) {
					uni.setStorageSync('user_info', res.data.info);
					uni.setStorageSync('user_token', res.data.token);
					resolve(true);
				});
			},
		})
	});
}

function loginNow() {
	return new Promise((resolve, reject) => {
		let userInfo = uni.getStorageSync('user_info');
		if (userInfo == '' || userInfo == null || typeof(userInfo) == "undefined") {
			uni.login({
				provider: 'weixin',
				success: function(code) {
					request(api.wxEmpowerUrl, {
						code: code.code
					}, 'POST').then(function(res) {
						uni.setStorageSync('user_info', res.data.info);
						uni.setStorageSync('user_token', res.data.token);
						if (res.data.info.status == 0) {
							uni.reLaunch({
								url: '/pages/setting/err'
							});
						} else {
							resolve(true);
						}
					});
				},
			});
		} else {
			if (userInfo.status == 0) {
				request(api.userRefreshInfoUrl).then(function(res) {
					uni.setStorageSync('user_info', res.data.info);
					uni.setStorageSync('user_token', res.data.token);
					if (res.data.info.status == 0) {
						uni.reLaunch({
							url: '/pages/setting/err'
						});
					} else {
						resolve(true);
					}
				});
			} else {
				resolve(true);
			}
		}
	});
}


/**
 * 
 * @param {*} source 源数组
 * @param {*} count 要取出多少项
 * @param {*} isPermutation 是否使用排列的方式
 * @return {any[]} 所有排列组合,格式为 [ [1,2], [1,3]] ...
 */
function getNumbers(source, count, isPermutation = true) {
	//如果只取一位，返回数组中的所有项，例如 [ [1], [2], [3] ]
	let currentList = source.map((item) => [item]);
	if (count === 1) {
		return currentList;
	}
	let result = [];
	//取出第一项后，再取出后面count - 1 项的排列组合，并把第一项的所有可能（currentList）和 后面count-1项所有可能交叉组合
	for (let i = 0; i < currentList.length; i++) {
		let current = currentList[i];
		//如果是排列的方式，在取count-1时，源数组中排除当前项
		let children = [];
		if (isPermutation) {
			children = getNumbers(source.filter(item => item !== current[0]), count - 1, isPermutation);
		}
		//如果是组合的方法，在取count-1时，源数组只使用当前项之后的
		else {
			children = getNumbers(source.slice(i + 1), count - 1, isPermutation);
		}
		for (let child of children) {
			result.push(current + '_' + child);
			result.push(child + '_' + current)
		}
	}
	return result;
}

//身份证号严格校验
function identityIDCard(code) {
	//身份证号前两位代表区域
	var city = {
		11: "北京",
		12: "天津",
		13: "河北",
		14: "山西",
		15: "内蒙古",
		21: "辽宁",
		22: "吉林",
		23: "黑龙江 ",
		31: "上海",
		32: "江苏",
		33: "浙江",
		34: "安徽",
		35: "福建",
		36: "江西",
		37: "山东",
		41: "河南",
		42: "湖北 ",
		43: "湖南",
		44: "广东",
		45: "广西",
		46: "海南",
		50: "重庆",
		51: "四川",
		52: "贵州",
		53: "云南",
		54: "西藏 ",
		61: "陕西",
		62: "甘肃",
		63: "青海",
		64: "宁夏",
		65: "新疆",
		71: "台湾",
		81: "香港",
		82: "澳门",
		91: "国外 "
	};
	//身份证格式正则表达式
	var idCardReg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
	var errorMsg = ""; //错误提示信息
	var isPass = true; //身份证验证是否通过（true通过、false未通过）

	//如果身份证不满足格式正则表达式
	if (!idCardReg.test(code)) {
		errorMsg = "身份证格式有误！";
		isPass = false;
	}

	//区域数组中不包含需验证的身份证前两位
	else if (!city[code.substr(0, 2)]) {
		errorMsg = "身份证地址编码有误！";
		isPass = false;
	} else {
		//18位身份证需要验证最后一位校验位
		if (code.length == 18) {
			code = code.split('');
			//∑(ai×Wi)(mod 11)
			//加权因子
			var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
			//校验位
			var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
			var sum = 0;
			var ai = 0;
			var wi = 0;
			for (var i = 0; i < 17; i++) {
				ai = code[i];
				wi = factor[i];
				sum += ai * wi;
			}
			var last = parity[sum % 11];
			if (parity[sum % 11] != code[17]) {
				errorMsg = "身份证号不存在！";
				isPass = false;
			}
		}
	}
	var returnParam = {
		'errorMsg': errorMsg,
		'isPass': isPass
	}
	return returnParam;
}

function showShareMenu() {
	// #ifdef MP
	uni.showShareMenu();
	// #endif
}


module.exports = {
	Newlogin,
	showShareMenu,
	userRefreshInfo,
	getMessageCount,
	navigateTo,
	loginNowNew,
	request,
	getNewToken,
	loginNow,
	getNumbers,
	lnkUploadFile,
	identityIDCard,
}