package com.kiplening.demo.activity.Main;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kiplening.androidlib.activity.BaseActivity;
import com.kiplening.demo.R;
import com.kiplening.demo.activity.settings.SettingActivity;
import com.kiplening.demo.MainApplication;
import com.kiplening.demo.module.App;
import com.kiplening.demo.tools.CategoryAdapter;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.demo.tools.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements MainView {

    private List<Map<String, Object>> listItems;
    private List<Map<String, Object>> lock_listItems;
    private ArrayList<String> lockList = MainApplication.getLockList();
    private DataBaseUtil dataBaseUtil;
    private String status;
    private static String TAG = "MAINACTIVITY";
    private int prePosition;
    private int SELECTED=0;
    private int UNSELECTED=1;
    private int isSelected=1;


    @InjectView(R.id.list)
    ListView myList;
    @InjectView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    private MainPresenter presenter;

    @Override
    protected void initVariables() {
        presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);


        ButterKnife.inject(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        floatingActionButton.setVisibility(View.INVISIBLE);
        presenter.checkPromission();

        Toast.makeText(this,String.format(Locale.US,"http://%s/status","localhost:8081"),Toast.LENGTH_SHORT);
    }

    @Override
    protected void loadData() {

    }

    public boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    public boolean isUserApp(PackageInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }

    public boolean isLocked(PackageInfo pInfo, ArrayList<App> lockedApps) {
        for (App a : lockedApps) {
            if (pInfo.applicationInfo.packageName.equals(a.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStop() {
//        lockedApps = dataBaseUtil.getAll(db);
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            finish();
        }
        if (KeyEvent.KEYCODE_HOME == event.getKeyCode()) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
        MenuItem item = menu.findItem(R.id.action_check);

        status = dataBaseUtil.getStatus();
        if (status.equals("true")){
            item.setChecked(true);
        }
        else {
            item.setChecked(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent i = new Intent(MainActivity.this, SettingActivity.class);
//
//            startActivity(i);
//            return true;
//        }
        if (id == R.id.action_check) {
            dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
            status = dataBaseUtil.getStatus();
            if (status.equals("false")){
                        dataBaseUtil.setStatus("true");
                        item.setChecked(true);
                        Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                        //Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
                        intent.putStringArrayListExtra("lockList", lockList);
                        intent.putExtra("status", "false");
                        sendBroadcast(intent);
                    } else {
                item.setChecked(false);
                        dataBaseUtil.setStatus("false");
                        Intent intent = new Intent("android.intent.action.SET_BROADCAST");
                        //Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
                        intent.putStringArrayListExtra("lockList", lockList);
                        intent.putExtra("status", "false");
                        sendBroadcast(intent);
                    }



        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void RequestPromission() {
        new AlertDialog.Builder(this).
                setTitle("设置").
                //setMessage("开启usagestats权限")
                setMessage(String.format(Locale.US,"请允许应用锁查看应用的使用情况。"))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        startActivity(intent);
                        //finish();
                    }
                }).show();
    }

    @Override
    public void showList(ArrayList<App> lockedApps, String status) {

        Toast.makeText(this,
                String.format(Locale.US,"http://%s/status","localhost:8081") + "---hello",
                Toast.LENGTH_SHORT);
        if (status.equals("error")) {
            ContentValues cv = new ContentValues();
            cv.put("status", "true");
            //db.insert("settings", null, cv);
        }
        listItems = new ArrayList<>();
        lock_listItems = new ArrayList<>();
        ArrayList<String> appList = new ArrayList<>();
        List<PackageInfo> packages = getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (isUserApp(packageInfo)) {
                appList.add(packageInfo.packageName);
                Map<String, Object> map = new HashMap<>();
                map.put("info", "installed app");
                map.put("name",
                        packageInfo.applicationInfo.loadLabel(
                                getPackageManager()).toString());
                map.put("packageName", packageInfo.applicationInfo.packageName);
                map.put("icon", packageInfo.applicationInfo
                        .loadIcon(getPackageManager()));
                if (isLocked(packageInfo, lockedApps)) {
                    map.put("flag", "已锁定");
                    lock_listItems.add(map);
                    lockList.add(packageInfo.applicationInfo.packageName);
                } else {
                    map.put("flag", "锁定");
                    listItems.add(map);

                }

                //lockList.add(packageInfo.applicationInfo.packageName);


                Log.i("test", packageInfo.applicationInfo.loadLabel(
                        getPackageManager()).toString());
            }
        }
        //myList = (ListView) findViewById(R.id.list);
        //listViewAdapter = new ListViewAdapter(this, listItems);
        final CategoryAdapter adapter=new CategoryAdapter() {
            @Override
            public View getTitleView(String caption, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView= LayoutInflater.from(MainActivity.this).inflate(R.layout.title_items,parent,false);
                    System.out.println("log"+caption);
                }
                convertView= LayoutInflater.from(MainActivity.this).inflate(R.layout.title_items,parent,false);
                    ((TextView) convertView.findViewById(R.id.title)).setText(caption);
                return convertView;
            }
        };

        ListViewAdapter listViewAdapter = new ListViewAdapter(this,listItems,lock_listItems);

        adapter.addCategery("已封印",new ListViewAdapter(this,lock_listItems,listItems));
        adapter.addCategery("未封印",listViewAdapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myList.setSelector(new ColorDrawable(getResources().getColor(R.color.third_purple)));
                if (position==prePosition){
                    judge(position);

                }
                else {
                    System.out.println(position);
                    isSelected=SELECTED;
                    if (position>lock_listItems.size()){
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                    else{
                        floatingActionButton.setVisibility(View.INVISIBLE);
                    }
                    prePosition=position;
                }
                myList.forceLayout();

            }

            private void judge(int position) {
                if(isSelected==UNSELECTED){
                    isSelected=SELECTED;
                    if (position>lock_listItems.size()) {
                        floatingActionButton.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    isSelected=UNSELECTED;
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    myList.setItemChecked(position,false);
                    //view.setBackgroundColor(getResources().getColor(R.color.white));
                    myList.setSelector(new ColorDrawable(Color.TRANSPARENT));

                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position;
                position = prePosition - lock_listItems.size()-2;
                App app = new App((String) listItems.get(position).get("packageName"),(String) listItems.get(position).get("name"));


                    listItems.get(position).put("flag", "已锁定");
                    lock_listItems.add(listItems.get(position));
                    listItems.remove(position);
                    if(dataBaseUtil.insert(app) == -1){
                      //  Log.i(tableName, "insert failed! ");
                    }
                    else {
                       // Log.i(tableName, "insert success! ");
                    }
                    System.out.println("lock");

                //thread.start();
                    floatingActionButton.setVisibility(View.INVISIBLE);
                    adapter.notifyDataSetChanged();
                    //notifyDataSetChanged();



                String status = dataBaseUtil.getStatus();
                ArrayList<String> lockList = dataBaseUtil.getAllLocked();
                if (status.equals("true")){
                    Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
                    intent.putStringArrayListExtra("lockList", lockList);
                    intent.putExtra("status", "true");
                    sendBroadcast(intent);
                }else{
                    Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
                    intent.putStringArrayListExtra("lockList", lockList);
                    intent.putExtra("status","false");
                    sendBroadcast(intent);
                }
            }
        });

        myList.setAdapter(adapter);

        if (status.equals("true")) {
            Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
            intent.putStringArrayListExtra("lockList", lockList);
            intent.putExtra("status", "true");
            sendBroadcast(intent);
        } else {
            Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
            intent.putStringArrayListExtra("lockList", lockList);
            intent.putExtra("status", "false");
            sendBroadcast(intent);
        }
    }
    @Override
    public void onDestory() {
        presenter.onDestory();
        super.onDestroy();
    }

    /**
     * Activity 跳转
     */

    @Override
    public void navigationToSetting() {

        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }
}
