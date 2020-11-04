package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.UploadFaceContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;
import com.android.mobilebox.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MultipartBody;


/**
 * @author yhm
 * @date 2018/2/26
 */
public class UploadFacePresenter extends BasePresenter<UploadFaceContract.View> implements UploadFaceContract.Presenter {


    public UploadFacePresenter() {
        super();
    }


    @Override
    public void uploadFace(MultipartBody.Part part) {
        addSubscribe(DataManager.getInstance().uploadFace(part)
        .compose(RxUtils.rxSchedulerHelper())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new BaseObserver<BaseResponse<UploadFaceResponse>>(mView,false) {
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
        .subscribeWith(new BaseObserver<BaseResponse<UserInfo>>(mView,false) {
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
}
