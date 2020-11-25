package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface UserInfoContract {
    interface View extends AbstractView {
        void handleUploadFace(BaseResponse<UploadFaceResponse> uploadFaceResponse);

        void handleupdateFace(BaseResponse<UserInfo> userInfoResponse);

        void handleGetAllOrders(BaseResponse<List<OrderResponse>> newOrderResponse);
    }

    interface Presenter extends AbstractPresenter<View> {
        void uploadFace(MultipartBody.Part part);

        void updateFace(FaceBody faceBody);

        //查询操作单记录
        void getAllOrders(String devId, String actType);

        //查询操作单记录
        void getUserOrders(Integer userId, String devId, String actType);
    }
}
