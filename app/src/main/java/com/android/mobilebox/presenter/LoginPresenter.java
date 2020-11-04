package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.LoginContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.CommonUtils;
import com.android.mobilebox.utils.Md5Util;
import com.android.mobilebox.utils.RxUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * @author yhm
 * @date 2018/2/26
 */
public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {
    

    public LoginPresenter() {
        super();
    }
    
    @Override
    public void login(final LoginUser loginUser) {
        final String passWord = loginUser.getPassword();
        if(CommonUtils.isNetworkConnected()){
            //loginUser.setPassword(Md5Util.getMD5(passWord));
            addSubscribe(DataManager.getInstance().login(loginUser)
            .compose(RxUtils.rxSchedulerHelper())
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new BaseObserver<BaseResponse<UserLoginResponse>>(mView,
                    false) {

                @Override
                public void onNext(BaseResponse<UserLoginResponse> userLoginResponse) {
                    if(userLoginResponse.getCode() == 200000){
                        DataManager.getInstance().setToken(userLoginResponse.getData().getToken());
                        DataManager.getInstance().setLoginAccount(loginUser.getUsername());
                        DataManager.getInstance().setLoginPassword(loginUser.getPassword());
                        mView.startMainActivity();
                    }

                }
            }));
        }
    }



}
