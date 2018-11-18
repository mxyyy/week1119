package com.bwie.week1119.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.week1119.R;
import com.bwie.week1119.home.bean.ShopBean;


import java.util.List;



public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context context;
    private List<ShopBean.DataBean.ListBean> list;

    public ShopAdapter(Context context, List<ShopBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_shop, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String images = list.get(position).getImages();
        String[] split = images.replace("|", ",").split(",");

        Glide.with(context).load(split[0]).into(holder.imgLogo);
        holder.txtTitle.setText(list.get(position).getTitle());
        holder.txtPrice.setText("￥ " + list.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtTitle;
        private TextView txtPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_logo);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
