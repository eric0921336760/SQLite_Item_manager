package com.alex.week6_sqlitehw;

/*
        SQLiteDatabase db = openOrCreateDatabase("DataBase.db",MODE_PRIVATE,null);
        String str="CREATE TABLE Provider" +
                "(_id INTEGER PRIMARY KEY,name TEXT NOT NULL,phoneNo TEXT ,address TEXT)";
        String str2="CREATE TABLE Item" +
                "(_id INTEGER PRIMARY KEY,name TEXT NOT NULL,provider_id INTEGER NOT NULL," +
                "buy_price INTEGER,sell_price INTEGER,stock INTEGER)";
        String str3="CREATE TABLE Order" +
                "(_id INTEGER PRIMARY KEY,item_id INTEGER,buy_or_sell INTEGER,number INTEGER,datetime TEXT)";
        db.execSQL(str);
        db.execSQL(str2);
        db.execSQL(str3);
        */

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void GoToProvider(View view){
        Fragment_Provider fragmentProvider = new Fragment_Provider();
        FragmentManager fragmentMgr = getFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.replace(R.id.framelayout, fragmentProvider);
        fragmentTrans.commit();
    }

    public void GoToItem(View view){
        Fragment_Item fragmentItem = new Fragment_Item();
        FragmentManager fragmentMgr = getFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.replace(R.id.framelayout, fragmentItem);
        fragmentTrans.commit();
    }

    public void GoToOrder(View view){
        Fragment_Order orderFragment = new Fragment_Order();
        FragmentManager fragmentMgr = getFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        fragmentTrans.replace(R.id.framelayout,orderFragment);
        fragmentTrans.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
