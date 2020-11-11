package com.android.mobilebox.ui.login;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.mobilebox.MainActivity;
import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.LoginContract;
import com.android.mobilebox.core.DataManager;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.LoginUser;
import com.android.mobilebox.core.http.api.GeeksApis;
import com.android.mobilebox.core.http.client.RetrofitClient;
import com.android.mobilebox.presenter.LoginPresenter;
import com.android.mobilebox.ui.unlock.DeviceActivity;
import com.android.mobilebox.utils.RxUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.observers.ResourceObserver;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


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
        final LoginUser loginUser = new LoginUser();
        loginUser.setUsername(mAccountEdit.getText().toString());
        loginUser.setPassword(mPasswordEdit.getText().toString());
        mPresenter.login(loginUser);
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

    public void testUploadFace(){
        File file = new File("/storage/emulated/0/Pictures/2.png");
        boolean exists = file.exists();
        Log.e("LoginActivity",file.exists() + "");
        RequestBody imgBody = RequestBody.create(MediaType.parse("image/*"), file);
        //将文件转化为MultipartBody.Part
        //第一个参数：上传文件的key；第二个参数：文件名；第三个参数：RequestBody对象
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), imgBody);
        RetrofitClient.getInstance().create(GeeksApis.class).uploadFace(filePart)
                .compose(RxUtils.rxSchedulerHelper())
                .subscribe(new ResourceObserver<BaseResponse<UploadFaceResponse>>() {
                    @Override
                    public void onNext(BaseResponse<UploadFaceResponse> uploadFaceResponseBaseResponse) {
                        Log.e("LoginActivity",uploadFaceResponseBaseResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("LoginActivity",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, DeviceActivity.class));
        finish();
    }
}
