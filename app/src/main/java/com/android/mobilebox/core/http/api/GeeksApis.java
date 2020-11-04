package com.android.mobilebox.core.http.api;

import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author yhm
 * @date 2018/2/12
 */

public interface GeeksApis {

    //登录
    @POST("/api/v1/auth/login")
    Observable<BaseResponse<UserLoginResponse>> login(@Body LoginUser loginUser);

    //人脸头像上传
    @Multipart
    @POST("/api/v1/users/uploadface")
    Observable<BaseResponse<UploadFaceResponse>> uploadFace(@Part MultipartBody.Part part);

    //人脸头像更新
    @POST("/api/v1/users/updateface")
    Observable<BaseResponse<UserInfo>> updateFace(@Body FaceBody faceBody);

    //人脸头像特征值更新
    @POST("/api/v1/users/updateFeature")
    Observable<BaseResponse<UserInfo>> updateFeature(@Query("id") String id, @Query("faceFeature") String faceFeature);

    //根据用户id获取用户信息
    @GET("/api/v1/users/{userId}")
    Observable<BaseResponse<UserInfo>> getUserInfoById(@Path("userId") String userId);

    //获取所有用户信息
    @GET("/api/v1/users/{userId}")
    Observable<BaseResponse<List<UserInfo>>> getAllUserInfo();

    //终端指令管理，远程开锁等
    @POST("/api/v1/terminalctl/devices/{dev_id}/insts")
    Observable<BaseResponse<OpenResult>> terminalOrder(@Path("dev_id") String devId, @Body OrderBody orderBody);

    //移动端查询终端操作记录结果属性
    @POST("/api/v1/properties/devices/{dev_id}")
    Observable<BaseResponse<List<TerminalResult>>> getTerminalProp(@Path("dev_id") String devId,@Query("cap_id") String cap_id, @Query("relevance_id") String relevance_id);

}
