package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.AddUserContract;
import com.android.mobilebox.contract.DeviceContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;
import com.android.mobilebox.utils.ToastUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MultipartBody;


/**
 * @author yhm
 * @date 2018/2/26
 */
public class DevicePresenter extends BasePresenter<DeviceContract.View> implements DeviceContract.Presenter {


    @Override
    public void getAllDevices() {
        addSubscribe(DataManager.getInstance().getAllDevices()
                .compose(RxUtils.rxSchedulerHelper())
        .subscribeWith(new BaseObserver<BaseResponse<List<DeviceResponse>>>(mView, false) {
            @Override
            public void onNext(BaseResponse<List<DeviceResponse>> listBaseResponse) {
                mView.handleGetAllDevices(listBaseResponse);
            }
        }));
    }
}
