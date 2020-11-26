package com.android.mobilebox.core.http.api;

import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;

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
    @POST("/api/v1/unauth/updateface")
    Observable<BaseResponse<UserInfo>> updateFace(@Body FaceBody faceBody);

    //根据用户id获取用户信息
    @GET("/api/v1/users/{userId}")
    Observable<BaseResponse<UserInfo>> getUserInfoById(@Path("userId") String userId);

    //获取所有用户信息
    @GET("/api/v1/users")
    Observable<BaseResponse<List<UserInfo>>> getAllUserInfo();

    //添加用户
    @POST("/api/v1/users")
    Observable<BaseResponse<UserInfo>> addUser(@Body AddUserBody addUserBody);

    //终端指令管理，远程开锁等
    @POST("/api/v1/terminalctl/devices/{dev_id}/insts")
    Observable<BaseResponse<OpenResult>> terminalOrder(@Path("dev_id") String devId, @Body OrderBody orderBody);

    //移动端查询终端操作记录结果属性
    @GET("/api/v1/properties/devices/{dev_id}")
    Observable<BaseResponse<List<TerminalResult>>> getTerminalProp(@Path("dev_id") String devId, @Query("relevance_id") String relevance_id);

    //查询操作单记录
    @GET("/api/v1/actrecords")
    Observable<BaseResponse<List<OrderResponse>>> getAllOrders(@Query("dev_id") String devId, @Query("act_type") String actType);

    //创建操作单
    @POST("/api/v1/actrecords/devices/{dev_id}/")
    Observable<BaseResponse<OrderResponse>> newOrder(@Path("dev_id") String devId, @Body NewOrderBody newOrderBody);

    //查询所有的终端设备
    @GET("/api/v1/iotterminals/rfidcount")
    Observable<BaseResponse<List<DeviceResponse>>> getAllDevices();

    //获取指定用户的操作记录
    //查询操作单记录
    @GET("/api/v1/useractrecords/users/{userId}")
    Observable<BaseResponse<List<OrderResponse>>> getUserOrders(@Path("userId") Integer userId, @Query("dev_id") String devId, @Query("act_type") String actType);
}
