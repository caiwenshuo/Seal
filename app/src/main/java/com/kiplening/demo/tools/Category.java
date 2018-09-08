package com.kiplening.demo.tools;


import android.widget.Adapter;
import android.widget.ListAdapter;

public class Category {
    private Adapter mAdapter;
    private String mTitle;

    public Category() {

    }

    public Category(Adapter mAdapter, String mTitle) {
        this.mAdapter = mAdapter;
        this.mTitle = mTitle;
    }

    public Adapter getmAdapter() {
        return mAdapter;
    }

    public void setmAdapter(Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
