package com.android.mobilebox.presenter;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.RecordDetailContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;

import java.util.List;

public class RecordDetailPresenter extends BasePresenter<RecordDetailContract.View> implements RecordDetailContract.Presenter {
    @Override
    public void getTerminalProp(String devId, String relevance_id) {
        addSubscribe(DataManager.getInstance().getTerminalProp(devId, relevance_id)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<TerminalResult>>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<List<TerminalResult>> listBaseResponse) {
                        if (mView != null) {
                            mView.handleGetTerminalProp(listBaseResponse);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }
}
