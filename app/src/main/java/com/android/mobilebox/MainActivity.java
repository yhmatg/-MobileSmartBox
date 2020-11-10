package com.android.mobilebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.contract.AddUserContract;
import com.android.mobilebox.ui.unlock.UnlockActivity;
import com.android.mobilebox.ui.uploadface.UploadFaceActivity;
import com.android.mobilebox.ui.user.AddUserActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public AbstractPresenter initPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.bt_upload_face,R.id.bt_unlock,R.id.bt_add_user})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_upload_face:
                startActivity(new Intent(this, UploadFaceActivity.class));
                break;
            case R.id.bt_unlock:
                startActivity(new Intent(this, UnlockActivity.class));
                break;
            case R.id.bt_add_user:
                startActivity(new Intent(this, AddUserActivity.class));
                break;
        }
    }
}
