package com.coracle.dms.constant;

/**
 * dms系统全局常量
 * @author tanyb
 *
 */
public interface DmsModuleEnums {

	//产品标签枚举 1-新品2-推荐3-热销4-爆款
	enum PRODUCT_LABEL{
		NEW(1), RECOMMEND(2), SELLING(3),EXPLOSION(4);
		private final int type;
		PRODUCT_LABEL(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//等级类型枚举
	enum RANK_TYPE{
		RANK_QT(0), RANK_BY(1), RANK_HJ(2),RANK_ZS(3);
		private final int type;
		RANK_TYPE(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
	}
	
	//渠道类型枚举：1-销售公司；2-代理商；3-经销商；4-分销商
	enum CHANNEL_TYPE{
		SALE_COMPANY(0), AGENT(1), DEALER(2), DISTRIBUTOR(3);
		private final int type;
		CHANNEL_TYPE(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
	}
	
	//联系信息关联表类型枚举：1-渠道联系人；2-门店联系人；3-员工
	enum CONTACT_RELATED_TABLE_TYPE{
		CHANNEL_CONTACT(1), STORE_CONTACT(2), EMPLOYEE(3);
		private final int type;
		CONTACT_RELATED_TABLE_TYPE(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
	}
	
	//联系信息联系方式类型枚举：1-手机；2-座机；3-Email；4-QQ；5-微信；6-钉钉
	enum CONTACT_TYPE{
		MOBILE(1), PHONE(2), EMAIL(3), QQ(4), WECHAT(5);
		private final int type;
		CONTACT_TYPE(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
	}
	//备注关联表类型枚举：1-渠道（dms_channel）；2-渠道联系人（dms_channel_contacts）；3-门店（dms_shop）；4-门店店员（dms_seller）
	enum REMARK_RELATED_TABLE_TYPE{
		CHANNEL_MAIN(1), CHANNEL_CONTACT(2), STORE_SHOP(3), STORE_SELLER(4);
		private final int type;
		REMARK_RELATED_TABLE_TYPE(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
	}
	//附件关联表类型枚举：1-渠道（dms_channel）；2-渠道联系人（dms_channel_contacts）；3-门店（dms_shop）；4-门店店员（dms_seller）；
	//5-订单评价（dms_order_evaluation）；6-转账凭证（dms_order_payment）
	enum ATTACHMENT_RELATED_TABLE_TYPE{
		CHANNEL_MAIN(1), CHANNEL_CONTACT(2), STORE_SHOP(3), STORE_SELLER(4), ORDER_PRODUCT_EVALUATION(5), TRANSFER_VOUCHER(6);
		private final int type;
		ATTACHMENT_RELATED_TABLE_TYPE(int type) {
            this.type = type;
        }
        public int getType() {
            return type;
        }
	}

	//账号来源：1-员工(dms_employee)；2-渠道商(dms_channel_contacts)；3-门店(dms_seller)
	enum ACCOUNT_SOURCE_TYPE {
		EMPLOYEE(1), CHANNEL_CONTACTS(2), SELLER(3);
		private final int type;
		ACCOUNT_SOURCE_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//账号状态：0-禁用；1-启用；2-待审核
	enum ACCOUNT_STATUS_TYPE {
		INVALID(0), VALID(1), UNCHECKED(2);
		private final int type;
		ACCOUNT_STATUS_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	/**
	 * 有效性
	 * 1：有效、启用、在职、发布、是、
	 * 0：失效、无效、禁用、离职、撤回、否
	 */
	enum ACTIVE_STATUS {
		DIABLE(0), ENABLE(1);
		private final int type;
		ACTIVE_STATUS(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//岗位（1店长；0店员）
	enum STORE_POST {
		EMPLOYEE(0), MANAGER(1);
		private final int type;
		STORE_POST(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//新闻资讯类型（0新闻资讯包括下面两个都是走数据字典,）
	enum NEWS_TYPE {
		NEWS("0");
		private final String type;
		NEWS_TYPE(String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}
	//通知类型（0通知，1促销）
	enum NOTICE_TYPE {
		NOTICE(0),PROMOTION(1);
		private final int type;
		NOTICE_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//渠道赋能类型类型（0渠道政策，1理论学习）
	enum CHANNEL_INFOMATION_TYPE {
		POLICY(0),STUDY(1);
		private final int type;
		CHANNEL_INFOMATION_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//审批状态（0待提交，1待审批，2审批通过，3审批不通过）
	enum APPROVE_STATUS {
		APPROVE_STATUS_DTJ(0),APPROVE_STATUS_DSP(1),APPROVE_STATUS_TG(2),APPROVE_STATUS_BTG(3);
		private final int type;
		APPROVE_STATUS(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	public final static String PROXY_TEMPLATE_KEY = "proxyTemplate";

	//home_images图片类型（0首页banner图）
	enum HOME_IMAGES_TYPE {
		BANNER(0);
		private final int type;
		HOME_IMAGES_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//有效性（1已删除,是；0未删除）
	enum REMOVE_FLAG_STATUS {
		NOREMOVE(0), REMOVE(1);
		private final int type;
		REMOVE_FLAG_STATUS(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//调货类型 1-渠道铺货；2-门店之间调货；3-门店向渠道调货
	enum TRANSFER_APPLY_TYPE {
		CHANNEL_PH(1), STORE_DH(2),CHANNEL_DH(3);
		private final int type;
		TRANSFER_APPLY_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//处理状态1-已处理；0-未处理
	enum PROCESS_STATUS {
		UNPROCESS(0), PROCESSED(1);
		private final int type;
		PROCESS_STATUS(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	
	// 我发起的-发货状态 1-等待对方发货；2-待收货；3-已完成；4-取消
	enum DELIVERY_STATUS {
		WAIT(1), WAIT_DELIVERY(2), FINISH(3), CANCEL(4);
		private final int type;

		DELIVERY_STATUS(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	// 我收到的-收货状态（1-待发货；2-已发货；3-已完成）；4-取消
	enum RECEIVE_STATUS {
		WAIT(1), RECEIVED(2), FINISH(3) ,CANCEL(4);
		private final int type;

		RECEIVE_STATUS(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	// 订单状态：1-待审核；2-待发货(待厂商发货)；3-待客户收获(待收货)；4-已完成；5-已取消。 注：标注的是厂商端的状态，括号中是订货端的状态
	enum ORDER_STATUS_TYPE {
	    AUDIT_AWAIT(1), DELIVER_AWAIT(2), RECEIVE_AWAIT(3), FINISHED(4), CANCEL(5);
        private final int type;
	    ORDER_STATUS_TYPE(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}

	// 订单收款状态：1-未付款；2-待确认收款；3-部分到账；4-已收款。
	enum PAYMENT_STATUS_TYPE {
		UNPAY(1), CONFIRM_AWAIT(2), PART_PAID(3), FINISHED(4);
		private final int type;
		PAYMENT_STATUS_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	// 订单收款确认状态：0-未确认；1-已确认
	enum PAYMENT_CONFIRM_TYPE {
	    UNCONFIRMED(0), CONFIRMED(1);
		private final int type;
		PAYMENT_CONFIRM_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
		    return type;
        }
	}


	// 订单支付类型：1-对公转账；2-在线支付
	enum PAYMENT_TYPE {
		PUBLIC_TRANSFER(1), ONLINE_PAYMENT(2);
		private final int type;
		PAYMENT_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	enum MESSAGE_TYPE {
		// 消息类型状态：0-普通消息；1-公告；2-促销；3-订单；4-要货；5-物流；6-卡券；7-账号开通申请；8增值税发票审核；9-退货；
		MESSAGE_SYSTEM(0),MESSAGE_NOTICE(1), MESSAGE_PROMOTION(2), MESSAGE_ORDER(3), MESSAGE_GOODS(4), MESSAGE_LOGISTICE(5), MESSAGE_COUPONS(6), MESSAGE_ACCOUNT(7), MESSAGE_INVOICE(8),MESSAGE_RETURN(9);
		private final int type;
		MESSAGE_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	enum MESSAGE_ENTITY_TYPE {
		// 消息实体类型：0-未获取到实体类型,系统消息；1-dms_notice；2-dms_order；3-dms_transfer_apply；4-dms_Invoice；5-dms_user；6-dms_order_return；
		SYS_MESSAGE(0),DMS_NOTICE(1), DMS_ORDER(2), DMS_TRANSFER_APPLY(3),DMS_INVOICE(4),DMS_USER(5),DMS_ORDER_RETURN(6);
		private final int type;
		MESSAGE_ENTITY_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	enum TREE_RELATED_TYPE {
		//  类型状态：0-未获取到实体类型；1-dms_channel:渠道；2-dms_store：门店；3-dms_organization：组织；4-dms_sys_area：区域；5-；6-
		NO_ENTITY(0),DMS_CHANNEL(1), DMS_STORE(2), DMS_ORGANIZATION(3), DMS_AREA(4);
		private final int type;
		TREE_RELATED_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	enum NEWS_RELATED_TYPE {
		//  资讯类型状态：0-未获取到实体类型；1-dms_news:新闻；2-dms_notice：公告；3-dms_channel_information：渠道赋能；4-；5-；6-
		NO_ENTITY(0),DMS_NEWS(1), DMS_NOTICE(2), DMS_CHANNEL_INFORMATION(3);
		private final int type;
		NEWS_RELATED_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}
	//退换货类型（1-退换；2-换货）
	enum RETURN_ORDER_TYPE {
	    RETURN(1), CHANGE(2);
        private final int type;
        RETURN_ORDER_TYPE(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}
	//退换货配送类型（1-渠道收货；2-品牌商收货）
	enum RETURN_ORDER_DELIVERY_TYPE {
		CHANNEL_RECEIVERY(1), BRAND_RECEIVERY(2);
        private final int type;
        RETURN_ORDER_DELIVERY_TYPE(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}
	//退换货审核状态   0-待审核；1-已审核；2-拒绝
	enum RETURN_AUDIT_STATUS {
		WAIT_PEND(0), AUDITED(1),REFUSE(2);
        private final int type;
        RETURN_AUDIT_STATUS(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}
	//退换货订货端状态（1-待审核；2-待退货；3-退货中（待厂商收货）；4-待发货；5-待收货；6-已完成；7-交易取消；8-待确认退款）
	enum RETURN_BUYER_STATUS {
		WAIT_PEND(1), WAIT_RETURN(2), RETURNING(3), WAIT_DELIVERY(4), WAIT_RECEIVERY(5), FINISHED(6), CANCEL(7),WAIT_PAY(8);
		private final int type;

		RETURN_BUYER_STATUS(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}
	
	//品牌商状态（1-待审核；2-待客户发货；3-待收货；4-待发货；5-待客户收货；6-已完成；7-取消；8-待确认退款）
	enum RETURN_SELLER_STATUS {
		WAIT_PEND(1), WAIT_CUSTOMER_DELIVERY(2), WAIT_RECEIVERY(3), WAIT_DELIVERY(4), WAIT_CUSTOMER_RECEIVERY(5), FINISHED(6), CANCEL(7),WAIT_PAY(8);
		private final int type;

		RETURN_SELLER_STATUS(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	//发票类型：1-普通发票；2-增值税发票
	enum INVOICE_TYPE {
		NORMAL(1), VAT(2);
		private final int type;

		INVOICE_TYPE(int type) {
			this.type = type;
		}

		public int getType() {
			return type;
		}
	}

	//仓库类型（1-品牌商；2-渠道；3-门店）
	enum STORAGE_TYPE {
		BRAND(1), CHANNEL(2),STORE(3);
        private final int type;
        STORAGE_TYPE(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}

	//关联单据类型（1-调货 2-订单发货 3- 退换货（渠道到品牌商）4-退换货（品牌商到渠道）
	enum RELATION_TYPE {
		TRANSFER(1), SEND(2), RETURN_CHANNEL(3),RETURN_BRAND(4);
		private final int type;
		RELATION_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//出入库(1出库，2入库)
	enum STORAGE_OUTIN {
		OUT(1), IN(2);
		private final int type;
		STORAGE_OUTIN(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//出入库类型(1采购入库，2采购退货，3销售出库，4销售退货，5调拨出库，6调拨入库，7库存调整，8其他入库)
	enum STORAGE_OUTIN_TYPE {
		PUT_IN(1), PUT_OUT(2),SALE_OUT(3),SALE_RETURN(4), ALLOCATION_OUT(5), ALLOCATION_IN(6),INVENTORY_ADJUSTMENT(7),OTHER_IN(8);
		private final int type;
		STORAGE_OUTIN_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//审核意见：0-拒绝；1-同意
	enum AUDIT_OPINION_TYPE {
	    DISAGREE(0), AGREE(1);
	    private final int type;
	    AUDIT_OPINION_TYPE(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}

	//发货单类型：1-订单发货；2-退换货单发货；3-退换货
	enum ORDER_DELIVERY_TYPE {
		ORDER(1), ORDER_RETURN(2),TRANSFER(3);
		private final int type;
		ORDER_DELIVERY_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//门店运营方式类型：0-直营；1-分销
	enum STORE_OPERATE_WAY {
	    DIRECT(0), DISTRIBUTE(1);
		private final int type;

		STORE_OPERATE_WAY(int type) {
			this.type = type;
		}

		public int getType() {
		    return type;
		}
	}

	//促销活动状态：0-失效；1-有效
	enum PROMOTION_ACTIVE_TYPE {
		INVALID(0), VALID(1);
		private final int type;

		PROMOTION_ACTIVE_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//促销适用范围类型：0-区域；1-渠道
	enum PROMOTION_SCOPE_TYPE {
		AREA(0), CHANNEL(1);
		private final int type;

		PROMOTION_SCOPE_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//购物车数据类型：1-购物车；2-已结算的购物车数据；3-手动添加的常购商品
	enum SHOPPINT_CART_TYPE {
		SHOPPING_CART(1), BALANCED(2), MANUAL(3);
		private final int type;

		SHOPPINT_CART_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}

	//订单来源：1-APP下单
	enum ORDER_SOURCE {
	    APP(1);
	    private final int type;

	    ORDER_SOURCE(int type) {
	    	this.type = type;
		}
		public int getType() {
	    	return type;
		}
	}

	//订单类型：1-常规订单；2-预订单；3-特价订单
	enum ORDER_TYPE {
		NORMAL(1), PRE(2), SPECIAL(3);
		private final int type;

		ORDER_TYPE(int type) {
			this.type = type;
		}
		public int getType() {
			return type;
		}
	}


	//需要生成code的key值
	//门店编码
	public final static String STORE_SERIAL_KEY = "store_serial_key";
	//渠道编码
	public final static String CHANNEL_SERIAL_KEY = "channel_serial_key";
	//订单，含退换货订单
	public final static String ORDER_SERIAL_KEY = "order_serial_key";
	//销售出库单(
	public final static String SELL_OUT_SERIAL_KEY = "sell_out_serial_key";
	//销售退货
	public final static String SALES_RETURNS_SERIAL_KEY = "sales_returns_serial_key";
	//采购入库单
	public final static String PURCHASE_IN_STORAGE_SERIAL_KEY = "purchase_in_storage_serial_key";
	//其他入库单
	public final static String OTHER_IN_STORAGE_SERIAL_KEY = "other_in_storage_serial_key";
	//库存调整
	public final static String INVENTORY_ADJUSTMENT_SERIAL_KEY = "inventory_adjustment_serial_key";
	//（门店）调货单
	public final static String TRANSFER_SERIAL_KEY = "transfer_serial_key";
	//促销活动编号
	public final static String PROMOTION_SERIAL_KEY = "promotion_serial_key";
	//退货单号
	public final static String BACK_GOODS_SERIAL_KEY = "back_goods_serial_key";
	//树形结构层级：渠道
	public final static String TREE_LEVEL_CHANNEL = "channel";
	//树形结构层级：门店
	public final static String TREE_LEVEL_STORE = "store";

	//excel 导出文件的存放地址
	public static final String excelAddress ="/excel";
	//服务器后台保存文件地址 begin
	public static final String BASEADDRESS="/uf";

	//省
	public static final String PROVINCE = "province";
	//市
	public static final String CITY = "city";
	//区
	public static final String COUNTY = "county";


	//数据字典-渠道类型
	public static final String DICT_CHANNEL_TYPE = "channel_type";
	//数据字典-渠道等级
	public static final String DICT_CHANNEL_RANK = "channel_rank";

	public static final String DEFAULT_PASSWORD = "123456";

}
