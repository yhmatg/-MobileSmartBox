package com.android.mobilebox.core.http;

import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.core.http.api.GeeksApis;
import com.android.mobilebox.core.http.client.RetrofitClient;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * 对外隐藏进行网络请求的实现细节
 *
 * @author yhm
 * @date 2017/11/27
 */

public class HttpHelperImpl implements HttpHelper {

    private GeeksApis mGeeksApis;

    private volatile static HttpHelperImpl INSTANCE = null;


    private HttpHelperImpl(GeeksApis geeksApis) {
        mGeeksApis = geeksApis;
    }

    public static HttpHelperImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpHelperImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpHelperImpl(RetrofitClient.getInstance().create(GeeksApis.class));
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Observable<BaseResponse<UserLoginResponse>> login(LoginUser loginUser) {
        return mGeeksApis.login(loginUser);
    }

    @Override
    public Observable<BaseResponse<UploadFaceResponse>> uploadFace(MultipartBody.Part part) {
        return mGeeksApis.uploadFace(part);
    }

    @Override
    public Observable<BaseResponse<UserInfo>> updateFace(FaceBody faceBody) {
        return mGeeksApis.updateFace(faceBody);
    }

    @Override
    public Observable<BaseResponse<UserInfo>> getUserInfoById(String userId) {
        return mGeeksApis.getUserInfoById(userId);
    }

    @Override
    public Observable<BaseResponse<List<UserInfo>>> getAllUserInfo() {
        return mGeeksApis.getAllUserInfo();
    }

    @Override
    public Observable<BaseResponse<OpenResult>> terminalOrder(String devId, OrderBody orderBody) {
        return mGeeksApis.terminalOrder(devId, orderBody);
    }

    @Override
    public Observable<BaseResponse<List<TerminalResult>>> getTerminalProp(String devId, String cap_id, String relevance_id) {
        return mGeeksApis.getTerminalProp(devId, cap_id, relevance_id);
    }

}
