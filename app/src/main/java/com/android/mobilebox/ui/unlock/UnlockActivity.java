package com.android.mobilebox.ui.unlock;

import android.view.View;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.NewOrderResponse;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderBody;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.presenter.UnlockPresenter;
import com.android.mobilebox.utils.StringUtils;
import com.android.mobilebox.utils.ToastUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UnlockActivity extends BaseActivity<UnlockPresenter> implements UnlockContract.View {

    private String orderUuid;
    private static String deviceId = "15aa68f3183311ebb7260242ac120004_uniqueCode002";
    private static String capId = "e684ea3f18cc11ebb7260242ac120004";
    private static String instName = "openKey";
    @BindView(R.id.tv_order_result)
    TextView newOrderResult;
    @BindView(R.id.tv_unlock_result)
    TextView unlockResult;

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
        if (200000 == terminalOrderResponse.getCode()) {
            String result = "";
            result += "设备号：" + terminalOrderResponse.getData().getDevId() + "\n";
            result += "操作：" + terminalOrderResponse.getData().getInstName() + "\n";
            result += "操作单号：" + terminalOrderResponse.getData().getInstData().getRelevanceId() + "\n";
            result += "操作状态：" + terminalOrderResponse.getData().getInstStatus() + "\n";
            unlockResult.setText(result);
        } else {
            ToastUtils.showShort("发送开锁消息失败");
        }
    }

    @Override
    public void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse) {
        if (200000 == terminalPropResponse.getCode()) {
            String result = "";
            List<TerminalResult> data = terminalPropResponse.getData();
            if(data.size() > 0){
                result += "操作单号：" + data.get(0).getRelevance_id() + "\n";
            }
            for (TerminalResult datum : data) {
                if("id1".equals(datum.getCap_id())){
                    result += "开锁完成" +"\n";
                }else if("id2".equals(datum.getCap_id())){
                    result += "关锁完成" +"\n";
                }else if("id3".equals(datum.getCap_id())){
                    result += "盘点上报完成" +"\n";
                    result += "   存件：" + datum.getProp().getRfid_in()+"\n";
                    result += "   取件：" + datum.getProp().getRfid_out()+"\n";
                }
            }
            unlockResult.setText(result);
        } else {
            ToastUtils.showShort("获取终端设备操作结果失败");
        }
    }

    @Override
    public void handleNewOrder(BaseResponse<NewOrderResponse> newOrderResponse) {
        if (200000 == newOrderResponse.getCode()) {
            String result = "";
            result += "设备号：" + newOrderResponse.getData().getDevId() + "\n";
            result += "操作：" + newOrderResponse.getData().getActType() + "\n";
            result += "操作单号：" + newOrderResponse.getData().getRelevanceId() + "\n";
            newOrderResult.setText(result);
        } else {
            orderUuid = null;
            ToastUtils.showShort("创建操作单失败");
        }
    }

    @OnClick({R.id.bt_new_order, R.id.bt_unlock})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_new_order:
                NewOrderBody newOrderBody = new NewOrderBody();
                newOrderBody.setActType("取件");
                orderUuid = UUID.randomUUID().toString();
                newOrderBody.setRelevanceId(orderUuid);
                newOrderBody.setRemark("remarkOne");
                mPresenter.newOrder(deviceId, newOrderBody);
                break;
            case R.id.bt_unlock:
                if (!StringUtils.isEmpty(orderUuid)) {
                    OrderBody orderBody = new OrderBody();
                    OrderBody.InstData instData = new OrderBody.InstData();
                    instData.setEkey("on");
                    instData.setRelevanceId(orderUuid);
                    orderBody.setInstData(instData);
                    orderBody.setCapId(capId);
                    orderBody.setInstName(instName);
                    mPresenter.terminalOrder(deviceId, orderBody);
                } else {
                    ToastUtils.showShort("请先创建操作单");
                }
                break;
        }
    }
}
