package com.android.mobilebox.ui.user;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.UserListContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.presenter.UserListPresenter;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Predicate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserListActivity extends BaseActivity<UserListPresenter> implements UserListContract.View {
    @BindView(R.id.rv_users)
    RecyclerView mRecycleView;
    @BindView(R.id.edit_search)
    EditText editText;
    private List<UserInfo> mUsers = new ArrayList<>();
    private List<UserInfo> mAllUsers = new ArrayList<>();
    private UserAdapter userAdapter;
    @Override
    public UserListPresenter initPresenter() {
        return new UserListPresenter();
    }

    @Override
    protected void initEventAndData() {
        userAdapter = new UserAdapter(this,mUsers);
        mRecycleView.setAdapter(userAdapter);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    String assetsId = editText.getText().toString();
                    editText.setSelection(assetsId.length());
                    List<UserInfo> filterList = Stream.of(mAllUsers).filter(new Predicate<UserInfo>() {

                        @Override
                        public boolean test(UserInfo value) {
                            int i = value.getUsername().indexOf(assetsId);
                            return i != -1;
                        }
                    }).collect(Collectors.toList());
                    mUsers.clear();
                    mUsers.addAll(filterList);
                    userAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });
        mPresenter.getAllUserInfo();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_list;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void handelAllUserInfo(BaseResponse<List<UserInfo>> userInfos) {
        if(200000 == userInfos.getCode()){
            mAllUsers.clear();
            mUsers.clear();
            mAllUsers.addAll(userInfos.getData());
            mUsers.addAll(userInfos.getData());
            userAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.title_back, R.id.iv_add_user})
    void performClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                finish();
                break;
            case R.id.iv_add_user:

                break;
        }
    }
}
