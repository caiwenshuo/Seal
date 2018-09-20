package com.kiplening.demo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.kiplening.demo.MainApplication;
import com.kiplening.demo.activity.Main.MainActivity;
import com.kiplening.demo.tools.DataBaseUtil;

import java.util.ArrayList;

/**
 * Created by MOON on 1/23/2016.
 */
public class Receiver extends BroadcastReceiver {
    static private Context mainContext ;
    private ArrayList<String> lockList;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (mainContext == null){
            this.mainContext = context;
        }
        System.out.println("receiver123 begin");
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
//            Intent intent_n = new Intent(context,
//                    MainActivity.class);
//
//            intent_n.setAction("android.intent.action.MAIN");
//            intent_n.addCategory("android.intent.category.LAUNCHER");
//            intent_n.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent_n);

            System.out.println("receiver123 boot");
            DataBaseUtil dataBaseUtil = new DataBaseUtil(MainApplication.getInstance());
            String status = dataBaseUtil.getStatus();
            ArrayList<String> lockList = dataBaseUtil.getAllLocked();
            if(status.equals("true")){
                System.out.println("checkbox broadcast is true.");
                Intent servIntent = new Intent(context, LockService.class);
                servIntent.putStringArrayListExtra("lockList", lockList);
                servIntent.putExtra("status","true");
                mainContext.startService(servIntent);
            }else if (status.equals("false")){
                System.out.println("checkbox broadcast is false.");
                Intent servIntent = new Intent(context, LockService.class);
                servIntent.putStringArrayListExtra("lockList", lockList);
                servIntent.putExtra("status","false");
                mainContext.startService(servIntent);
            }
        }else{
            lockList = intent.getStringArrayListExtra("lockList");
            if (intent.getAction().equals("android.intent.action.SET_BROADCAST")){
                System.out.println("checkbox broadcast is working.");
                if(intent.getStringExtra("status").equals("true")){
                    System.out.println("checkbox broadcast is true.");
                    Intent servIntent = new Intent(context, LockService.class);
                    servIntent.putStringArrayListExtra("lockList", lockList);
                    servIntent.putExtra("status","true");
                    mainContext.startService(servIntent);
                }else if (intent.getStringExtra("status").equals("false")){
                    System.out.println("checkbox broadcast is false.");
                    Intent servIntent = new Intent(context, LockService.class);
                    servIntent.putStringArrayListExtra("lockList", lockList);
                    servIntent.putExtra("status","false");
                    mainContext.startService(servIntent);
                }
                return;
            }
            if (intent.getAction().equals("android.intent.action.MAIN_BROADCAST")){

                //this.lockList = intent.getStringArrayListExtra("lockList");
                if (intent.getStringExtra("status").equals("false")){
                    Intent servIntent = new Intent(context, LockService.class);
                    servIntent.putStringArrayListExtra("lockList", lockList);
                    servIntent.putExtra("status","false");
                    mainContext.startService(servIntent);
                    return;
                }else {
                    Intent servIntent = new Intent(context, LockService.class);
                    servIntent.putStringArrayListExtra("lockList", lockList);
                    servIntent.putExtra("status","true");
                    mainContext.startService(servIntent);
                    return;
                }

            }
        }

        System.out.print("don't work");
    }
}
