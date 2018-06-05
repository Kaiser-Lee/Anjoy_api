package com.coracle.dms.xweb.common.util;

import com.coracle.yk.xframework.common.exception.runtime.ParamException;
import com.coracle.yk.xframework.util.BlankUtil;
import com.xiruo.medbid.components.StringUtils;

import java.math.BigDecimal;

/**
 * 表单检验工具
 * Created by huangbaidong
 * 2017/5/16.
 */
public class FormCheckUtil {

    /**
     * 检查必填项属性值
     * @param fieldValue 属性值
     * @param fieldName 属性名
     */
    public static void checkRequiredField(Object fieldValue, String fieldName) {
        if(BlankUtil.isEmpty(fieldValue)) {
            throw new ParamException(fieldName+"不能为空");
        }
    }

    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, Long fieldValue, String fieldName, Long min, Long max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ParamException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ParamException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, Integer fieldValue, String fieldName, Integer min, Integer max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ParamException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ParamException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, Double fieldValue, String fieldName, Double min, Double max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ParamException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ParamException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void withinLimits(boolean checkRequiredField, BigDecimal fieldValue, String fieldName, BigDecimal min, BigDecimal max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(fieldValue != null) {
            if (min != null) {
                if (fieldValue.compareTo(min) < 0) {
                    throw new ParamException(fieldName + "不能小于" + min);
                }
            }
            if (max != null) {
                if (fieldValue.compareTo(max) > 0) {
                    throw new ParamException(fieldName + "不能大于" + max);
                }
            }
        }
    }


    /**
     * 边界值检查
     * @param checkRequiredField 必填项检查
     * @param fieldValue 属性值
     * @param fieldName 属性名
     * @param min 最大值
     * @param max 最小值
     */
    public static void checkLength(boolean checkRequiredField, String fieldValue, String fieldName, Integer min, Integer max) {
        if(checkRequiredField) {
            checkRequiredField(fieldValue, fieldName);
        }
        if(BlankUtil.isNotEmpty(fieldValue)) {
            if (min != null) {
                if (StringUtils.chineseLength(fieldValue) < min) {
                    throw new ParamException(fieldName + "长度不能小于" + min);
                }
            }
            if (max != null) {
                if (StringUtils.chineseLength(fieldValue) > max) {
                    throw new ParamException(fieldName + "长度不能大于" + max);
                }
            }
        }
    }

    public static void main(String[] args) {
        BigDecimal commission = new BigDecimal(100)
                .divide(new BigDecimal(1 + 2.3),2,BigDecimal.ROUND_HALF_DOWN);
        System.out.println(commission);
        //checkLength(false,"ABC我爱你","名称",0,9);
        withinLimits(true, null, "金额", 101, 200);
    }
}
