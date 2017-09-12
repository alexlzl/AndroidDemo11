package com.example.myapplication;

import java.io.Serializable;

/**
 * 当不定义序列化serialVersionUID时 ，如果Student类属性发生变换，例如，删除name属性，反序列化student对象为null，定义了serialVersionUID,即使修改了相应当属性，反序列化student对象不为null，相应删除的属性对应默认值
 * <p>
 * 当定义了serialVersionUID，但是在反序列化的时候serialVersionUID发生了改变，这时候反序列化的对象仍然为空
 * <p>
 * 　简单来说，Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的。在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，否则就会出现序列化版本不一致的异常。
 * <p>
 * 有两种生成方式：
 * 一个是默认的1L，比如：private static final long serialVersionUID = 1L;
 * 一个是根据类名、接口名、成员方法及属性等来生成一个64位的哈希字段，比如：
 * private static final   long     serialVersionUID = xxxxL;
 * Created by liuzhouliang on 2017/6/30.
 */

public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    //    private String name;
    private int age;
    private String sex;
//    private String  city;
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
