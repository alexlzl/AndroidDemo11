package com.ihongqiqu.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ihongqiqu.databinding.data.Student;

public class DataBindingSampleActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Student student=new Student("alex","30");
       ViewDataBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_data_binding_sample4);
        binding.setVariable(BR.data,student);
    }
}
