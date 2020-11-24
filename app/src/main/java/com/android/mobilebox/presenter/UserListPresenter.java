package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.UserListContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;

import java.util.List;

public class UserListPresenter extends BasePresenter<UserListContract.View> implements UserListContract.Presenter {

    @Override
    public void getAllUserInfo() {
        addSubscribe(DataManager.getInstance().getAllUserInfo()
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<UserInfo>>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<List<UserInfo>> listBaseResponse) {
                        mView.handelAllUserInfo(listBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }
}
