package com.android.mobilebox.ui.unlock;

import android.view.View;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.presenter.UnlockPresenter;
import com.android.mobilebox.utils.StringUtils;

import java.io.File;
import java.util.List;

import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UnlockActivity extends BaseActivity<UnlockPresenter> implements UnlockContract.View {

    @Override
    public UnlockPresenter initPresenter() {
        return new UnlockPresenter();
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_unlock;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void handleTerminalOrder(BaseResponse<OpenResult> terminalOrderResponse) {

    }

    @Override
    public void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse) {

    }

    @OnClick({R.id.bt_unlock})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_unlock:

                break;
        }
    }
}
