package com.android.mobilebox.presenter;

import android.os.Handler;

import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.NewOrderResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;


/**
 * @author yhm
 * @date 2018/2/26
 */
public class UnlockPresenter extends BasePresenter<UnlockContract.View> implements UnlockContract.Presenter {

    private static String deviceId = "15aa68f3183311ebb7260242ac120004_uniqueCode002";

    public UnlockPresenter() {
        super();
    }


    @Override
    public void terminalOrder(String devId, OrderBody orderBody) {
        addSubscribe(DataManager.getInstance().terminalOrder(devId, orderBody)
                .compose(RxUtils.rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponse<OpenResult>>() {
                    @Override
                    public void accept(BaseResponse<OpenResult> openResultBaseResponse) throws Exception {
                        if (200000 == openResultBaseResponse.getCode()) {
                            //开锁指定请求成功，开始查询终端操作状态
                            getTerminalProp(devId, openResultBaseResponse.getData().getInstData().getRelevanceId());
                        }
                    }
                })
                .subscribeWith(new BaseObserver<BaseResponse<OpenResult>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<OpenResult> openResultBaseResponse) {
                        mView.handleTerminalOrder(openResultBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }

    @Override
    public void getTerminalProp(String devId, String relevance_id) {
        addSubscribe(DataManager.getInstance().getTerminalProp(devId, relevance_id)
                .compose(RxUtils.rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponse<List<TerminalResult>>>() {
                    @Override
                    public void accept(BaseResponse<List<TerminalResult>> listBaseResponse) throws Exception {
                        List<TerminalResult> data = listBaseResponse.getData();
                        boolean isInvReported = false;
                        for (TerminalResult datum : data) {
                            if ("id3".equals(datum.getCap_id())) {
                                isInvReported = true;
                                break;
                            }
                        }
                        if (!isInvReported) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getTerminalProp(devId, relevance_id);
                                }
                            }, 2000);
                        }
                    }
                })
                .subscribeWith(new BaseObserver<BaseResponse<List<TerminalResult>>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<List<TerminalResult>> listBaseResponse) {
                        mView.handleGetTerminalProp(listBaseResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }

    @Override
    public void newOrder(String devId, NewOrderBody newOrderBody) {
        addSubscribe(DataManager.getInstance().newOrder(devId, newOrderBody)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<NewOrderResponse>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<NewOrderResponse> newOrderResponse) {
                        mView.handleNewOrder(newOrderResponse);
                    }
                }));
    }
}
