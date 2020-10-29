package com.android.mobilebox.core.bean.user;

import java.io.Serializable;

/**
 * Auto-generated: 2019-03-05 16:34:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OrderBody {

    /**
     * capId : string
     * creationTimestamp : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     * id : string
     * instData : string
     * instName : string
     * instStatus : string
     * modificationTimestamp : {"date":0,"day":0,"hours":0,"minutes":0,"month":0,"nanos":0,"seconds":0,"time":0,"timezoneOffset":0,"year":0}
     */

    private String capId;
    private String id;
    private String instData;
    private String instName;
    private String instStatus;

    public String getCapId() {
        return capId;
    }

    public void setCapId(String capId) {
        this.capId = capId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstData() {
        return instData;
    }

    public void setInstData(String instData) {
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
}