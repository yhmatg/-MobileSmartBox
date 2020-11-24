package com.android.mobilebox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.base.presenter.AbstractPresenter;
import com.android.mobilebox.ui.user.UserListActivity;

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

    @OnClick({R.id.bt_in_out, R.id.bt_users})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.bt_in_out:
                break;
            case R.id.bt_users:
                startActivity(new Intent(this, UserListActivity.class));
                break;
        }
    }
}
