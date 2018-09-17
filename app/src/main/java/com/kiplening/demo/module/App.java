package com.kiplening.demo.module;

/**
 * Created by MOON on 1/20/2016.
 */
public class App {
    private String packageName;
    private String name;



    private String date;

    public App(String packageName, String name,String date) {
        this.packageName = packageName;
        this.name = name;
        this.date = date;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setName(String name) {
        this.name = name;
    }
}
