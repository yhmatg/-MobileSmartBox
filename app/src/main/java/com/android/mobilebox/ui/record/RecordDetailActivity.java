package com.android.mobilebox.ui.record;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.app.SmartBoxApplication;
import com.android.mobilebox.base.activity.BaseActivity;
import com.android.mobilebox.contract.RecordDetailContract;
import com.android.mobilebox.core.bean.BaseResponse;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.core.bean.user.UserInfo;
import com.android.mobilebox.presenter.RecordDetailPresenter;
import com.android.mobilebox.utils.StringUtils;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RecordDetailActivity extends BaseActivity<RecordDetailPresenter> implements RecordDetailContract.View {
    @BindView(R.id.iv_user_icon)
    ImageView mUserIcon;
    @BindView(R.id.tv_user_name)
    TextView mUserName;
    @BindView(R.id.tv_time)
    TextView mTime;
    @BindView(R.id.tv_boxcode)
    TextView mBoxCode;
    @BindView(R.id.tv_in)
    TextView mInFiles;
    @BindView(R.id.tv_out)
    TextView mOutFiles;
    @BindView(R.id.rv_prop)
    RecyclerView mRecycleView;
    private RecordPropAdapter mAdapter;
    private List<PropFileBean> propFileBeans = new ArrayList<>();
    private UserInfo currentUser;
    private String relevanceId = "";
    private String deviceId = "";
    @Override
    public RecordDetailPresenter initPresenter() {
        return new RecordDetailPresenter();
    }

    @Override
    protected void initEventAndData() {
        Intent intent = getIntent();
        if(intent != null){
            String operateTime = intent.getStringExtra("OPERATE_TIME");
            String deviceName = intent.getStringExtra("DEVICE_NAME");
            relevanceId = intent.getStringExtra("RELEVANCE_ID");
            deviceId = intent.getStringExtra("DEVICE_ID");
            if(!StringUtils.isEmpty(operateTime)){
                mTime.setText(operateTime);
            }
            if(!StringUtils.isEmpty(deviceName)){
                mBoxCode.setText("柜号：" + deviceName);
            }
        }
        currentUser = SmartBoxApplication.getInstance().getSelectUserInfo();
        if (currentUser != null) {
            Glide.with(this).load(currentUser.getFaceImg()).into(mUserIcon);
            mUserName.setText(currentUser.getUsername());
        }
        mAdapter = new RecordPropAdapter(this,propFileBeans);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mPresenter.getTerminalProp(deviceId,relevanceId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_record_detail;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void handleGetTerminalProp(BaseResponse<List<TerminalResult>> terminalPropResponse) {
        if(200000 == terminalPropResponse.getCode()){
            List<TerminalResult> results = terminalPropResponse.getData();
            for (TerminalResult result : results) {
                if("id3".equals(result.getCapId())){
                    List<String> inEpcs = result.getProp().getRfid_in();
                    List<String> outEpcs = result.getProp().getRfid_out();
                    mInFiles.setText("存件：" + inEpcs.size() + "件");
                    mOutFiles.setText("取件：" + outEpcs.size() + "件");
                    List<PropFileBean> inFiles = Stream.of(inEpcs).map(new Function<String, PropFileBean>() {
                        @Override
                        public PropFileBean apply(String s) {
                            return new PropFileBean(s,"档案","存");
                        }
                    }).collect(Collectors.toList());
                    List<PropFileBean> outFiles = Stream.of(outEpcs).map(new Function<String, PropFileBean>() {
                        @Override
                        public PropFileBean apply(String s) {
                            return new PropFileBean(s,"档案","取");
                        }
                    }).collect(Collectors.toList());
                    propFileBeans.addAll(inFiles);
                    propFileBeans.addAll(outFiles);
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
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
