package com.android.mobilebox.core.bean.user;

/**
 * Auto-generated: 2019-03-05 16:34:54
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class OrderResponse {

    /**
     * actType : 存取
     * devId : 15aa68f3183311ebb7260242ac120004_uniqueCode002
     * gmtCreate : 1605766666000
     * gmtModified : 1605766666000
     * id : 2c92808575de79f30175df26d863003f
     * relevanceId : f76c5514-bc9e-4864-92ac-67ad5fdefa4d
     * user : {"faceImg":"http://172.16.61.101:9000/group1/M00/00/00/rBA9ZV-ib0uAdnu-AChUMxWesXk799.jpg","id":3,"username":"manager01"}
     * userId : 3
     */

    private String actType;
    private String devId;
    private long gmtCreate;
    private long gmtModified;
    private String id;
    private String relevanceId;
    private UserInfo user;
    private int userId;

    public String getActType() {
        return actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
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

    public String getRelevanceId() {
        return relevanceId;
    }

    public void setRelevanceId(String relevanceId) {
        this.relevanceId = relevanceId;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}