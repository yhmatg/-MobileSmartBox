package com.android.mobilebox.ui.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.core.bean.user.OrderResponse;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRecordAdapter extends RecyclerView.Adapter<UserRecordAdapter.ViewHolder> {
    private Context context;
    private List<OrderResponse> mOrderRecords;

    public UserRecordAdapter(Context context, List<OrderResponse> mOrderRecords) {
        this.context = context;
        this.mOrderRecords = mOrderRecords;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_user_record_layout, viewGroup, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        OrderResponse orderResponse = mOrderRecords.get(i);
        Glide.with(context).load(orderResponse.getUser().getFaceImg()).into( viewHolder.tvUserIcon);
        viewHolder.tvUserName.setText(orderResponse.getUser().getUsername());
        viewHolder.tvOperateTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(orderResponse.getGmtModified())));
    }

    @Override
    public int getItemCount() {
        return mOrderRecords == null ? 0 : mOrderRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_user_icon)
        ImageView tvUserIcon;
        @BindView(R.id.tv_user_name)
        TextView tvUserName;
        @BindView(R.id.iv_operate_time)
        TextView tvOperateTime;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
