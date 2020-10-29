package com.android.mobilebox.core.http.api;

import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author yhm
 * @date 2018/2/12
 */

public interface GeeksApis {

    //登录
    @POST("/api/v1/auth/login")
    Observable<BaseResponse<UserLoginResponse>> login(@Body UserInfo userInfo);

    //人脸头像上传
    @Multipart
    @POST("/api/v1/users/uploadface")
    Observable<BaseResponse<UploadFaceResponse>> uploadFace(@Part MultipartBody.Part part);

    //人脸头像更新
    @POST("/api/v1/users/updateface")
    Observable<BaseResponse>updateFace(@Query("id") String id,@Query("faceImg") String faceImg,@Query("faceFeature") String faceFeature);

    //人脸头像特征值更新
    @POST("/api/v1/users/updateFeature")
    Observable<BaseResponse>updateFeature(@Query("id") String id,@Query("faceImg") String faceImg,@Query("faceFeature") String faceFeature);

}
