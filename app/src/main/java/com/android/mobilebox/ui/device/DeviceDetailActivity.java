package com.android.mobilebox.ui.device;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.UnlockContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.NewOrderBody;
import com.android.mobilebox.core.bean.user.OpenResult;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.presenter.UnlockPresenter;
import com.android.mobilebox.utils.StringUtils;
import com.android.mobilebox.utils.ToastUtils;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceDetailActivity extends BaseActivity<UnlockPresenter> implements UnlockContract.View {
    @BindView(R.id.rv_users)
    RecyclerView mRecycleView;
    @BindView(R.id.edit_search)
    EditText editText;
    @BindView(R.id.title_content)
    TextView mTitle;
    @BindView(R.id.iv_unlock)
    ImageView mUnlockView;
    private List<OrderResponse> mOrderRecords = new ArrayList<>();
    private List<OrderResponse> mAllOrderRecords = new ArrayList<>();
    private UserRecordAdapter deviceAdapter;
    private String deviceId;
    private String deviceName;
    private String status;
    private String orderUuid;
    private MaterialDialog openDialog;

    @Override
    public UnlockPresenter initPresenter() {
        return new UnlockPresenter();
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        if(intent != null){
            deviceId = intent.getStringExtra("DEVICE_ID");
            deviceName = intent.getStringExtra("DEVICE_NAME");
            status = intent.getStringExtra("DEVICE_STATUS");
            if(!StringUtils.isEmpty(deviceName)){
                mTitle.setText("柜号：" + deviceName);
            }
            if("connected".equals(status)){
                mUnlockView.setImageResource(R.drawable.online_unlock);
                mUnlockView.setEnabled(true);
            }else {
                mUnlockView.setImageResource(R.drawable.offline_unlock);
                mUnlockView.setEnabled(false);
            }
        }

        deviceAdapter = new UserRecordAdapter(this,mOrderRecords);
        mRecycleView.setAdapter(deviceAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    String assetsId = editText.getText().toString();
                    editText.setSelection(assetsId.length());
                    List<OrderResponse> filterList = Stream.of(mAllOrderRecords).filter(new Predicate<OrderResponse>() {

                        @Override
                        public boolean test(OrderResponse value) {
                            int i = value.getUser().getUsername().indexOf(assetsId);
                            return i != -1;
                        }
                    }).collect(Collectors.toList());
                    mOrderRecords.clear();
                    mOrderRecords.addAll(filterList);
                    deviceAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        mPresenter.getAllOrders(deviceId,"");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device_detail;
    }

    @Override
    protected void initToolbar() {

    }


    @OnClick({R.id.title_back,R.id.iv_unlock})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_unlock:
                NewOrderBody newOrderBody = new NewOrderBody();
                newOrderBody.setActType("存取");
                orderUuid = UUID.randomUUID().toString();
                newOrderBody.setRelevanceId(orderUuid);
                newOrderBody.setRemark("remarkOne");
                mPresenter.newOrder(deviceId, newOrderBody);
                break;
        }
    }


    @Override
    public void handleTerminalOrder(BaseResponse<OpenResult> terminalOrderResponse) {
        if (200000 == terminalOrderResponse.getCode()) {
        } else {
            ToastUtils.showShort("发送开锁消息失败");
        }
    }

    @Override
    public void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse) {
        if (200000 == terminalPropResponse.getCode()) {
            List<TerminalResult> data = terminalPropResponse.getData();
            for (TerminalResult datum : data) {
                if ("id1".equals(datum.getCapId())) {
                    showOpenDialog();
                   break;
                }
            }

        } else {
            ToastUtils.showShort("获取终端设备操作结果失败");
        }
    }

    @Override
    public void handleNewOrder(BaseResponse<OrderResponse> NewOrderResponse) {
        if (200000 == NewOrderResponse.getCode()) {
        } else {
            orderUuid = null;
            ToastUtils.showShort("创建操作单失败");
        }
    }

    @Override
    public void handleGetAllOrders(BaseResponse<List<OrderResponse>> newOrderResponse) {
        if(200000 == newOrderResponse.getCode()){
            mOrderRecords.clear();
            mAllOrderRecords.clear();
            mOrderRecords.addAll(newOrderResponse.getData());
            mAllOrderRecords.addAll(newOrderResponse.getData());
            deviceAdapter.notifyDataSetChanged();
        }
    }

    public void showOpenDialog() {
        if (openDialog != null) {
            openDialog.show();
        } else {
            View contentView = LayoutInflater.from(this).inflate(R.layout.unlock_succ_dialog, null);
            Button confirmBt = contentView.findViewById(R.id.bt_confirm);
            confirmBt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog.dismiss();
                }
            });
            openDialog = new MaterialDialog.Builder(this)
                    .customView(contentView, false)
                    .show();
            Window window = openDialog.getWindow();
            window.setBackgroundDrawableResource(android.R.color.transparent);
        }

    }

    public void dismissUpdateDialog() {
        if (openDialog != null && openDialog.isShowing()) {
            openDialog.dismiss();
        }
    }
}
