package com.example.apphelper.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apphelper.R;
import com.example.apphelper.dialog.DialogActivity;
import com.example.apphelper.touch.TouchActivity;
import com.example.apphelper.utlis.ScreenUtils;
import com.example.apphelper.wave.WaveActivity;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

    private Context context;
    private List<MainItemBean> itemBeans;

    private LayoutInflater inflater;

    private int itemWidth;

    public MainAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        itemBeans = new ArrayList<>();
        itemBeans.add(new MainItemBean(R.drawable.android, "自定义View"));
        itemBeans.add(new MainItemBean(R.drawable.android, "Dialog动画"));
        itemBeans.add(new MainItemBean(R.drawable.android, "各种波动效果"));
        itemBeans.add(new MainItemBean(R.drawable.android, "android 触摸事件相关"));
        itemBeans.add(new MainItemBean(R.drawable.android, "android UI大全"));
        itemBeans.add(new MainItemBean(R.drawable.android, "android UI大全"));

        itemWidth = ScreenUtils.getScreenWidth(context) / 3;

    }

    @NonNull
    @Override
    public MainHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.item_main, viewGroup, false);
        return new MainHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainHolder mainHolder, final int i) {
        final MainItemBean bean = itemBeans.get(i);
        mainHolder.imgItemLogo.setImageResource(bean.getResourceId());
        mainHolder.tvItemName.setText(bean.getName());

        ViewGroup.LayoutParams lp = mainHolder.lineItemContainer.getLayoutParams();
        lp.width = itemWidth;
        lp.height = itemWidth;

        mainHolder.lineItemContainer.setLayoutParams(lp);

        mainHolder.lineItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (i) {
                    case 0:
                        context.startActivity(new Intent(context, AndroidUIActivity.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, DialogActivity.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, WaveActivity.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, TouchActivity.class));
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemBeans.size();
    }

    public class MainHolder extends RecyclerView.ViewHolder {
        private LinearLayout lineItemContainer;
        private ImageView imgItemLogo;
        private TextView tvItemName;

        public MainHolder(@NonNull View itemView) {
            super(itemView);
            lineItemContainer = itemView.findViewById(R.id.line_item_container);
            imgItemLogo = itemView.findViewById(R.id.img_item_logo);
            tvItemName = itemView.findViewById(R.id.tv_item_name);
        }

    }

}
