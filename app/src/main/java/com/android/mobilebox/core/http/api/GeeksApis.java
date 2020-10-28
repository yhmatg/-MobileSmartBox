package com.android.mobilebox.core.http.api;

import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author yhm
 * @date 2018/2/12
 */

public interface GeeksApis {

    //登录
    @POST("/api/v1/auth/login")
    Observable<BaseResponse<UserLoginResponse>> login(@Body UserInfo userInfo);


}
