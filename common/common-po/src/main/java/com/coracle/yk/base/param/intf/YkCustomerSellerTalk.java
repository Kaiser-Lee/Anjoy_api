package com.coracle.yk.base.param.intf;

import java.util.Date;

public interface YkCustomerSellerTalk {
	void setSellerId(String sellerId);
	void setSentByCustomer(Short sent);
	void setMsgType(String type);
	void setIsRead(Short read);
	void setCreatedDate(Date date);
}
