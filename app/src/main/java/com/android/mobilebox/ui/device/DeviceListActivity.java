package com.android.mobilebox.ui.device;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.DeviceContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.customview.CustomPopWindow;
import com.android.mobilebox.presenter.DevicePresenter;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DeviceListActivity extends BaseActivity<DevicePresenter> implements DeviceContract.View {
    @BindView(R.id.rv_users)
    RecyclerView mRecycleView;
    @BindView(R.id.edit_search)
    EditText editText;
    @BindView(R.id.iv_add_user)
    ImageView ivAddUser;
    @BindView(R.id.title_content)
    TextView mTitle;
    private List<DeviceResponse> mDevices = new ArrayList<>();
    private List<DeviceResponse> mAllDevices = new ArrayList<>();
    private DeviceAdapter deviceAdapter;
    private CustomPopWindow mCustomPopWindow;
    private ImageView addDeviceIm;

    @Override
    public DevicePresenter initPresenter() {
        return new DevicePresenter();
    }

    @Override
    protected void initEventAndData() {
        mTitle.setText("存储柜");
        deviceAdapter = new DeviceAdapter(this,mDevices);
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
                    List<DeviceResponse> filterList = Stream.of(mAllDevices).filter(new Predicate<DeviceResponse>() {

                        @Override
                        public boolean test(DeviceResponse value) {
                            int i = value.getDevName().indexOf(assetsId);
                            return i != -1;
                        }
                    }).collect(Collectors.toList());
                    mDevices.clear();
                    mDevices.addAll(filterList);
                    deviceAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_layout,findViewById(android.R.id.content),false );
        addDeviceIm = contentView.findViewById(R.id.iv_add_user);
        addDeviceIm.setImageResource(R.drawable.add_device_icon);
        mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .enableBackgroundDark(false)
                .size(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getAllDevices();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void initToolbar() {

    }


    @OnClick({R.id.title_back, R.id.iv_add_user})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_add_user:
                mCustomPopWindow.showAsDropDown(ivAddUser);
                break;
        }
    }

    @Override
    public void handleGetAllDevices(BaseResponse<List<DeviceResponse>> devices) {
        if(200000 == devices.getCode()){
            mDevices.clear();
            mAllDevices.clear();
            mDevices.addAll(devices.getData());
            mAllDevices.addAll(devices.getData());
            deviceAdapter.notifyDataSetChanged();
        }
    }

}
