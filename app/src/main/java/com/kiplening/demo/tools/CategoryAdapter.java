package com.kiplening.demo.tools;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public abstract class CategoryAdapter extends BaseAdapter {

    //用于存储分类和类型数据的集合,这里每一个对象为一个分类和其相对应的数据。
    private List<Category> categories=new ArrayList();
    public Callback cb;
    public void addCategery(String title,Adapter adapter){
        categories.add(new Category(adapter,title));
    }



    @Override
    public int getCount() {
        int total=0;
        for (Category category:categories){
            total=total+category.getmAdapter().getCount()+1;
        }
        return total;
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public interface Callback{
        public abstract void callback();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            cb=new Callback() {
            @Override
            public void callback() {
                notifyDataSetChanged();
            }
        };
        for (Category category:categories){
            //如果是第一个，则显示分类的名称
            if (position == 0){
                return getTitleView(category.getmTitle(),convertView,parent);
            }
            //否则显示子分类的名称
            //每个分类加上其子类的数量
            int size=category.getmAdapter().getCount()+1;
            if (position < size){
                return ((ListViewAdapter)category.getmAdapter()).getView(position-1,convertView,parent,cb);
            }
            //当position为0时，说明一个分类的数据已经展示完毕，紧接着展示下一个分类的数据
            position=position-size;
        }
        return null;
    }

    //获取分类数据视图的方法,OOP的思想,留给子类实现该方法，
    public abstract View getTitleView(String caption, View convertView, ViewGroup parent);

}
