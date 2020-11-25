package com.android.mobilebox.ui.device;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mobilebox.R;
import com.android.mobilebox.core.bean.user.DeviceResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.ViewHolder> {
    private Context context;
    private List<DeviceResponse> mDevices;

    public DeviceAdapter(Context context, List<DeviceResponse> mDevices) {
        this.context = context;
        this.mDevices = mDevices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_devices_layout, viewGroup, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DeviceResponse device = mDevices.get(i);
        viewHolder.tvDeviceName.setText(device.getDevName());
        viewHolder.tvNumber.setText("数量统计" + device.getRfid_inbox() + "件");
        if("connect".equals(device.getDevStatus())){
            viewHolder.ivStatus.setImageResource(R.drawable.online_icon);
        }else {
            viewHolder.ivStatus.setImageResource(R.drawable.offline_icon);
        }
        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDevices == null ? 0 : mDevices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;
        @BindView(R.id.tv_file_numbers)
        TextView tvNumber;
        @BindView(R.id.device_item_layout)
        RelativeLayout itemLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
