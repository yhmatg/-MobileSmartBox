package com.android.mobilebox.core.bean.user;


public class UserLoginResponse {

    /**
     * loginUser : {"faceImg":"http://172.16.61.101:9000/group1/M00/00/00/rBA9ZV-ib0uAdnu-AChUMxWesXk799.jpg","faceStatus":"0","gmtCreate":1604368994000,"gmtModified":1604627592000,"id":2,"username":"string"}
     * token : b6d706a6-b705-4b1f-8de6-2b82907af6f9
     */

    private LoginUser loginUser;
    private String token;

    public LoginUser getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(LoginUser loginUser) {
        this.loginUser = loginUser;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class LoginUser {
        /**
         * faceImg : http://172.16.61.101:9000/group1/M00/00/00/rBA9ZV-ib0uAdnu-AChUMxWesXk799.jpg
         * faceStatus : 0
         * gmtCreate : 1604368994000
         * gmtModified : 1604627592000
         * id : 2
         * username : string
         */

        private String faceImg;
        private String faceStatus;
        private long gmtCreate;
        private long gmtModified;
        private int id;
        private String username;

        public String getFaceImg() {
            return faceImg;
        }

        public void setFaceImg(String faceImg) {
            this.faceImg = faceImg;
        }

        public String getFaceStatus() {
            return faceStatus;
        }

        public void setFaceStatus(String faceStatus) {
            this.faceStatus = faceStatus;
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
