package com.android.mobilebox.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.LoginContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.http.HttpHelperImpl;
import com.android.mobilebox.core.http.client.RetrofitClient;
import com.android.mobilebox.presenter.LoginPresenter;
import com.android.mobilebox.utils.StringUtils;
import com.android.mobilebox.utils.ToastUtils;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author yhm
 * @date 2018/2/26
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {

    @BindView(R.id.edit_account)
    EditText mAccountEdit;
    @BindView(R.id.edit_password)
    EditText mPasswordEdit;
    @BindView(R.id.btn_login)
    Button mLoginBtn;
    @BindView(R.id.password_invisible)
    ImageView ivEye;
    private String TAG = "LoginActivity";
    private boolean isOpenEye = false;
    Toast toast;
    private String hostUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initToolbar() {
    }

    @Override
    protected void initEventAndData() {
        initAccountState();

    }

    private void initAccountState() {
        mAccountEdit.setText(DataManager.getInstance().getLoginAccount());
        mAccountEdit.setSelection(DataManager.getInstance().getLoginAccount().length());
        mPasswordEdit.setText(DataManager.getInstance().getLoginPassword());
        mPasswordEdit.setSelection(DataManager.getInstance().getLoginPassword().length());
    }

    @OnClick({R.id.btn_login, R.id.password_invisible})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.password_invisible:
                settingVisible();
            default:
                break;
        }
    }

    //密码显示
    private void settingVisible() {
        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpenEye) {
                    ivEye.setSelected(false);
                    isOpenEye = false;
                    ivEye.setImageResource(R.drawable.psd_invisible);
                    mPasswordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivEye.setSelected(true);
                    isOpenEye = true;
                    ivEye.setImageResource(R.drawable.psd_visible);
                    mPasswordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }


    private void login() {
        final UserInfo userInfo = new UserInfo();
        userInfo.setUsername(mAccountEdit.getText().toString());
        userInfo.setPassword(mPasswordEdit.getText().toString());
        mPresenter.login(userInfo);
    }

    public void startMainActivity() {

    }


    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startLoginActivity(){

    }
}
