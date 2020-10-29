package com.android.mobilebox.presenter;

import android.util.Log;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.LoginContract;
import com.android.mobilebox.contract.UploadFaceContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.CommonUtils;
import com.android.mobilebox.utils.Md5Util;
import com.android.mobilebox.utils.RxUtils;
import com.android.mobilebox.utils.ToastUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
}
