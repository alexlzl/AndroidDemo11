package com.ihongqiqu.databinding;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ihongqiqu.databinding.databinding.ActivityDataBindingRvBinding;

public class DataBindingRV extends AppCompatActivity {
    RvViewModel rvViewModel;
    BindingAdapter bindingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingRvBinding bindingRvBinding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding_rv);
        rvViewModel = new RvViewModel();
        rvViewModel.setName("lzl");
        bindingRvBinding.setRvData(rvViewModel);
        bindingAdapter = new BindingAdapter(this);
        bindingRvBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        bindingRvBinding.rv.setAdapter(bindingAdapter);
    }
}
