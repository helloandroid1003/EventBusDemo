package com.zypvv.eventbusdemo;

/**
 * Created by zhang on 2017/4/1.
 */
public class Friend {

    private String name;

    private String age;

    public Friend(String name, String age) {
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
