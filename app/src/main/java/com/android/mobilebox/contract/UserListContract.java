package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UserInfo;

import java.util.List;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface UserListContract {
    interface View extends AbstractView {
        void handelAllUserInfo(BaseResponse<List<UserInfo>> userInfos);
    }

    interface Presenter extends AbstractPresenter<View> {
        void getAllUserInfo();
    }
}
