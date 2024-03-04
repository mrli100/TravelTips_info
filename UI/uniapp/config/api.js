const AppName = 'lnksns'; //你的APP名称
const AppTitle = 'lnksns'; //你的APP标题
const AppCopyrightCn = '邻客互动 版权所有'; //设置中显示的信息
const AppCopyrightEn = 'Copyright ©️ www.lnksns.vip All Rights Reserved.'; //设置中显示的信息
// const RootUrl = 'https://api.traveltips.com.cn'; //后端接口地址
const RootUrl = 'http://localhost:8082'; //后端接口地址
const ApiRootUrl = RootUrl + '/';
const ImgRootUrl = RootUrl + '/static/images/';
const ApiAppCenterUrl = RootUrl + '/appcenter/api/'; //插件

module.exports = {
	//登陆
	loginEmpowerUrl: ApiRootUrl + 'sys/auth/login',
	//刷新token地址
	user_token_refreshToken: ApiRootUrl + 'sys/auth/token?refreshToken=',
	//获取用户信息
	getUserInfo: ApiRootUrl + 'v1/user/info',
	getTravelUserInfo: ApiRootUrl + 'travelV1/user/travelInfo',
	// 获取配置
	configUrl: ApiRootUrl + 'travel/t_app_config/get',
	
	
	
	// 微信授权登录
	wxEmpowerUrl: ApiRootUrl + 'user/wx_empower',
	// 刷新用户资料
	userRefreshInfoUrl: ApiRootUrl + 'user/user_refresh_info',
	// 编辑用户资料
	editUserInfoUrl: ApiRootUrl + 'user/edit_user_info',
	// 用户绑定手机号
	userBindMobileUrl: ApiRootUrl + 'user/user_bind_mobile',
	// 用户刷新IP
	userRefreshIpUrl: ApiRootUrl + 'user/user_refresh_ip',
	// 顶部推荐圈子
	getTopCircleUrl: ApiRootUrl + 'circle/get_top_circle',
	// 推荐圈子列表
	getCircleListUrl: ApiRootUrl + 'circle/get_circle_list',
	// 圈子详情
	getCircleDetailsUrl: ApiRootUrl + 'circle/get_circle_details',
	// 动态圈子
	dynamicCircleUrl: ApiRootUrl + 'circle/dynamic_circle',
	// 关注圈子
	followCircleUrl: ApiRootUrl + 'circle/follow_circle',
	// 编辑动态
	saveDynamicUrl: ApiRootUrl + 'dynamic/save_dynamic',
	// 用户圈子
	userCircleUrl: ApiRootUrl + 'circle/user_circle',
	// 用户详情
	userDetailsUrl: ApiRootUrl + 'user/user_details',
	// 用户数据
	userPublishContentUrl: ApiRootUrl + 'user/user_publish_content',
	// 关注用户
	followUserUrl: ApiRootUrl + 'user/follow_user',
	// 用户关注数据
	userFollowUrl: ApiRootUrl + 'user/user_follow',
	// 动态数据
	getDynamicInfoUrl: ApiRootUrl + 'dynamic/get_dynamic_info',
	// 推荐动态
	recommendDynamicUrl: ApiRootUrl + 'dynamic/recommend_dynamic',
	// 删除动态
	delDynamicUrl: ApiRootUrl + 'dynamic/del_dynamic',
	// 圈子动态
	getCircleDynamicUrl: ApiRootUrl + 'circle/get_circle_dynamic',
	// 圈子粉丝
	circleFansUrl: ApiRootUrl + 'circle/circle_fans',
	// 动态详情
	dynamicDetailsUrl: ApiRootUrl + 'dynamic/dynamic_details',
	// 喜欢动态
	likeDynamicUrl: ApiRootUrl + 'dynamic/like_dynamic',
	// 评论动态
	commentDynamicUrl: ApiRootUrl + 'comment/comment_dynamic',
	// 动态评论
	dynamicCommentUrl: ApiRootUrl + 'comment/dynamic_comment',
	// 评论的评论
	sonCommentUrl: ApiRootUrl + 'comment/son_comment',
	// 删除评论
	delCommentUrl: ApiRootUrl + 'comment/del_comment',
	// 消息列表
	getMessageUrl: ApiRootUrl + 'message/get_message',
	// 读消息
	readMessageUrl: ApiRootUrl + 'message/read_message',
	// 未读消息数量
	getMessageCountUrl: ApiRootUrl + 'message/get_message_count',
	// 用户通知
	messageCountUrl: ApiRootUrl + 'message/user_message_count',
	// 获取条款
	clauseUrl: ApiRootUrl + 'clause/details',

	// 上传图片接口
	uploadsUrl: ApiRootUrl + 'upload/image',
	// 上传视频接口
	uploadsVideoUrl: ApiRootUrl + 'upload/video',
	// 配置
	insetUrl: ImgRootUrl + 'inset/',
	emojiUrl: ImgRootUrl + 'emoji/',
	// 随机推送动态
	randDynamicUrl: ApiRootUrl + 'index/rand_dynamic',
	// 签到打卡
	userSignInUrl: ApiRootUrl + 'integral/user_sign_in',
	// 用户积分
	userIntegealRecordUrl: ApiRootUrl + 'integral/user_integeal_record',
	// 反馈
	saveFeedbackUrl: ApiRootUrl + 'index/save_feedback',
	// 聚合搜索
	searchUrl: ApiRootUrl + 'index/search',
	// 聚合搜索数量
	searchCountUrl: ApiRootUrl + 'index/search_count',
	//插件
	appCenterApi: ApiAppCenterUrl + 'appmod',
	// 登陆保存用户信息
	loginUsersInfo: ApiRootUrl + 'user/login_users_info',
	// 登陆时上传头像
	uploadsActiveUrl: ApiRootUrl + 'upload/login_users_image',
	appName: AppName,
	appTitle: AppTitle,
	appCopyrightCn: AppCopyrightCn,
	appCopyrightEn: AppCopyrightEn,
};