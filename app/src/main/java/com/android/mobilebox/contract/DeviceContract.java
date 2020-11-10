package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.core.bean.user.NewOrderResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface DeviceContract {
    interface View extends AbstractView {

        void handleGetAllDevices(BaseResponse<List<DeviceResponse>> devices);

    }

    interface Presenter extends AbstractPresenter<View> {

        void getAllDevices();
    }
}
