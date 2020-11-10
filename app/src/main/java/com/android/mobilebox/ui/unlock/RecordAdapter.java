package com.android.mobilebox.ui.unlock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.core.bean.user.TerminalResult;
import com.android.mobilebox.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private Context context;
    private List<TerminalResult> mData;

    public RecordAdapter(Context context, List<TerminalResult> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_record_layout, viewGroup, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TerminalResult terminalResult = mData.get(i);
        if ("id1".equals(terminalResult.getCap_id())) {
            viewHolder.tvInoutResult.setVisibility(View.GONE);
            viewHolder.tvOperateType.setText("开锁");
        } else if ("id2".equals(terminalResult.getCap_id())) {
            viewHolder.tvInoutResult.setVisibility(View.GONE);
            viewHolder.tvOperateType.setText("关锁");
        } else if ("id3".equals(terminalResult.getCap_id())) {
            viewHolder.tvInoutResult.setVisibility(View.VISIBLE);
            viewHolder.tvOperateType.setText("存取件");
            String result= "";
            result += "   存件：" + terminalResult.getProp().getRfid_in() + "\n";
            result += "   取件：" + terminalResult.getProp().getRfid_out() + "\n";
            viewHolder.tvInoutResult.setText(result);
        }
        viewHolder.tvOperateTime.setText(DateUtils.long2String(terminalResult.getGmtCreate(),"MM月dd日 hh:mm"));
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.tv_type)
        TextView tvOperateType;
        @BindView(R.id.tv_operate_time)
        TextView tvOperateTime;
        @BindView(R.id.tv_inout_result)
        TextView tvInoutResult;


        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
