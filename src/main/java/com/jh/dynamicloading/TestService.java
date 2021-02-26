package com.jh.dynamicloading;

public class TestService {
    private String id;
    private String name;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String sout() {
        System.out.println("动态加载bean 调用成功"+this.name);
        return name;
    }
}