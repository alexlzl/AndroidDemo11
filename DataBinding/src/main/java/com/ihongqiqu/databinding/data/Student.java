package com.ihongqiqu.databinding.data;

import android.databinding.BaseObservable;

/**
 * Created by liuzhouliang on 2017/7/7.
 */

public class Student extends BaseObservable {
    public  String name;
    public  String age;

    public Student(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
