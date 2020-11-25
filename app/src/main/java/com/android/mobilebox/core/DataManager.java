package com.android.mobilebox.core;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.core.http.HttpHelper;
import com.android.mobilebox.core.http.HttpHelperImpl;
import com.android.mobilebox.core.prefs.PreferenceHelper;
import com.android.mobilebox.core.prefs.PreferenceHelperImpl;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2017/11/27
 */

public class DataManager implements HttpHelper, PreferenceHelper {

    private HttpHelper mHttpHelper;
    private PreferenceHelper mPreferenceHelper;
    private volatile static DataManager INSTANCE = null;

    private DataManager(HttpHelper httpHelper, PreferenceHelper preferencesHelper) {
        mHttpHelper = httpHelper;
        mPreferenceHelper = preferencesHelper;
    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            synchronized (DataManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DataManager(HttpHelperImpl.getInstance(), PreferenceHelperImpl.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void saveHostUrl(String hostUrl) {
        mPreferenceHelper.saveHostUrl(hostUrl);
    }

    @Override
    public String getHostUrl() {
        return mPreferenceHelper.getHostUrl();
    }

    @Override
    public void setLoginAccount(String account) {
        mPreferenceHelper.setLoginAccount(account);
    }

    @Override
    public void setLoginPassword(String password) {
        mPreferenceHelper.setLoginPassword(password);
    }

    @Override
    public String getLoginAccount() {
        return mPreferenceHelper.getLoginAccount();
    }

    @Override
    public String getLoginPassword() {
        return mPreferenceHelper.getLoginPassword();
    }

    @Override
    public void setToken(String token) {
        mPreferenceHelper.setToken(token);
    }

    @Override
    public String getToken() {
        return mPreferenceHelper.getToken();
    }

    @Override
    public Observable<BaseResponse<UserLoginResponse>> login(LoginUser loginUser) {
        return mHttpHelper.login(loginUser);
    }

    @Override
    public Observable<BaseResponse<UploadFaceResponse>> uploadFace(MultipartBody.Part part) {
        return mHttpHelper.uploadFace(part);
    }

    @Override
    public Observable<BaseResponse<UserInfo>> updateFace(FaceBody faceBody) {
        return mHttpHelper.updateFace(faceBody);
    }

    @Override
    public Observable<BaseResponse<UserInfo>> getUserInfoById(String userId) {
        return mHttpHelper.getUserInfoById(userId);
    }

    @Override
    public Observable<BaseResponse<List<UserInfo>>> getAllUserInfo() {
        return mHttpHelper.getAllUserInfo();
    }

    @Override
    public Observable<BaseResponse<OpenResult>> terminalOrder(String devId, OrderBody orderBody) {
        return  mHttpHelper.terminalOrder(devId, orderBody);
    }

    @Override
    public Observable<BaseResponse<List<TerminalResult>>> getTerminalProp(String devId, String relevance_id) {
        return mHttpHelper.getTerminalProp(devId, relevance_id);
    }

    @Override
    public Observable<BaseResponse<UserInfo>> addUser(AddUserBody addUserBody) {
        return mHttpHelper.addUser(addUserBody);
    }

    @Override
    public Observable<BaseResponse<OrderResponse>> newOrder(String devId, NewOrderBody newOrderBody) {
        return mHttpHelper.newOrder(devId, newOrderBody);
    }

    @Override
    public Observable<BaseResponse<List<DeviceResponse>>> getAllDevices() {
        return mHttpHelper.getAllDevices();
    }

    @Override
    public Observable<BaseResponse<List<OrderResponse>>> getAllOrders(String devId, String actType) {
        return mHttpHelper.getAllOrders(devId, actType);
    }

    @Override
    public Observable<BaseResponse<List<OrderResponse>>> getUserOrders(Integer userId, String devId, String actType) {
        return mHttpHelper.getUserOrders(userId, devId, actType);
    }

}
