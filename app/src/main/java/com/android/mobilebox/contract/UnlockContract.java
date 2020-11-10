package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.NewOrderResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface UnlockContract {
    interface View extends AbstractView {

        void handleTerminalOrder(BaseResponse<OpenResult> terminalOrderResponse);

        void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse, boolean isAll);

        void handleNewOrder(BaseResponse<NewOrderResponse> NewOrderResponse);

    }

    interface Presenter extends AbstractPresenter<View> {
        //远程开锁接口
        void terminalOrder(String devId, OrderBody orderBody);

        //查询远程终端设备操作属性
        void getTerminalProp(String devId, String relevance_id, boolean isAll);

        void newOrder(String devId, NewOrderBody newOrderBody);
    }
}
