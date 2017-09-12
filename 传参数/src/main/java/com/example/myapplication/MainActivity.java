package com.example.myapplication;

import android.content.ClipData;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    List<ItemBean> list;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        list = new ArrayList<ItemBean>();
        for (int i = 0; i < 10; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.setButton("button" + i);
            itemBean.setTest(i + "");
            list.add(itemBean);
        }
        customAdapter = new CustomAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(customAdapter);
    }

    public void testclick(View view) {
        Toast.makeText(this,"test",Toast.LENGTH_LONG).show();
        i++;
        ItemBean itemBean = new ItemBean();
        itemBean.setButton("新增加button" + i);
        itemBean.setTest(i + "");
        list.add(itemBean);
        customAdapter.notifyDataSetChanged();
    }
}
