package com.coracle.dms.vo;

import com.coracle.dms.po.DmsDynamic;
import com.coracle.dms.po.DmsDynamicProduct;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/28.
 */
public class DmsDynamicVo extends DmsDynamic {
    private List<Map<String,Object>> dmsDynamicMapList;
    private List<DmsDynamicProduct> dmsDynamicProductList;
    @ApiModelProperty(value = "发货类型文本",hidden = true)
    private String sendTypeText;

    public List<DmsDynamicProduct> getDmsDynamicProductList() {
        return dmsDynamicProductList;
    }

    public void setDmsDynamicProductList(List<DmsDynamicProduct> dmsDynamicProductList) {
        this.dmsDynamicProductList = dmsDynamicProductList;
    }

    public List<Map<String, Object>> getDmsDynamicMapList() {
        return dmsDynamicMapList;
    }

    public void setDmsDynamicMapList(List<Map<String, Object>> dmsDynamicMapList) {
        this.dmsDynamicMapList = dmsDynamicMapList;
    }

    public String getSendTypeText() {
        return sendTypeText;
    }

    public void setSendTypeText(String sendTypeText) {
        this.sendTypeText = sendTypeText;
    }
}
