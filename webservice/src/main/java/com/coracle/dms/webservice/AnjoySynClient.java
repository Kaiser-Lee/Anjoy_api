package com.coracle.dms.webservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.coracle.dms.webservice.locator.EASLoginProxyServiceLocator;
import com.coracle.dms.webservice.locator.WSGuideHrInforServiceFacadeSrvProxyServiceLocator;
import com.coracle.dms.webservice.locator.WSOrderImportFacadeSrvProxyServiceLocator;
import com.coracle.dms.webservice.model.AnjoyOrderModel;
import com.coracle.dms.webservice.model.AnjoyOrderProductModel;
import com.coracle.dms.webservice.model.WSContext;
import com.coracle.dms.webservice.proxy.EASLoginProxy;
import com.coracle.dms.webservice.proxy.WSGuideHrInforServiceFacadeSrvProxy;
import com.coracle.dms.webservice.proxy.WSOrderImportFacadeSrvProxy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class AnjoySynClient {
    private static final Logger logger = LoggerFactory.getLogger(AnjoySynClient.class);
    private static final String USER_NAME = "0057234";
    private static final String PASSWORD = "504359";
    private static final String S_IN_NAME = "eas";
    private static final String DC_NAME = "DEV";
    private static final String LANGUAGE = "L2";
    private static final Integer DB_TYPE = 2;

    /**
     * 登录接口，调用该接口后登录后才能调用其它同步接口
     */
    public static void login() {
        try {
            EASLoginProxyServiceLocator easLoginProxyServiceLocator = new EASLoginProxyServiceLocator();
            EASLoginProxy easLoginProxy = easLoginProxyServiceLocator.getEASLogin();
            WSContext context = easLoginProxy.login(USER_NAME, PASSWORD, S_IN_NAME, DC_NAME, LANGUAGE, DB_TYPE);
            String sessionId = context.getSessionId();
            System.out.println(sessionId);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 数据同步类型：1-组织；2-员工；3-渠道；4-产品分类；5-产品；6-区域；
     */
    public enum SYN_TYPE {
        ORG, EMPLOYEE, CHANNEL, PRODUCT_CATEGORY, PRODUCT, AREA, TEST,
        CHANNEL_ADDRESS, PRODUCT_SPECIFICATION
    }

    /**
     * 根据同步SQL的类型从安井同步数据
     *
     * @param type
     * @return
     */
    public static JSONArray getAnjoySynDataBySQLType(SYN_TYPE type) {
        JSONArray result = null;
        String sql = "";
        switch (type) {
            case ORG:
                sql = AnjoySynSQL.ORGANIZATION_SYN_SQL;
                break;
            case EMPLOYEE:
                sql = AnjoySynSQL.EMPLOYEE_SYN_SQL;
                break;
            case CHANNEL:
                sql = AnjoySynSQL.CHANNEL_SYN_SQL;
                break;
            case PRODUCT_CATEGORY:
                sql = AnjoySynSQL.PRODUCT_CATEGORY_SYN_SQL;
                break;
            case PRODUCT:
                sql = AnjoySynSQL.PRODUCT_SYN_SQL;
                break;
            case AREA:
                sql = AnjoySynSQL.AREA_SYN_SQL;
                break;
            case CHANNEL_ADDRESS:
                sql = AnjoySynSQL.CHANNEL_ADDRESS_SYN_SQL;
                break;
            case PRODUCT_SPECIFICATION:
                sql = AnjoySynSQL.SPECIFICATIONS_SYN_SQL;
                break;
        }
        try {
            login();
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(sql);
            System.out.println(sql+"安井返回数据："+response);
            result = JSONArray.parseArray(response);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 安井经销商信息
     */
    public static JSONObject getChannelInfoByAnjoyId(String anjoyId) {
        JSONObject result = null;
        String sql = AnjoySynSQL.CHANNEL_INFO_SQL;
        sql = sql + " '" + anjoyId + "' ";
        try {
            login();
            logger.info("****************** 安井经销商信息：授信额度、是否锁单、是否超账期 SQL：{} ******************", sql);
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(sql);
            if(StringUtils.isNotBlank(response) && !response.equals("[]")){
                JSONArray array = JSONArray.parseArray(response);
                result = array.getJSONObject(0);
            }
        } catch (Exception e) {
            logger.error("获取安井经销商信息出错：{}", e.getMessage(), e);
        }
        return result;
    }

    /**
     * 经销商剩余额度
     * @param anjoyId
     * @return
     */
    public static JSONObject getCreditLimitByChannelAnjoyId(String anjoyId) {
        JSONObject result = null;
        String sql = AnjoySynSQL.CHANNEL_CREDIT_SQL;
        sql = sql + "'" + anjoyId + "'";
        try {
            login();
            logger.info("****************** 经销商剩余额度 SQL：{} ******************", sql);
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(sql);
            if(StringUtils.isNotBlank(response) && !response.equals("[]")){
                JSONArray array = JSONArray.parseArray(response);
                result = array.getJSONObject(0);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONArray getUpdatePriceData(Long updateTime) {
        JSONArray result = null;
        String sql = AnjoySynSQL.PRODUCT_PRICE;

        sql = String.format(sql,updateTime);

        try {
            login();
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(sql);
            if(!response.equals("[]")){
                result = JSONArray.parseArray(response);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将订单信息同步到安井
     *
     * @param object
     * @return
     */
    public static List<String> pushOrderToAnjoy(JSONArray object) {
        List<String> result = new ArrayList<>();
        try {
            login();
            WSOrderImportFacadeSrvProxyServiceLocator locator = new WSOrderImportFacadeSrvProxyServiceLocator();
            WSOrderImportFacadeSrvProxy service = locator.getWSOrderImportFacade();
            String response = service.orderImportBegin(object.toJSONString());
            System.out.println("---------------- 安井接口返回订单号："+response);
            logger.info("***************安井接口返回订单号：{}*******************",response);
            //安井订单格式  null|AdUAABmCLxDEikI6   用|分割取最后那个
            //result = JSONObject.parseObject(response);
            if(StringUtils.isNotBlank(response)){
                String[] anjoyOrderNumber = response.split("\\|");
                for(String no : anjoyOrderNumber){
                    if(null != no && !"null".equalsIgnoreCase(no.trim())){
                        result.add(no);
                    }
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取余货信息-安井
     * @param date yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static JSONArray getAnjoySurplusGood(String date){
        JSONArray result = null;
        try {
            login();
            StringBuilder sql = new StringBuilder(AnjoySynSQL.PUSH_SURPLUS_GOODS_SYN_SQL);
            sql.append(" '").append(date).append("' ");
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(sql.toString());
            result = JSONArray.parseArray(response);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONArray getAnjoyProductUnit(final String sql){
        JSONArray result = null;
        try {
            login();
            StringBuilder querySqler = new StringBuilder(AnjoySynSQL.PRODUCT_UNIT_SQL);
            querySqler.append(sql);
            logger.info("****************** 产品对应计量SQL：{} ******************", querySqler.toString());
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(querySqler.toString());
            result = JSONArray.parseArray(response);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 同步安井-经销商产品白名单接口
     * @return
     */
    public static JSONArray getAnjoyChannelProductWhiteList(){
        JSONArray result = null;
        try {
            login();
            StringBuilder querySqler = new StringBuilder(AnjoySynSQL.CHANNEL_PRODUCT_WHITE_LIST_SQL);
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(querySqler.toString());
            result = JSONArray.parseArray(response);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 如果没有结果集为空或者第一笔的cfislock==0则不管，如果第一笔的cfislock==1则提示用户该客户已被锁单，不允许下单
     * @param channelAnjoyId
     * @return
     */
    public static JSONObject getAnjoyChannelIsLock(String channelAnjoyId){
        JSONObject result = null;
        try {
            login();
            StringBuilder querySqler = new StringBuilder("");
            querySqler.append("SELECT CFISLOCK, CFTIME FROM (SELECT CFISLOCK, CFTIME FROM CT_MS_BILLLOCK WHERE CFCUSTOMERID = '")
            .append(channelAnjoyId).append("' ORDER BY CFTIME DESC) WHERE ROWNUM = 1");

            logger.info("判断客户是否已被锁单 SQL：{} ", querySqler.toString());
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(querySqler.toString());
            if(StringUtils.isNotBlank(response)){
                result = JSONArray.parseObject(response);
            }
        } catch (Exception e) {
            logger.error("客户是否已锁单异常", e.getMessage());
        }
        return result;
    }

    /**
     * 客户是否超账期
     * @param channelAnjoyCode
     * @return
     */
    public static JSONObject getAnjoyChannelOverAccountPeriod(String channelAnjoyCode){
        JSONObject result = null;
        try {
            login();
            StringBuilder querySqler = new StringBuilder("");
            querySqler.append("select dc.fname_l2 supplierName,dc.fnumber supplierNumber ")
                .append(" , (CASE when to_char(nvl(cr.cfcountenddate,sysdate+1),'yyyy-mm-dd') > to_char(sysdate,'yyyy-mm-dd') ")
                .append(" then 0 else 1 END) isOutEndDate ")
                .append(" from T_BD_Customer dc left join T_SD_CreditFile cr on cr.fcustomerid = dc.fid ")
                .append(" where dc.fnumber = '").append(channelAnjoyCode).append("'");

            logger.info("判断客户是否超账期 SQL：{} ", querySqler.toString());
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(querySqler.toString());
            if(StringUtils.isNotBlank(response)){
                result = JSONArray.parseObject(response);
            }
        } catch (Exception e) {
            logger.error("客户是否超账期", e);
        }
        return result;
    }

    /**
     * 获取安井 经销商-业务员 关系数据
     * lastUpdate：最后更新日期
     * @return
     */
    public static JSONArray getAnjoyChannelEmployee(String lastUpdate){
        JSONArray result = null;
        try {
            login();
            StringBuilder sql = new StringBuilder(AnjoySynSQL.CHANNEL_EMPLOYEE_SYN_SQL);
            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
            String response = service.getBaseData(sql.toString());
            result = JSONArray.parseArray(response);
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 测试方法
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("------ start ------");

        JSONObject channelInfo = getChannelInfoByAnjoyId("AdUAAAAOwmS/DAQO");
        System.out.println(channelInfo.toString());

        //JSONObject jsonObject111 = getCreditLimitByChannelAnjoyId("01.3504.001");
        //System.out.println(jsonObject111.toString());

        /*JSONObject jsonObject6 = getAnjoyChannelOverAccountPeriod("01.1101.003");
        System.out.println(jsonObject6.toString());*/

        /*JSONArray organization = getAnjoySynDataBySQLType(SYN_TYPE.ORG);
        System.out.println("安井组织信息："+organization);*/
        /*JSONArray employee = getAnjoySynDataBySQLType(SYN_TYPE.EMPLOYEE);
        System.out.println("安井人员信息："+employee);*/
        /*JSONArray channel = getAnjoySynDataBySQLType(SYN_TYPE.CHANNEL);
        System.out.println("安井渠道信息："+channel);*/
        /*JSONArray productCategory = getAnjoySynDataBySQLType(SYN_TYPE.PRODUCT_CATEGORY);
        System.out.println("安井产品类别信息："+productCategory);*/
        /*JSONArray product = getAnjoySynDataBySQLType(SYN_TYPE.PRODUCT);
        System.out.println("安井产品信息："+product);*/
        /*JSONArray area = getAnjoySynDataBySQLType(SYN_TYPE.AREA);
        System.out.println("安井区域信息："+area);*/
        /*JSONArray test = getAnjoySynDataBySQLType(SYN_TYPE.TEST);
        System.out.println("安井数据："+test);*/
        /*JSONArray channelAddress = getAnjoySynDataBySQLType(SYN_TYPE.CHANNEL_ADDRESS);
        System.out.println("安井渠道-收货地址数据："+channelAddress);*/
        /*JSONArray orderParam = getOrderJsonObject();
        System.out.println(orderParam.toString());
        List<String> orderNumber = pushOrderToAnjoy(orderParam);
        System.out.println(orderNumber.toString());*/

        /*JSONArray jsonArray = getSurplusGoodsFromAnjoy("2018-01-01");
        System.out.println(jsonArray);*/

//        JSONArray organization = getAnjoySynDataBySQLType(SYN_TYPE.ORG);
//        JSONArray employee = getAnjoySynDataBySQLType(SYN_TYPE.EMPLOYEE);
//        JSONArray channel = getAnjoySynDataBySQLType(SYN_TYPE.CHANNEL);
//        JSONArray productCategory = getAnjoySynDataBySQLType(SYN_TYPE.PRODUCT_CATEGORY);
//        JSONArray product = getAnjoySynDataBySQLType(SYN_TYPE.PRODUCT);
          //JSONArray area = getAnjoySynDataBySQLType(SYN_TYPE.AREA);
        //System.out.println(area.toString());
//          JSONObject credit = getCreditLimitByChannelAnjoyId("03.4201.003");
//        System.out.println(credit);
//          JSONArray array=getUpdatePriceData("2018-02-28 00:00:00");
//        System.out.println(array.toString());
//          JSONArray area = getAnjoySynDataBySQLType(SYN_TYPE.AREA);
//          JSONObject credit = getCreditLimitByChannelAnjoyId("03.4201.003");
//          JSONArray array=getUpdatePriceData("2018-02-01 00:00:00");
//        try {
//            login();
//            WSGuideHrInforServiceFacadeSrvProxyServiceLocator locator = new WSGuideHrInforServiceFacadeSrvProxyServiceLocator();
//            WSGuideHrInforServiceFacadeSrvProxy service = locator.getWSGuideHrInforServiceFacade();
//            String response = service.getBaseData(AnjoySynSQL.ORDER_SQL);
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }

        System.out.println("------ end ------");
    }

    public static JSONArray getOrderJsonObject(){
        JSONArray jsonArray = new JSONArray();
        /** 订单抬头信息 */
        AnjoyOrderModel anjoyOrderModel = new AnjoyOrderModel();
        anjoyOrderModel.setCreator("N0DHycUzRz+EO8OJyK+SKhO33n8=");//创建者
        anjoyOrderModel.setBizDate("2018-02-26 15:05:05");//业务日期(订单审核通过日期)
        anjoyOrderModel.setDescription("单据说明");//DMS订单备注
        //业务类型 FID：d8e80652-010e-1000-e000-04c5c0a812202407435C，普通类型
        anjoyOrderModel.setBizType("d8e80652-010e-1000-e000-04c5c0a812202407435C");
        anjoyOrderModel.setIsInnerSale(false);//是否内部销售
        /** 订货客户：对应DMS渠道 anjoy_id , 安井这边的客户FID AdUAAAAOoMi/DAQO */
        anjoyOrderModel.setOrderCustomer("AdUAAAAOoK6/DAQO");//渠道FID
        //anjoyOrderModel.setDeliveryType("51eb893e-0105-1000-e000-0c00c0a8123362E9EE3F");//交货方式
        anjoyOrderModel.setCurrency("dfd38d11-00fd-1000-e000-1ebdc0a8100dDEB58FDC");//币别：默认人民币
        //anjoyOrderModel.setExchangeRate(new BigDecimal("1.2094"));//汇率
        //anjoyOrderModel.setPaymentType("2fa35444-5a23-43fb-99ee-6d4fa5f260da6BCA0AB5");//付款方式
        //anjoyOrderModel.setSettlementType("e09a62cd-00fd-1000-e000-0b33c0a8100dE96B2B8E");//结算方式
        anjoyOrderModel.setSaleOrgUnit("AdUAAAAAB+DM567U");//销售组织
        anjoyOrderModel.setTotalAmount(new BigDecimal("345.76547"));//抬头金额合计
        anjoyOrderModel.setTotalTax(new BigDecimal("43.64"));//抬头税额合计
        anjoyOrderModel.setTotalTaxAmount(new BigDecimal("54.73"));//抬头价税合计
        //anjoyOrderModel.setPreReceived(new BigDecimal("4312.64"));//已收应收款
        //anjoyOrderModel.setUnPrereceivedAmount(new BigDecimal("234.64"));//未预收款金额
        anjoyOrderModel.setSendAddress("送货地址：深圳市南山区XX街道");//送货地址
        anjoyOrderModel.setLocalTotalAmount(new BigDecimal("534.65"));//抬头金额本位币合计
        anjoyOrderModel.setLocalTotalTaxAmount(new BigDecimal("43.64"));//抬头价税本位币合计
        anjoyOrderModel.setIsInTax(true);//是否含税
        anjoyOrderModel.setCustomerPhone("15814664052");//联系电话
        anjoyOrderModel.setLinkMan("陶凯");//联系人
        //anjoyOrderModel.setIsCentralBalance(null);//是否集中结算
        //anjoyOrderModel.setReceiveCondition("AdUAAAAc9tCu4Nue");//收款条件
        //anjoyOrderModel.setStorageOrgUnit("销售方库存组织");//销售方库存组织
        //anjoyOrderModel.setWarehouse("销售方仓库");//销售方仓库
        anjoyOrderModel.setAdminOrgUnit("1tHH9TVWVECMz9gioAMe+k2COWY=");//归属区域

        /** 订单明细 */
        AnjoyOrderProductModel anjoyOrderProductModel = new AnjoyOrderProductModel();
        /** 物料：对应DMS产品 anjoy_id , 安井这边的物料 FID */
        anjoyOrderProductModel.setMaterial("AdUAAAAH0udECefw");
        /** 计量单位：安井（箱） FID */
        anjoyOrderProductModel.setUnit("AdUAAAAHuOtbglxX");//计量单位（默认箱FID）
        //anjoyOrderProductModel.setRemark("备注");//备注
        //anjoyOrderProductModel.setReasonCode("原因代码");//原因代码
        //anjoyOrderProductModel.setIsPresent(false);//是否赠品
        anjoyOrderProductModel.setQty(new BigDecimal("2911"));//数量
        anjoyOrderProductModel.setAssistQty(new BigDecimal("1"));//辅助单位数量
        anjoyOrderProductModel.setPrice(new BigDecimal("32.5436"));//单价：DMS渠道对应商品单价（含税）
        anjoyOrderProductModel.setTaxPrice(new BigDecimal("35.64"));//含税单价：DMS渠道对应商品单价（含税）
        //anjoyOrderProductModel.setCheapRate(new BigDecimal("1.02"));//减价比例
        //anjoyOrderProductModel.setDiscountCondition(-1);//折扣条件
        //anjoyOrderProductModel.setDiscountType(-1);//折扣方式
        //anjoyOrderProductModel.setDiscountCondition(0);//单位折扣（率）
        //anjoyOrderProductModel.setDiscountAmount(new BigDecimal("64.75"));//折扣额
        anjoyOrderProductModel.setAmount(new BigDecimal("34.657"));//金额：（含税）
        anjoyOrderProductModel.setActualPrice(new BigDecimal("43.65"));//实际单价：（含税）
        anjoyOrderProductModel.setActualTaxPrice(new BigDecimal("64.8"));//实际含税单价（含税）
        anjoyOrderProductModel.setTaxRate(new BigDecimal("17"));//税率
        anjoyOrderProductModel.setTax(new BigDecimal("43.64"));//税额（实际含税单价 * 0.17）
        anjoyOrderProductModel.setTaxAmount(new BigDecimal("5436.343"));//价税合计（含税总价）
        //anjoyOrderProductModel.setSendDate("2018-02-26 16:15:25");//发货日期
        //anjoyOrderProductModel.setDeliveryDate("2018-02-26 16:15:33");//交货日期
        anjoyOrderProductModel.setStorageOrgUnit("AdUAAAAAB+LM567U");//发货组织
        //anjoyOrderProductModel.setUnOrderedQty(new BigDecimal("22"));//未订货数量
        //anjoyOrderProductModel.setQuantityUnCtrl(new BigDecimal("10"));//不控制数量
        //anjoyOrderProductModel.setReason("原因");//原因
        //anjoyOrderProductModel.setIsBetweenCompanySend(null);//是否跨公司发货
        anjoyOrderProductModel.setDeliveryCustomer("AdUAAAAOv8i/DAQO");//送货客户
        anjoyOrderProductModel.setPaymentCustomer("AdUAAAAOod6/DAQO");//收款客户
        anjoyOrderProductModel.setReceiveCustomer("AdUAAAA5Ysi/DAQO");//应收客户
        anjoyOrderProductModel.setSendAddress("送货地址");//送货地址
        anjoyOrderModel.getEntry().add(anjoyOrderProductModel);

        jsonArray.add(anjoyOrderModel);
        return jsonArray;
    }
}
