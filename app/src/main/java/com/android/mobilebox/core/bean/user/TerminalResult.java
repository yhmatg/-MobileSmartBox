package com.android.mobilebox.core.bean.user;

import java.util.List;

/**
 * Auto-generated: 2019-03-05 16:34:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TerminalResult {

    /**
     * cap_id : id1
     * data_event_time : 1604300824000
     * dev_id : 15aa68f3183311ebb7260242ac120004_uniqueCode002
     * gmtCreate : 1604371255000
     * gmtModified : 1604371255000
     * id : 2c90c40e758bf17b01758bfa91030004
     * prop : {"openEkey":true,"openType":"remote","closeEkey":true,"rfid_out":["epc01,epc02,epc03"],"rfid_in":["epc04","epc05","epc06"]}
     * relevance_id : 自定义Id推荐uuid
     */

    private String  capId;
    private long dataEventTime;
    private String devId;
    private long gmtCreate;
    private long gmtModified;
    private String id;
    private Prop prop;
    private String relevanceId;

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

    public Prop getProp() {
        return prop;
    }

    public void setProp(Prop prop) {
        this.prop = prop;
    }

    public String getCapId() {
        return capId;
    }

    public void setCapId(String capId) {
        this.capId = capId;
    }

    public long getDataEventTime() {
        return dataEventTime;
    }

    public void setDataEventTime(long dataEventTime) {
        this.dataEventTime = dataEventTime;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public static class Prop {
        /**
         * openEkey : true
         * openType : remote
         * closeEkey : true
         * rfid_out : ["epc01,epc02,epc03"]
         * rfid_in : ["epc04","epc05","epc06"]
         */

        private boolean openEkey;
        private String openType;
        private boolean closeEkey;
        private List<String> rfid_out;
        private List<String> rfid_in;

        public boolean isOpenEkey() {
            return openEkey;
        }

        public void setOpenEkey(boolean openEkey) {
            this.openEkey = openEkey;
        }

        public String getOpenType() {
            return openType;
        }

        public void setOpenType(String openType) {
            this.openType = openType;
        }

        public boolean isCloseEkey() {
            return closeEkey;
        }

        public void setCloseEkey(boolean closeEkey) {
            this.closeEkey = closeEkey;
        }

        public List<String> getRfid_out() {
            return rfid_out;
        }

        public void setRfid_out(List<String> rfid_out) {
            this.rfid_out = rfid_out;
        }

        public List<String> getRfid_in() {
            return rfid_in;
        }

        public void setRfid_in(List<String> rfid_in) {
            this.rfid_in = rfid_in;
        }
    }
}