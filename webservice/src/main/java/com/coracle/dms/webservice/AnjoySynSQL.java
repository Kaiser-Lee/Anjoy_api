package com.coracle.dms.webservice;

public class AnjoySynSQL {

    /**
     * 同步组织机构
     * fid：唯一，严格区分大小写
     * FNumber：唯一 code
     name	String	组织名称	FNAME_L2
     isEnable	boolean	是否有效	当前日期（getdate()）和失效日期（FParentID）比较  0生效  1失效    state
     supId	Long	上级组织ID	FParentID
     */
    public static final String ORGANIZATION_SYN_SQL = "SELECT fid,FNumber,fname_l2,case when getdate()>FInvalidDate then 0 else 1 end state,FParentID FROM T_ORG_BaseUnit";

    /**
     * 同步员工
     anjoy_id	Long	人员ID	fid
     loginName	String	登录账号-唯一	fnumber
     name	String	人员名称	fname_l2
     isEnable	boolean	是否有效	FDeletedStatus   普通=1,作废=2
     orgId	Long	所在组织ID	FHROrgUnitID
     mail	String	邮箱地址	FEMail  电子邮箱
     telephone	String	电话，座机	FOfficePhone  办公室电话
     phone	String	手机号码	FCell
     address	String	员工地址	FIDCardAddress  身份证地址
     */
    public static final String EMPLOYEE_SYN_SQL = "select fid,fnumber,fname_l2,FDeletedStatus,FHROrgUnitID,FEMail, FOfficePhone,Fcell,FIDCardAddress from view_bufenyuangonxinxi";

    /**
     * 同步渠道数据
     */
    public static final String CHANNEL_SYN_SQL = "select * from view_jingxiaoshang where 1=1 ";

    /**
     * 同步渠道-收货人数据
     */
    public static final String CHANNEL_ADDRESS_SYN_SQL =
            /*
            "select DISTINCT a.fid CUSFID, a.fnumber CUSFnumber,a.fname_l2 CUSNAME, \n" +
            "e.fname_l2 销售组织名称 , c.fid, c.fnumber, c.FADDRESS_L2 送货地址,f.FCONTACTPERSON 联系人 ,f.fmobile 联系人手机号  \n" +
            "from T_BD_Customer a \n" +
            "inner join T_BD_CUSTOMERSALEINFO b on a.fid=b.fcustomerid \n" +
            "inner join  T_BD_CUSTOMERDLVADDRESS c on c.FCUSTOMERSALEINFOID =b.fid \n" +
            "inner join T_CON_ContractInfoOrderEntry d on d.FCUSTOMERCODEId =a.fid \n" +
            "inner join T_ORG_BaseUnit e on e.fid=d.cfcontractsignid \n" +
            "inner join T_BD_CUSTOMERLINKMAn f on f.fcustomersaleid=b.fid \n" +
            "where f.CFISSEND <> '1' and cfisdzdsend <> '1' ";
            */
            "select a.fid 客户ID, a.fnumber 客户代码, TRIM(c.FADDRESS_L2) 送货地址\n" +
            ",TRIM(d.fcontactperson) 联系人, d.fmobile 手机, d.fphone 电话\n" +
            //",t1.fname_l2 合同签订销售组织,a1.fnumber 收款客户代码,a1.fname_l2 收款客户名称\n" +
            //",a2.fnumber 应收客户代码,a2.fname_l2 应收客户名称,a3.fnumber 送货客户代码,\n" +
            //"a3.fname_l2 送货客户名称,cfissend,cfisdzdsend\n" +
            "FROM T_BD_CUSTOMER a \n" +
            "INNER JOIN (\n" +
            "select b1.fcustomercodeid,max(b1.fcontractbegindate)fcontractbegindate,max(cfcontractsignid)cfcontractsignid from \n" +
            "T_CON_ContractInfoOrderEntry b1 \n" +
            "inner join  T_CON_ContractInfoOrder a1 on b1.FPARENTID =a1.fid\n" +
            "group by b1.fcustomercodeid\n" +
            ") xy on xy.fcustomercodeid=a.fid\n" +
            "inner join T_ORG_BaseUnit t1 on xy.cfcontractsignid=t1.fid\n" +
            "inner join T_BD_CUSTOMERSALEINFO b on b.FCUSTOMERID =xy.fcustomercodeid and b.fsaleorgid=xy.cfcontractsignid\n" +
            "left join T_BD_CUSTOMERDLVADDRESS c on c.FCUSTOMERSALEINFOID=b.fid\n" +
            "left join T_BD_CUSTOMERLINKMAN d on d.fcustomersaleid=b.fid\n" +
            "inner join T_BD_CUSTOMER a1 on a1.fid=b.fsettlementorgunitid \n" +
            "inner join T_BD_CUSTOMER a2 on a2.fid=b.fbillingorgunitid \n" +
            "inner join T_BD_CUSTOMER a3 on a3.fid=fdeliverorgunitid\n" +
            "where b.fusingstatus = 0 \n" +
            "AND d.fcontactperson IS NOT NULL \n" +
            "AND c.FADDRESS_L2 IS NOT NULL \n" +
            "AND (\n" +
            "    (d.cfissend is null and d.cfisdzdsend is null) or (d.cfissend='0' and d.cfisdzdsend='0')\n" +
            "    OR (d.cfissend = '0'and d.cfisdzdsend is null) or (d.cfissend is null and d.cfisdzdsend='0')\n" +
            ")\n" +
            "order by a.fnumber\n";

    /**
     * 同步 渠道-业务员 数据
     */
    public static final String CHANNEL_EMPLOYEE_SYN_SQL = "select d.fid, a.FCUSTOMERNAME ,b.fnumber as customernumer," +
            "d.fname_l2 as AssessmentSaleName,\n" +
            "d.fnumber as AssessmentSaleNumber\n" +
            "from T_AP_Assessmentperson a\n" +
            "inner join T_BD_Customer b on a.FCUSTOMERNUMBERID = b.fid\n" +
            "inner join T_AP_AssessmentpersonAssEntry c on a.fid = c.FPARENTID \n" +
            "inner join T_BD_Person d on c.FASSESSMENTSALEID = d.fid";

    /**
     * 同步产品类别
     */
    public static final String PRODUCT_CATEGORY_SYN_SQL = "select FID, fnumber, fname_l2, FDeletedStatus, FDESCRIPTION_L2, FParentID from  view_shangpinfenlei";

    /**
     * 同步产品信息
     */
    public static final String PRODUCT_SYN_SQL = //"SELECT * FROM VCT_CUSTITEM_NEWCUSTITEM_NEW T";
        "select FID,FNUMBER,FNAME_L2,FStatus,FMATERIALGROUPID,FDescription_L2,FUNIT from view_shangpinxinxi";

    /** 不需要同步 */
    public static final String PRODUCT_UNIT_SQL = "SELECT d.fname_l2 产品状态,b.fnumber 产品代码,B.FNAME_L2 产品名称, " +
            " C.FNAME_L2 计量单位,  A.物料ID, A.计量单位ID, " +
            " A.与基本计量单位换算率, A.是否统计单位, A.是否基本计量单位 " +
            " FROM " +
            " vex_eas_物料多计量单位 A " +
            " INNER JOIN T_BD_MATERIAL b ON b.fid=a.物料ID " +
            " INNER JOIN T_BD_MEASUREUNIT C ON C.FID=A.计量单位ID " +
            " INNER JOIN CT_CUS_SubMessage d ON d.fid=b.cfcpztid " +
            " WHERE B.FNUMBER LIKE '00%' AND d.fname_l2 = '正常' "
            ;
    /**
     * 同步经销商白名单的SQL语句
     */
    public static final String CHANNEL_PRODUCT_WHITE_LIST_SQL =
            /*"SELECT a.cfqdnumber 渠道编码,d.fnumber 客户编码,d.fname_l2 客户名称\n" +
            ",c.fnumber 物料编码,c.fname_l2 物料名称,b.cfspecification 规格型号\n" +
            ",b.cfiseffective 是否生效,b.cfcontrolstate 控制状态\n" +
            */
            "SELECT d.fnumber 客户编码,c.fnumber 物料编码\n" +
            "FROM CT_BC_JXSBMD a\n" +
            "LEFT JOIN CT_BC_JXSBMDEntry b ON a.fid=b.fparentid\n" +
            "LEFT JOIN T_BD_Material c ON b.cfmaterielnumberid=c.fid\n" +
            "LEFT JOIN T_BD_Customer d ON b.cfcustomernumberid=d.fid\n" +
            "where b.cfiseffective='生效' and b.cfcontrolstate='可销'\n" +
            "order by a.cfqdnumber,d.fnumber,c.fnumber"
    ;

    /**
     * 同步区域信息
     */
    public static final String AREA_SYN_SQL = "select FID,FNUMBER,FNAME_L2,CFGLDQID,CFQYID from CT_CUS_BIBSC";

    /**
     * 获取余货信息（已作废）
     */
    public static final String PUSH_SURPLUS_GOODS_SYN_SQL = "select FSaleOrderNumber,FQTY,FNUMBER,CFBizDate,CFCarryNumber  from view_yuhuoxinxi where CFBizDate > ";

    /**
     * 获取渠道额度数据（不需要同步）
     */
    public static final String CHANNEL_CREDIT_SQL = "select fAmount,balance,qk,saleamount,cfcountenddate from view_kehuxinyon where fid=";

    /**
     * 同步产品价格数据
     */
    public static final String PRODUCT_PRICE = "select a.customernumber,a.materialnumber,a.fprice,cfqidingl from SP_ANJOY_DMS_PRICE a where a.PRICEUPDTIME>%s";

    /**
     * 同步产品规格信息
     */
    public static final String SPECIFICATIONS_SYN_SQL = "select B.FNAME_L2 SPE ,A.FNUMBER CODE  ,A.FMODEL SPEMODEL from T_BD_MATERIAL A\n" +
            "inner JOIN CT_CUS_SubMessage C ON C.FID=A.CFCPZTID\n" +
            "inner  join CT_CUS_SubMessage B ON B.FID=A.CFBZDLID\n" +
            "WHERE A.FNUMBER  LIKE '00%' AND C.FNAME_L2='正常'";


    /**
     * 通过安井渠道fid获取授信额度、是否锁单、是否超账期 等信息
     */
    public static final String CHANNEL_INFO_SQL =
            /*
            "SELECT V.FAMOUNT, V.BALANCE, V.QK, V.SALEAMOUNT, V.CFCOUNTENDDATE " +
            " , (SELECT CASE " +
            " WHEN TO_CHAR(NVL(CR.CFCOUNTENDDATE, SYSDATE + 1), 'yyyy-mm-dd') > TO_CHAR(SYSDATE, 'yyyy-mm-dd') THEN 0 " +
            " ELSE 1 END FROM T_SD_CREDITFILE CR WHERE CR.FCUSTOMERID = V.FID " +
            " ) AS ISOUTENDDATE " +
            " , NVL(( " +
            " SELECT TV.CFISLOCK FROM ( " +
            " SELECT T.CFISLOCK, T.CFCUSTOMERID, T.CFTIME, ROW_NUMBER() OVER(PARTITION BY T.CFCUSTOMERID " +
            " ORDER BY T.CFTIME DESC) RN " +
            " FROM CT_MS_BILLLOCK T " +
            " ) TV WHERE TV.CFCUSTOMERID = V.FID AND RN = 1 " +
            " ), 0) ISLOKED " +
            " FROM VIEW_KEHUXINYON V WHERE V.FID = "
            */
            " SELECT * FROM view_kehu_info t WHERE t.fid = "
        ;


}
