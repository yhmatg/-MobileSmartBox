package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.AddUserBody;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;

import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface AddUserContract {
    interface View extends AbstractView {

        void handleUploadFace(BaseResponse<UploadFaceResponse> uploadFaceResponse);

        void handleAddUser(BaseResponse<UserInfo> userInfoResponse);

    }

    interface Presenter extends AbstractPresenter<View> {

        void uploadFace(MultipartBody.Part part);

        void addUser(AddUserBody addUserBody);
    }
}
