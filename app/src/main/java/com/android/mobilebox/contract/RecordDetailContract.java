
package com.android.mobilebox.contract;

import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.base.view.AbstractView;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.TerminalResult;

import java.util.List;

/**
 * @author yhm
 * @date 2018/2/26
 */

public interface RecordDetailContract {
    interface View extends AbstractView {

        void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse);

    }

    interface Presenter extends AbstractPresenter<View> {
        //查询远程终端设备操作属性
        void getTerminalProp(String devId, String relevance_id);

    }
}
