package com.android.mobilebox.core.http;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2017/11/27
 */

public interface HttpHelper {

    Observable<BaseResponse<UserLoginResponse>> login(UserInfo userInfo);

    Observable<BaseResponse<UploadFaceResponse>> uploadFace( MultipartBody.Part part);

}
