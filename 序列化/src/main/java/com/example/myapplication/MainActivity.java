package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ontest(View view) {
        Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
        Student student = new Student();
        student.setAge(100);
//        student.setName("alex");
        student.setSex("男");
        FileManager fileManager = new FileManager();
        fileManager.saveObject(this, student, "student");
    }


    public void ontest1(View view){
        Toast.makeText(this, "ok", Toast.LENGTH_LONG).show();
        Student student;
        student= (Student) FileManager.getObject(this,"student");
        if(student!=null){
//            ((TextView)view).setText(student.getName());
            ((TextView)view).setText("反序列化成功");
        }

    }
}
