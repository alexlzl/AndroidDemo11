package com.ihongqiqu.databinding;

import android.view.View;
import android.widget.Toast;

/**
 * Created by liuzhouliang on 2017/7/12.
 */

public class RvViewModel {
    public RvViewModel() {
    }

    public String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public final void onClickName(View view) {
        Toast.makeText(view.getContext(), "onClickName()", Toast.LENGTH_SHORT).show();
    }
    public static  class Item {
        public String age;
        public String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }
}
