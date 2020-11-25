package com.android.mobilebox.ui.unlock;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.presenter.UnlockPresenter;
import com.android.mobilebox.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class UnlockActivity extends BaseActivity<UnlockPresenter> implements UnlockContract.View {

    private String orderUuid;
    private static String deviceId = "15aa68f3183311ebb7260242ac120004_uniqueCode002";
    private static String capId = "e684ea3f18cc11ebb7260242ac120004";
    private static String instName = "openKey";
    @BindView(R.id.title_content)
    TextView mTitle;
    @BindView(R.id.tv_unlock_result)
    TextView unlockResult;
    @BindView(R.id.rv_records)
    RecyclerView mRecycleView;
    RecordAdapter mAdapter;
    List<OrderResponse> mData = new ArrayList<>();


    @Override
    public UnlockPresenter initPresenter() {
        return new UnlockPresenter();
    }

    @Override
    protected void initEventAndData() {
        mTitle.setText("设备详情");
        mAdapter = new RecordAdapter(this, mData);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mPresenter.getAllOrders(deviceId, "");
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
            if (data.size() > 0) {
                result += "操作单号：" + data.get(0).getRelevanceId() + "\n";
            }
            for (TerminalResult datum : data) {
                if ("id1".equals(datum.getCapId())) {
                    result += "开锁完成" + "\n";
                } else if ("id2".equals(datum.getCapId())) {
                    result += "关锁完成" + "\n";
                } else if ("id3".equals(datum.getCapId())) {
                    result += "盘点上报完成" + "\n";
                    result += "   存件：" + datum.getProp().getRfid_in() + "\n";
                    result += "   取件：" + datum.getProp().getRfid_out() + "\n";
                }
            }
            unlockResult.setText(result);

        } else {
            ToastUtils.showShort("获取终端设备操作结果失败");
        }
    }

    @Override
    public void handleNewOrder(BaseResponse<OrderResponse> newOrderResponse) {
        if (200000 == newOrderResponse.getCode()) {
            String result = "";
            result += "设备号：" + newOrderResponse.getData().getDevId() + "\n";
            result += "操作：" + newOrderResponse.getData().getActType() + "\n";
            result += "操作单号：" + newOrderResponse.getData().getRelevanceId() + "\n";
            unlockResult.setText(result);
        } else {
            orderUuid = null;
            ToastUtils.showShort("创建操作单失败");
        }
    }

    @Override
    public void handleGetAllOrders(BaseResponse<List<OrderResponse>> newOrderResponse) {
        if (200000 == newOrderResponse.getCode()) {
            mData.clear();
            mData.addAll(newOrderResponse.getData());
            mAdapter.notifyDataSetChanged();
        }

    }

    @OnClick({R.id.bt_unlock, R.id.title_back})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_unlock:
                NewOrderBody newOrderBody = new NewOrderBody();
                newOrderBody.setActType("存取");
                orderUuid = UUID.randomUUID().toString();
                newOrderBody.setRelevanceId(orderUuid);
                newOrderBody.setRemark("remarkOne");
                mPresenter.newOrder(deviceId, newOrderBody);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }
}
