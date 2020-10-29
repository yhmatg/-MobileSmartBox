package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;

import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface UploadFaceContract {

    interface View extends AbstractView {

        void handleUploadFace(BaseResponse<UploadFaceResponse> uploadFaceResponseBaseResponse);

    }

    interface Presenter extends AbstractPresenter<View> {

        void uploadFace(MultipartBody.Part part);

        void updateFace(String id, String faceImg, String faceFeature);
    }
}
