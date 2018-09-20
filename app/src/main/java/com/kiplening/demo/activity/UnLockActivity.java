package com.kiplening.demo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kiplening.demo.MainApplication;
import com.kiplening.demo.R;
import com.kiplening.demo.activity.Main.MainActivity;
import com.kiplening.demo.service.LockService;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.androidlib.activity.BaseActivity;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;

/**
 * Created by MOON on 1/19/2016.
 */
public class UnLockActivity extends BaseActivity{

    DataBaseUtil dataBaseUtil;
    @InjectView(R.id.unlocktext)
    TextView textView ;
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_unlock);


    }
    @Override
    protected void loadData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        //屏蔽后退键
        if(KeyEvent.KEYCODE_BACK == event.getKeyCode())
        {
            return true;//阻止事件继续向下分发
        }
        return super.onKeyDown(keyCode, event);
    }
}
