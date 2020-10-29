package com.android.mobilebox.core.http;

import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author yhm
 * @date 2017/11/27
 */

public interface HttpHelper {

    Observable<BaseResponse<UserLoginResponse>> login(LoginUser loginUser);

    Observable<BaseResponse<UploadFaceResponse>> uploadFace(MultipartBody.Part part);

    Observable<BaseResponse<UserInfo>> updateFace(String id, String faceImg, String faceFeature);

    Observable<BaseResponse<UserInfo>> getUserInfoById(String userId);

    Observable<BaseResponse<List<UserInfo>>> getAllUserInfo();

    Observable<BaseResponse> terminalOrder(String devId, OrderBody orderBody);

}
