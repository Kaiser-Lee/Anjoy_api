package com.coracle.dms.vo;

import com.coracle.dms.po.DmsTreeRelation;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */
public class DmsTreeRelationVo extends DmsTreeRelation {
    private List<Integer> relatedTypeList;

    public List<Integer> getRelatedTypeList() {
        return relatedTypeList;
    }

    public void setRelatedTypeList(List<Integer> relatedTypeList) {
        this.relatedTypeList = relatedTypeList;
    }
}
