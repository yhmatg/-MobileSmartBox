package com.android.mobilebox.core.bean.user;

/**
 * Auto-generated: 2019-03-05 16:34:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OpenResult {

    /**
     * capId : e684ea3f18cc11ebb7260242ac120004
     * devId : 15aa68f3183311ebb7260242ac120004_uniqueCode002
     * gmtCreate : 1604370717327
     * gmtModified : 1604370717327
     * id : 2c90c40e758bf17b01758bf25a930000
     * instData : {"relevanceId":"自定义Id推荐uuid","ekey":"on"}
     * instName : openKey
     * instStatus : PROCESSING
     */

    private String capId;
    private String devId;
    private long gmtCreate;
    private long gmtModified;
    private String id;
    private InstData instData;
    private String instName;
    private String instStatus;

    public String getCapId() {
        return capId;
    }

    public void setCapId(String capId) {
        this.capId = capId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
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

    public InstData getInstData() {
        return instData;
    }

    public void setInstData(InstData instData) {
        this.instData = instData;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstStatus() {
        return instStatus;
    }

    public void setInstStatus(String instStatus) {
        this.instStatus = instStatus;
    }

    public static class InstData {
        /**
         * relevanceId : 自定义Id推荐uuid
         * ekey : on
         */

        private String relevanceId;
        private String ekey;

        public String getRelevanceId() {
            return relevanceId;
        }

        public void setRelevanceId(String relevanceId) {
            this.relevanceId = relevanceId;
        }

        public String getEkey() {
            return ekey;
        }

        public void setEkey(String ekey) {
            this.ekey = ekey;
        }

        @Override
        public String toString() {
            return "InstData{" +
                    "relevanceId='" + relevanceId + '\'' +
                    ", ekey='" + ekey + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "OpenResult{" +
                "capId='" + capId + '\'' +
                ", devId='" + devId + '\'' +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", id='" + id + '\'' +
                ", instData=" + instData +
                ", instName='" + instName + '\'' +
                ", instStatus='" + instStatus + '\'' +
                '}';
    }
}