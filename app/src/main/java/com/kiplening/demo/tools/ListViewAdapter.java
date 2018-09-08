package com.kiplening.demo.tools;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kiplening.demo.R;
import com.kiplening.demo.MainApplication;
import com.kiplening.demo.module.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by MOON on 1/19/2016.
 */
public class ListViewAdapter extends BaseAdapter{

    private Context context;
    private LayoutInflater listContainer;
    private List<Map<String, Object>> listItems;
    private List<Map<String, Object>> otherlistItems;
    private boolean[] hasChecked;
    ListItemView listItemView = null;
    private onClickItemListener onClickItemListener;

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_CONTENT = 1;

    private SQLiteDatabase db;
    private String dataBaseName = "kiplening";
    private String tableName = "app";
    private final DataBaseHelper helper;
    private DataBaseUtil dataBaseUtil;

    private CategoryAdapter.Callback cb;

    public ListViewAdapter(Context context, List<Map<String,Object>> listItems, List<Map<String,Object>> otherlistItems) {
        this.context = context;
        dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
        helper = new DataBaseHelper(context,dataBaseName,null,1,null);
        db = helper.getWritableDatabase();
        //创建视图容器
        listContainer = LayoutInflater.from(context);
        this.listItems = listItems;
        this.otherlistItems = otherlistItems;
        hasChecked = new boolean[getCount()];
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        System.out.println("log: notifydatasetchanged is executed"+getCount());
    }


    @Override
    public  View getView(final int position, View convertView, ViewGroup parent){

        System.out.println("log: getview is executed"+listItems.get(position).get("name"));
        if (convertView == null){

            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_item, null);
            //System.out.println("log"+listItems.get(position).get("name"));
            //获取控件对象
            listItemView.icon = (ImageView)convertView.findViewById(R.id.icon);
            listItemView.name = (TextView)convertView.findViewById(R.id.name);
            listItemView.info = (TextView)convertView.findViewById(R.id.info);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        }else {

            //测试
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_item, null);
            //获取控件对象
            listItemView.icon = (ImageView)convertView.findViewById(R.id.icon);
            listItemView.name = (TextView)convertView.findViewById(R.id.name);
            listItemView.info = (TextView)convertView.findViewById(R.id.info);

            //设置控件集到convertView
            convertView.setTag(listItemView);



            //listItemView = (ListItemView)convertView.getTag();
        }
        listItemView.icon.setImageDrawable((Drawable)listItems.get(position).get("icon"));
        listItemView.name.setText((String) listItems.get(position).get("name"));
        listItemView.info.setText((String)listItems.get(position).get("info"));


        return convertView;
    };
    public View getView(final int position, View convertView, ViewGroup parent, CategoryAdapter.Callback cb) {
        //TODO 获取布局信息
        final CategoryAdapter.Callback cbc=cb;
        if (convertView == null){

            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_item, null);
            //System.out.println("log"+listItems.get(position).get("name"));
            //获取控件对象
            listItemView.icon = (ImageView)convertView.findViewById(R.id.icon);
            listItemView.name = (TextView)convertView.findViewById(R.id.name);
            listItemView.info = (TextView)convertView.findViewById(R.id.info);

            //设置控件集到convertView
            convertView.setTag(listItemView);
        }else {

            //测试
            listItemView = new ListItemView();
            //获取list_item布局文件的视图
            convertView = listContainer.inflate(R.layout.list_item, null);
            //获取控件对象
            listItemView.icon = (ImageView)convertView.findViewById(R.id.icon);
            listItemView.name = (TextView)convertView.findViewById(R.id.name);
            listItemView.info = (TextView)convertView.findViewById(R.id.info);

            //设置控件集到convertView
            convertView.setTag(listItemView);



            //listItemView = (ListItemView)convertView.getTag();
        }
        listItemView.icon.setImageDrawable((Drawable)listItems.get(position).get("icon"));
        listItemView.name.setText((String) listItems.get(position).get("name"));
        listItemView.info.setText((String)listItems.get(position).get("info"));

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO:显示封印/解封按钮
//
//                onClickItemListener.onClick();
//
//
//            }
//        });
//        listItemView.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //TODO:锁上该应用
//                App app = new App((String) listItems.get(position).get("packageName"),(String) listItems.get(position).get("name"));
//                if (listItems.get(position).get("flag").equals("已锁定")) {
//                    listItems.get(position).put("flag", "锁定");
//                    otherlistItems.add(listItems.get(position));
//                    listItems.remove(position);
//
//                    if(dataBaseUtil.delete(app) == 0){
//                        Log.i(tableName, "delete failed! ");
//                    }
//                    else {
//                        Log.i(tableName, "delete success! ");
//                    }
//                    System.out.println("delete");
//                }
//                else {
//                    listItems.get(position).put("flag", "已锁定");
//                    otherlistItems.add(listItems.get(position));
//                    listItems.remove(position);
//                    if(dataBaseUtil.insert(app) == -1){
//                        Log.i(tableName, "insert failed! ");
//                    }
//                    else {
//                        Log.i(tableName, "insert success! ");
//                    }
//                    System.out.println("lock");
//                }
//                //thread.start();
//
//                    cbc.callback();
//                    //notifyDataSetChanged();
//
//
//
//                String status = dataBaseUtil.getStatus();
//                ArrayList<String> lockList = dataBaseUtil.getAllLocked();
//                if (status.equals("true")){
//                    Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
//                    intent.putStringArrayListExtra("lockList", lockList);
//                    intent.putExtra("status", "true");
//                    context.sendBroadcast(intent);
//                }else{
//                    Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
//                    intent.putStringArrayListExtra("lockList", lockList);
//                    intent.putExtra("status","false");
//                    context.sendBroadcast(intent);
//                }
//
//            }
//        });
        return convertView;
    }

    /**
     * 封装两个视图组件的类
     */
    class ListItemView {
        ImageView icon;
        TextView name;
        TextView info;
    }
    public interface onClickItemListener{
        public abstract void onClick();
    }
    public void  setonClickItemLisener( onClickItemListener onClickItemListener){
        this.onClickItemListener = onClickItemListener;
    }
}
