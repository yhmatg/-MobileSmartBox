package com.android.mobilebox.ui.unlock;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.contract.DeviceContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.presenter.DevicePresenter;
import com.android.mobilebox.utils.StringUtils;
import com.android.mobilebox.utils.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DeviceActivity extends BaseActivity<DevicePresenter> implements DeviceContract.View {
    @BindView(R.id.title_content)
    TextView mTitle;
    @BindView(R.id.rv_devices)
    RecyclerView recyclerView;
    DeviceAdapter deviceAdapter;
    private List<DeviceResponse> mDevices = new ArrayList<>();

    @Override
    public DevicePresenter initPresenter() {
        return new DevicePresenter();
    }

    @Override
    protected void initEventAndData() {
        mTitle.setText("设备列表");
        deviceAdapter = new DeviceAdapter(this, mDevices);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deviceAdapter);
        mPresenter.getAllDevices();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_device;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void handleGetAllDevices(BaseResponse<List<DeviceResponse>> devices) {
        if (200000 == devices.getCode()) {
            mDevices.clear();
            mDevices.addAll(devices.getData());
            deviceAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.title_back})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }
}
