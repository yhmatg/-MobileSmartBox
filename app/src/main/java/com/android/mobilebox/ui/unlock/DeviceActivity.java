package com.android.mobilebox.ui.unlock;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.contract.DeviceContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.DeviceResponse;
import com.android.mobilebox.core.bean.user.FaceBody;
import com.android.mobilebox.presenter.DevicePresenter;
import com.android.mobilebox.ui.uploadface.UploadFaceActivity;
import com.android.mobilebox.ui.user.AddUserActivity;
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
    @BindView(R.id.rv_devices)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    DeviceAdapter deviceAdapter;
    private List<DeviceResponse> mDevices = new ArrayList<>();

    @Override
    public DevicePresenter initPresenter() {
        return new DevicePresenter();
    }

    @Override
    protected void initEventAndData() {
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
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void handleGetAllDevices(BaseResponse<List<DeviceResponse>> devices) {
        if (200000 == devices.getCode()) {
            mDevices.clear();
            mDevices.addAll(devices.getData());
            deviceAdapter.notifyDataSetChanged();
        }
    }

  /*  @OnClick({R.id.title_back})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update_icon:
                startActivity(new Intent(this, UploadFaceActivity.class));
                return true;
            case R.id.add_user:
                startActivity(new Intent(this, AddUserActivity.class));
                return true;
            default:
                return false;
        }
    }

}
