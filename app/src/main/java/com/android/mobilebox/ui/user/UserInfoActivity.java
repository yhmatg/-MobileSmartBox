package com.android.mobilebox.ui.user;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.app.SmartBoxApplication;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.UserInfoContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.android.mobilebox.core.bean.user.UploadFaceResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.core.bean.user.UserLoginResponse;
import com.android.mobilebox.presenter.UserInfoPresenter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View {

    @BindView(R.id.iv_user_icon)
    ImageView mUserIcon;
    @BindView(R.id.tv_user_name)
    TextView mUserName;
    @BindView(R.id.rv_record_items)
    RecyclerView mRecycleView;
    private RecordAdapter mAdapter;
    private List<OrderResponse> mOrders = new ArrayList<>();
    private UserLoginResponse.LoginUser currentUser;

    @Override
    public UserInfoPresenter initPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected void initEventAndData() {
        currentUser = SmartBoxApplication.getInstance().getUserResponse().getLoginUser();
        if (currentUser != null){
            Glide.with(this).load(currentUser.getFaceImg()).into(mUserIcon);
            mUserName.setText(currentUser.getUsername());
        }
        mAdapter = new RecordAdapter(this,mOrders);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.getAllOrders("","");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void handleUploadFace(BaseResponse<UploadFaceResponse> uploadFaceResponse) {

    }

    @Override
    public void handleupdateFace(BaseResponse<UserInfo> userInfoResponse) {

    }

    @Override
    public void handleGetAllOrders(BaseResponse<List<OrderResponse>> newOrderResponse) {
        if(200000 == newOrderResponse.getCode()){
            mOrders.clear();
            mOrders.addAll(newOrderResponse.getData());
            mAdapter.notifyDataSetChanged();
        }
    }
}
