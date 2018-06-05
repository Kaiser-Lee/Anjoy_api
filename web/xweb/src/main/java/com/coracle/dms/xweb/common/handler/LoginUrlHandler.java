package com.coracle.dms.xweb.common.handler;

import java.util.ArrayList;
import java.util.List;

public class LoginUrlHandler {
	
	public static List<String> URL_LIST = new ArrayList<>();
	
	static {
		URL_LIST.add("login/userLogin");
		URL_LIST.add("login/scanlogin");
		URL_LIST.add("login/phoneLogin");
		URL_LIST.add("login/smsValid");
		URL_LIST.add("login/validMsg");
		URL_LIST.add("login/resetPassword");
		URL_LIST.add("/swagger");
		URL_LIST.add("/springfox-swagger-ui");
		URL_LIST.add("/api-docs");
		URL_LIST.add("/resources/upload/files");
		URL_LIST.add("/api/v2/news/detailNoIntercept");
		URL_LIST.add("/api/v2/weiXinPay/wxNotify");
		URL_LIST.add("/api/bms/order/scan");
		URL_LIST.add("/api/v2/paymentReceive/receiveAlipay");
		URL_LIST.add("/api/v2/bankAbcPay/resultToServer");
		URL_LIST.add("/api/v2/news/commentList");
		URL_LIST.add("/api/v2/commonAttachUpload/getFile");
		URL_LIST.add("/api/v2/shortMessage/getValidateCode");
		URL_LIST.add("/api/v2/shortMessage/getValidateCodeByForgetPwd");
		URL_LIST.add("/api/v2/employee/modifyPwdByVilidateCode");
		URL_LIST.add("/api/v2/employee/registerByFirst");
		URL_LIST.add("/api/v2/employee/registerBySecond");
		URL_LIST.add("/api/v2/commonAttachUpload/upload");
		URL_LIST.add("/api/v2/commonAttachUpload/mongoUpload");
		URL_LIST.add("/api/v2/bbs/detail");
		URL_LIST.add("/v2/bbs/findComment");
		URL_LIST.add("/upload/files");
		URL_LIST.add("/xweb/error");
		URL_LIST.add("/api/v1/dms/commonAttachUpload/getFile");
		URL_LIST.add("/api/v1/dms/information/informationDetailApp");
		URL_LIST.add("/api/v1/dms/information/inforsCommentList");
		URL_LIST.add("/api/v1/dms/news/detailNoIntercept");
		URL_LIST.add("/api/v1/dms/news/commentList");
		URL_LIST.add("/api/v1/dms/orderReturn/anjoyReturn");
		URL_LIST.add("/api/v1/dms/order/anjoyAudit");
		URL_LIST.add("/api/v1/dms/orderReturn/anjoyAudit");
		URL_LIST.add("/api/v1/dms/orderDelivery/anjoyInsert");
	}

}
