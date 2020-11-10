package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.AddUserContract;
import com.android.mobilebox.contract.UploadFaceContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
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
public class AddUserPresenter extends BasePresenter<AddUserContract.View> implements AddUserContract.Presenter {


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
    public void addUser(AddUserBody addUserBody) {
        addSubscribe(DataManager.getInstance().addUser(addUserBody)
        .compose(RxUtils.rxSchedulerHelper())
        .subscribeWith(new BaseObserver<BaseResponse<UserInfo>>(mView, false) {
            @Override
            public void onNext(BaseResponse<UserInfo> userInfoBaseResponse) {
                mView.handleAddUser(userInfoBaseResponse);
            }
        }));
    }


}
