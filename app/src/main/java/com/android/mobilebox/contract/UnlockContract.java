
package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;

import java.util.List;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface UnlockContract {
    interface View extends AbstractView {

        void handleTerminalOrder(BaseResponse<OpenResult> terminalOrderResponse);

        void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse);

        void handleNewOrder(BaseResponse<OrderResponse> NewOrderResponse);

        void handleGetAllOrders(BaseResponse<List<OrderResponse>> newOrderResponse);
    }

    interface Presenter extends AbstractPresenter<View> {
        //远程开锁接口
        void terminalOrder(String devId, OrderBody orderBody);

        //查询远程终端设备操作属性
        void getTerminalProp(String devId, String relevance_id);

        void newOrder(String devId, NewOrderBody newOrderBody);

        //查询操作单记录
        void getAllOrders(String devId, String actType);
    }
}
