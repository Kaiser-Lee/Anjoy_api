package com.coracle.dms.service.impl;

import com.coracle.dms.dao.mybatis.DmsProductAreaMapper;
import com.coracle.dms.dao.mybatis.DmsProductCategoryMapper;
import com.coracle.dms.dao.mybatis.DmsReportFormsMapper;
import com.coracle.dms.dto.DmsSellNumByCategoryDto;
import com.coracle.dms.po.DmsChannel;
import com.coracle.dms.po.DmsChannelContacts;
import com.coracle.dms.po.DmsProductCategory;
import com.coracle.dms.service.*;
import com.coracle.yk.xframework.common.exception.runtime.ServiceException;
import com.coracle.yk.xframework.po.UserSession;
import com.coracle.yk.xframework.util.BlankUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/9/21.
 */
@Service
public class DmsReportFormsServiceImpl implements DmsReportFormsService {
    private static final Logger logger = Logger.getLogger(DmsReportFormsServiceImpl.class);
    @Autowired
    private DmsProductCategoryMapper dmsProductCategoryMapper;
    @Autowired
    private DmsReportFormsMapper dmsReportFormsMapper;
    @Autowired
    private DmsProductAreaMapper dmsProductAreaMapper;
    @Autowired
    private DmsSellerService dmsSellerService;
    @Autowired
    private DmsChannelContactsService dmsChannelContactsService;
    @Autowired
    private DmsChannelService dmsChannelService;
    @Autowired
    private DmsStoreService dmsStoreService;

    public Map<String, Object> getTop5ForProductCategoryByAPP(UserSession userSession, Integer type) {
        List<DmsSellNumByCategoryDto> dtoList = new ArrayList<>();

        if (BlankUtil.isEmpty(type)) type = 2;//todo 默认值待确定
        String startDate = getStartDate(type);

        DmsProductCategory dmsProductCategory = new DmsProductCategory();
        dmsProductCategory.setParentId(1L);//一级节点父ID为1
        dmsProductCategory.setRemoveFlag(0);
        dmsProductCategory.setActive(1);
        List<DmsProductCategory> dmsProductCategoryList = dmsProductCategoryMapper.selectByCondition(dmsProductCategory);
        if ("SR".equals(userSession.getRoleCode())) {//零售端
            if (BlankUtil.isNotEmpty(dmsProductCategoryList)) {
                Long storeId = dmsSellerService.getStoreIdByUserId(userSession.getId());
                if (BlankUtil.isEmpty(storeId) || storeId == 0) {
                    return null;
                }
                for (DmsProductCategory dmsProductCategory1 : dmsProductCategoryList) {
                    DmsSellNumByCategoryDto dmsSellNumByCategoryDto = dmsReportFormsMapper.getProductTopByCategory(storeId, dmsProductCategory1.getId(), startDate);
                    if (BlankUtil.isEmpty(dmsSellNumByCategoryDto)) {
                        dmsSellNumByCategoryDto = new DmsSellNumByCategoryDto();
                        dmsSellNumByCategoryDto.setSaleNum(0);
                    }
                    dmsSellNumByCategoryDto.setRootParentId(dmsProductCategory1.getId());
                    dmsSellNumByCategoryDto.setRootParentName(dmsProductCategory1.getName());
                    dtoList.add(dmsSellNumByCategoryDto);
                }
            }
        } else if ("CO".equals(userSession.getRoleCode())) {
            if (BlankUtil.isNotEmpty(dmsProductCategoryList)) {
                DmsChannel channel = dmsChannelService.getChannelInfoByUserId(userSession.getId());
                String storeIds = "";
                if (BlankUtil.isNotEmpty(channel)) {
                    storeIds = dmsStoreService.getStoreIdsByChannelId(channel.getId());
                } else {
                    return null;
                }
                for (DmsProductCategory dmsProductCategory1 : dmsProductCategoryList) {
                    DmsSellNumByCategoryDto dmsSellNumByCategoryDto = dmsReportFormsMapper.getChannelTopByProduct(storeIds, channel.getId(), dmsProductCategory1.getId(), startDate);
                    if (BlankUtil.isEmpty(dmsSellNumByCategoryDto)) {
                        dmsSellNumByCategoryDto = new DmsSellNumByCategoryDto();
                        dmsSellNumByCategoryDto.setSaleNum(0);
                    }
                    dmsSellNumByCategoryDto.setRootParentId(dmsProductCategory1.getId());
                    dmsSellNumByCategoryDto.setRootParentName(dmsProductCategory1.getName());
                    dtoList.add(dmsSellNumByCategoryDto);
                }
            }
        }
        // 排序
        if (BlankUtil.isNotEmpty(dtoList)) {
            Collections.sort(dtoList, new Comparator<DmsSellNumByCategoryDto>() {
                public int compare(DmsSellNumByCategoryDto o1, DmsSellNumByCategoryDto o2) {
                    if (o1.getSaleNum() > o2.getSaleNum()) return -1;
                    else return 0;
                }
            });
        }

        if (BlankUtil.isNotEmpty(dtoList) && dtoList.size() > 5) {
            dtoList = dtoList.subList(0, 5);
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("list", dtoList);
        return result;
    }


    /**
     * 获取app端库龄top5
     *
     * @param userSession
     * @return
     */
    public List<DmsSellNumByCategoryDto> getTopByOldLibrary(UserSession userSession) {

        Map<String, Object> map = new HashMap<String, Object>();
        if("CO".equals(userSession.getRoleCode())){//订货单---渠道
            map.put("type","1");
        }if("SR".equals(userSession.getRoleCode())){//零售端----门店
            map.put("type","2");
        }
        map.put("userId",userSession.getId());
        List<DmsSellNumByCategoryDto> mapList = dmsReportFormsMapper.getTopByOldLibrary(map);

        //过来掉没有库存的一级分类
        List<DmsSellNumByCategoryDto> removelist = new ArrayList<DmsSellNumByCategoryDto>();
        for (int i = 0; i < mapList.size(); i++) {
            if (mapList.get(i).getTopByOldLibraryList() == null) {
                removelist.add(mapList.get(i));
            }else{
                //根据渠道id（管理区域）和产品id（销售区域）过滤掉自己不能看的产品
                Map<String, Object> map1 = new HashMap<String, Object>();
                map1.put("productId",mapList.get(i).getTopByOldLibraryList().get("productId"));
                map1.put("channelId",userSession.getSuperiorOrgId());
                Integer num = dmsProductAreaMapper.selectByChannelIdAndProductId(map1);
                if(num==0){//不可见产品
                    removelist.add(mapList.get(i));
                }
            }
        }

        mapList.removeAll(removelist);

        // 排序
        if (BlankUtil.isNotEmpty(mapList)) {
            Collections.sort(mapList, new Comparator<DmsSellNumByCategoryDto>() {
                public int compare(DmsSellNumByCategoryDto o1, DmsSellNumByCategoryDto o2) {
                    if (Integer.parseInt(o1.getTopByOldLibraryList().get("days").toString()) > Integer.parseInt(o2.getTopByOldLibraryList().get("days").toString())) return -1;
                    else return 0;
                }
            });
        }

        //只取Top5个分类的数据
        if (BlankUtil.isNotEmpty(mapList) && mapList.size() > 5) {
            mapList = mapList.subList(0, 5);
        }

        return mapList;
    }


    /**
     * 根据类型查询PC端top5
     *
     * @param type
     * @return
     */
    public List<Map<String, Object>> getTop5ByPC(Integer type) {
        if (BlankUtil.isEmpty(type) || type > 4 || type < 2) type = 3;
        return dmsReportFormsMapper.getTopByDateType(getStartDate(type));
    }

    public List<Map<String, Object>> getOrderTopByPC(Integer dateType, Integer searchType) {
        List<Map<String, Object>> list = null;
        if (dateType == 2) {
            list = getInfoByMonth(searchType);
        } else if (dateType == 4) {
            list = getInfoByYear(searchType);
        } else {
            list = getInfoByQuarter(searchType);
        }
        return list;
    }

    /**
     * 获取时间
     *
     * @param type
     * @return
     */
    private String getStartDate(Integer type) {
        if (BlankUtil.isEmpty(type)) type = 0;
        String startDate = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        String m = "";
        if (month >= 10) m = "10";
        else if (month >= 7 && month <= 9) m = "07";
        else if (month >= 4 && month <= 6) m = "04";
        else m = "01";
        if (type == 1) {//本周
            int d = 0;
            if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                d = -7;
            } else {
                d = 1 - cal.get(Calendar.DAY_OF_WEEK);
            }
            cal.add(Calendar.DAY_OF_WEEK, d);
            //所在周开始日期
            startDate = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00";
            /*cal.add(Calendar.DAY_OF_WEEK, 6);
            //所在周结束日期
            System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));*/
        } else if (type == 2) { //本月
            startDate = year + "-" + month + "-" + "01 00:00:00";
        } else if (type == 3) {//本季度
            startDate = year + "-" + m + "-" + "01 00:00:00";
        } else if (type == 4) {//本年
            startDate = year + "-01-01 00:00:00";
        } else {
            startDate = year + "-" + month + "-" + "01 00:00:00";
        }
        return startDate;
    }

    /**
     * 获取通过月份
     */
    private List<Map<String, Object>> getInfoByMonth(Integer type) {
        List<Map<String, Object>> list = new ArrayList<>();
        String startDate = "";
        String endDate = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        for (int i = 0; i < 6; i++) {//6个月
            String key = "";
            int m = month - i;
            int mon = month - i + 1;
            if (m <= 0) {
                m = 12 + m;
                key = (year - 1) + "-" + (m < 10 ? "0" + m : m);
            } else {
                key = year + "-" + (m < 10 ? "0" + m : m);
            }
            startDate = getDateStr(year, m);
            if (month == 12) endDate = getDateStr(year + 1, mon);
            else endDate = getDateStr(year, mon);
            Map<String, Object> map = new HashMap<String, Object>();
            if (type == 1) {
                Double d = dmsReportFormsMapper.getSaleMoneyByOrder(startDate, endDate);
                map.put(key, d);
            } else {
                Integer n = dmsReportFormsMapper.getSaleNumByOrder(startDate, endDate);
                map.put(key, n);
            }
            list.add(map);
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 获取通过季度
     */
    private List<Map<String, Object>> getInfoByQuarter(Integer type) {
        List<Map<String, Object>> list = new ArrayList<>();
        String startDate = "";
        String endDate = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int m = 0;
        if (month >= 10) m = 10;
        else if (month >= 7 && month <= 9) m = 7;
        else if (month >= 4 && month <= 6) m = 4;
        else m = 1;
        //todo 下次优化
        for (int i = 0; i < 4; i++) {//季度
            String key = "";
            int s = m - 3 * i;
            if (s == 10) {
                startDate = year + "-10-01 00:00:00";
                endDate = (year + 1) + "-01-01 00:00:00";
                key = year + "Q4";
            } else if (s == 7) {
                startDate = year + "-07-01 00:00:00";
                endDate = year + "-10-01 00:00:00";
                key = year + "Q3";
            } else if (s == 4) {
                startDate = year + "-04-01 00:00:00";
                endDate = year + "-07-01 00:00:00";
                key = year + "Q2";
            } else if (s == 1) {
                startDate = year + "-01-01 00:00:00";
                endDate = year + "-04-01 00:00:00";
                key = year + "Q1";
            } else if (s == -2) {
                startDate = (year - 1) + "-10-01 00:00:00";
                endDate = year + "-01-01 00:00:00";
                key = (year - 1) + "Q4";
            } else if (s == -5) {
                startDate = (year - 1) + "-07-01 00:00:00";
                endDate = (year - 1) + "-10-01 00:00:00";
                key = (year - 1) + "Q3";
            } else if (s == -8) {
                startDate = (year - 1) + "-04-01 00:00:00";
                endDate = (year - 1) + "-07-01 00:00:00";
                key = (year - 1) + "Q2";
            } else if (s == -11) {
                startDate = (year - 1) + "-01-01 00:00:00";
                endDate = (year - 1) + "-04-01 00:00:00";
                key = (year - 1) + "Q1";
            }
            Map<String, Object> map = new HashMap<String, Object>();
            if (type == 1) {
                Double d = dmsReportFormsMapper.getSaleMoneyByOrder(startDate, endDate);
                map.put(key, d);
            } else {
                Integer n = dmsReportFormsMapper.getSaleNumByOrder(startDate, endDate);
                map.put(key, n);
            }
            list.add(map);
        }
        Collections.reverse(list);
        return list;
    }

    /**
     * 获取通过年份
     */
    private List<Map<String, Object>> getInfoByYear(Integer type) {
        List<Map<String, Object>> list = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String startDate = "";
        String endDate = "";
        int year = cal.get(Calendar.YEAR);
        for (int i = 0; i < 4; i++) {//年
            String key = String.valueOf(year - i);
            startDate = (year - i) + "-01-01 00:00:00";
            endDate = (year - i + 1) + "-01-01 00:00:00";
            Map<String, Object> map = new HashMap<String, Object>();
            if (type == 1) {
                Double d = dmsReportFormsMapper.getSaleMoneyByOrder(startDate, endDate);
                map.put(key, d);
            } else {
                Integer n = dmsReportFormsMapper.getSaleNumByOrder(startDate, endDate);
                map.put(key, n);
            }
            list.add(map);
        }
        Collections.reverse(list);
        return list;
    }

    private String getDateStr(int year, int month) {
        String mStr = String.valueOf(month);
        return year + "-" + (mStr.length() == 2 ? mStr : "0" + mStr) + "-" + "01 00:00:00";
    }
}
