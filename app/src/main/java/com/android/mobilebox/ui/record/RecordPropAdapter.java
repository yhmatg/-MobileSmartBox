package com.android.mobilebox.ui.record;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mobilebox.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecordPropAdapter extends RecyclerView.Adapter<RecordPropAdapter.ViewHolder> {
    private Context context;
    private List<PropFileBean> mPropFiles;

    public RecordPropAdapter(Context context, List<PropFileBean> mPropFiles) {
        this.context = context;
        this.mPropFiles = mPropFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.item_prop_file_layout, viewGroup, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        PropFileBean propFileBean = mPropFiles.get(i);
        viewHolder.tvBoxCode.setText(propFileBean.getCode());
        viewHolder.tvDeviceName.setText(propFileBean.getName());
        viewHolder.tvInout.setText(propFileBean.getStatus());
        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPropFiles == null ? 0 : mPropFiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_box_code)
        TextView tvBoxCode;
        @BindView(R.id.tv_device_name)
        TextView tvDeviceName;
        @BindView(R.id.tv_inout)
        TextView tvInout;
        @BindView(R.id.prop_item_layout)
        LinearLayout itemLayout;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
