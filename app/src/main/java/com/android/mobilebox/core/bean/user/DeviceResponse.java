package com.android.mobilebox.core.bean.user;

public class DeviceResponse {

    /**
     * devCode : uniqueCode
     * devName : 智能柜001
     * gmtCreate : 1603794415000
     * gmtModified : 1603794415000
     * id : 2c910e717569977401756998afdb0000
     * prodId : 15aa68f3183311ebb7260242ac120004
     */

    private String devCode;
    private String devName;
    private String devStatus;
    private long gmtCreate;
    private long gmtModified;
    private String id;
    private String prodId;

    public String getDevCode() {
        return devCode;
    }

    public void setDevCode(String devCode) {
        this.devCode = devCode;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
}
