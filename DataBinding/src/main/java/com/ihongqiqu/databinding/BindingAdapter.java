package com.ihongqiqu.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihongqiqu.databinding.databinding.RvItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhouliang on 2017/7/12.
 */

public class BindingAdapter extends RecyclerView.Adapter {
    private LayoutInflater layoutInflater;
    private List<RvViewModel.Item> listdata;


    public BindingAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        listdata=new ArrayList<>();
        for(int i=0;i<10;i++){
            RvViewModel.Item item=new RvViewModel.Item();
            item.setAge(i+1+"");
            item.setTitle("alex");
            listdata.add(item);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.rv_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(binding.getRoot());
        myViewHolder.setRvItemBinding(binding);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        RvViewModel.Item viewmodel=listdata.get(position);
        ((MyViewHolder)holder).rvItemBinding.setVariable(BR.rv_item_data,viewmodel);
        ((MyViewHolder)holder).rvItemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        View item;
        TextView title;
        TextView age;
        RvItemBinding rvItemBinding;

        public MyViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            title= (TextView) item.findViewById(R.id.title_tv);
            age= (TextView) item.findViewById(R.id.age_tv);
        }

        public void setRvItemBinding(RvItemBinding itemBinding){
            rvItemBinding=itemBinding;
        }
    }
}
