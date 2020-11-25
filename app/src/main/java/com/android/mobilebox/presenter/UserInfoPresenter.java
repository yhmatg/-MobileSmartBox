package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.UserInfoContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;
import com.android.mobilebox.utils.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MultipartBody;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.View> implements UserInfoContract.Presenter {
    @Override
    public void uploadFace(MultipartBody.Part part) {
        addSubscribe(DataManager.getInstance().uploadFace(part)
                .compose(RxUtils.rxSchedulerHelper())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<BaseResponse<UploadFaceResponse>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<UploadFaceResponse> uploadFaceResponseBaseResponse) {
                        mView.handleUploadFace(uploadFaceResponseBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("人脸头像上传失败" + e.getMessage());
                    }
                }));
    }

    @Override
    public void updateFace(FaceBody faceBody) {
        addSubscribe(DataManager.getInstance().updateFace(faceBody)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<UserInfo>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<UserInfo> userInfoResponse) {
                        mView.handleupdateFace(userInfoResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort("人脸头像更新失败" + e.getMessage());
                    }
                }));
    }

    @Override
    public void getAllOrders(String devId, String actType) {
        addSubscribe(DataManager.getInstance().getAllOrders(devId, actType)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<OrderResponse>>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<List<OrderResponse>> listBaseResponse) {
                        mView.handleGetAllOrders(listBaseResponse);
                    }
                }));
    }

    @Override
    public void getUserOrders(Integer userId, String devId, String actType) {
        addSubscribe(DataManager.getInstance().getUserOrders(userId,devId, actType)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<OrderResponse>>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<List<OrderResponse>> listBaseResponse) {
                        mView.handleGetAllOrders(listBaseResponse);
                    }
                }));
    }
}
