package com.android.mobilebox.contract;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.user.UserInfo;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface LoginContract {

    interface View extends AbstractView {

        void startMainActivity();

    }

    interface Presenter extends AbstractPresenter<View> {

        void login(UserInfo userInfo);
    }
}
