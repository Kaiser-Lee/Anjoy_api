package com.coracle.dms.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by
 * User：arno
 * Date：2018/1/24-11:41
 */
public class DmsResourceVo {
    @ApiModelProperty("菜单ID")
    private Long id;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("菜单url")
    private String url;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("菜单或者按钮")
    private String type;
    @ApiModelProperty("图标，按钮可忽略")
    private String icon;
    @ApiModelProperty("上级ID")
    private Long supId;
    private List<DmsResourceVo> children;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<DmsResourceVo> getChildren() {
        return children;
    }

    public void setChildren(List<DmsResourceVo> children) {
        this.children = children;
    }


    public Long getSupId() {
        return supId;
    }

    public void setSupId(Long supId) {
        this.supId = supId;
    }
}
