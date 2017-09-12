package com.example.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by liuzhouliang on 2017/7/13.
 */

public class CustomAdapter extends RecyclerView.Adapter {
    private List<ItemBean> list;
    private Context mcontext;
    private LayoutInflater layoutInflater;

    public CustomAdapter(List<ItemBean> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
        this.layoutInflater=LayoutInflater.from(mcontext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHold myViewHold=new MyViewHold(layoutInflater.inflate(R.layout.item,parent,false));
        return myViewHold;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHold)holder).textView.setText(list.get(position).getTest());
        ((MyViewHold)holder).button.setText(list.get(position).getButton());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static  class  MyViewHold extends RecyclerView.ViewHolder{
        Button button;
        TextView textView;

        public MyViewHold(View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.button);
            textView=itemView.findViewById(R.id.text);
        }
    }
}
