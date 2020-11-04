package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;
import java.util.List;


/**
 * @author yhm
 * @date 2018/2/26
 */
public class UnlockPresenter extends BasePresenter<UnlockContract.View> implements UnlockContract.Presenter {


    public UnlockPresenter() {
        super();
    }


    @Override
    public void terminalOrder(String devId, OrderBody orderBody) {
        addSubscribe(DataManager.getInstance().terminalOrder(devId, orderBody)
        .compose(RxUtils.rxSchedulerHelper())
        .subscribeWith(new BaseObserver<BaseResponse<OpenResult>>(mView, false) {
            @Override
            public void onNext(BaseResponse<OpenResult> openResultBaseResponse) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }));
    }

    @Override
    public void getTerminalProp(String devId, String cap_id, String relevance_id) {
        addSubscribe(DataManager.getInstance().getTerminalProp(devId, cap_id, relevance_id)
        .compose(RxUtils.rxSchedulerHelper())
        .subscribeWith(new BaseObserver<BaseResponse<List<TerminalResult>>>(mView, false) {
            @Override
            public void onNext(BaseResponse<List<TerminalResult>> listBaseResponse) {

            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        }));
    }
}
