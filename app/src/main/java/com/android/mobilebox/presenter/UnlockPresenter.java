package com.android.mobilebox.presenter;

import android.os.Handler;
import android.util.Log;

import com.android.mobilebox.app.SmartBoxApplication;
import com.android.mobilebox.base.presenter.BasePresenter;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.http.widget.BaseObserver;
import com.android.mobilebox.utils.RxUtils;
import com.android.mobilebox.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @author yhm
 * @date 2018/2/26
 */
public class UnlockPresenter extends BasePresenter<UnlockContract.View> implements UnlockContract.Presenter {

    private static String deviceId = "15aa68f3183311ebb7260242ac120004_uniqueCode002";

    @Override
    public void terminalOrder(String devId, OrderBody orderBody) {
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
                            if ("id1".equals(datum.getCapId())) {
                                isInvReported = true;
                                break;
                            }
                        }
                        if (!isInvReported) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mView != null) {
                                        getTerminalProp(devId, relevance_id);
                                    }
                                }
                            }, 2000);
                        }
                    }
                })
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

    @Override
    public void newOrder(String devId, NewOrderBody newOrderBody) {
        addSubscribe(DataManager.getInstance().newOrder(devId, newOrderBody)
                .compose(RxUtils.rxSchedulerHelper())
                .doOnNext(new Consumer<BaseResponse<OrderResponse>>() {
                    @Override
                    public void accept(BaseResponse<OrderResponse> orderResponseBaseResponse) throws Exception {
                        if (200000 == orderResponseBaseResponse.getCode()) {
                            mView.handleNewOrder(orderResponseBaseResponse);
                        }
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseResponse<OrderResponse>, ObservableSource<BaseResponse<OpenResult>>>() {
                    @Override
                    public ObservableSource<BaseResponse<OpenResult>> apply(BaseResponse<OrderResponse> orderResponseBaseResponse) throws Exception {
                        if (200000 == orderResponseBaseResponse.getCode()) {
                            OrderBody orderBody = new OrderBody();
                            OrderBody.InstData instData = new OrderBody.InstData();
                            instData.setEkey("on");
                            instData.setRelevanceId(orderResponseBaseResponse.getData().getRelevanceId());
                            instData.setUserId(SmartBoxApplication.getInstance().getUserResponse().getLoginUser().getId());
                            orderBody.setInstData(instData);
                            orderBody.setCapId("id1");
                            orderBody.setInstName("openKey");
                            return DataManager.getInstance().terminalOrder(devId, orderBody);
                        } else {
                            BaseResponse<OpenResult> openResultBaseResponse = new BaseResponse<>();
                            openResultBaseResponse.setCode(200001);
                            return Observable.just(openResultBaseResponse);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new BaseObserver<BaseResponse<OpenResult>>(mView, false) {

                    @Override
                    public void onNext(BaseResponse<OpenResult> openResultBaseResponse) {
                        if (200000 == openResultBaseResponse.getCode()) {
                            //开锁指定请求成功，开始查询终端操作状态
                            getTerminalProp(devId, openResultBaseResponse.getData().getInstData().getRelevanceId());
                            mView.handleTerminalOrder(openResultBaseResponse);
                        }else {
                            Log.e("UnlockPresenter",openResultBaseResponse.getMessage());
                            ToastUtils.showShort(openResultBaseResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                }));
    }

    @Override
    public void getAllOrders(String devId, String actType) {
        addSubscribe(DataManager.getInstance().getAllOrders(devId, actType)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribeWith(new BaseObserver<BaseResponse<List<OrderResponse>>>(mView, false) {
                    @Override
                    public void onNext(BaseResponse<List<OrderResponse>> listBaseResponse) {
                        mView.handleGetAllOrders(listBaseResponse);
                    }
                }));
    }
}
