package com.alex.week6_sqlitehw;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Modify_ProviderActivity extends AppCompatActivity {

    EditText Name,Address,PhoneNo;
    long position;
    private SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_prodiver);

        db=openOrCreateDatabase("db1.db", Context.MODE_PRIVATE,null);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Double width = dm.widthPixels * .8;
        Double height = dm.heightPixels * .6;

        getWindow().setLayout(width.intValue(),height.intValue());

        Intent it = getIntent();
        position=it.getLongExtra("id",0);
        findview();

        Cursor cursor = db.rawQuery("SELECT * FROM table01 WHERE _id="+position,null);
        cursor.moveToFirst();
        String s=cursor.getString(2);
        Name.setText(cursor.getString(1));
        PhoneNo.setText(cursor.getString(2));
        Address.setText(cursor.getString(3));
        //Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }

    public void findview(){
        Name=(EditText)findViewById(R.id.editText_name);
        Address=(EditText)findViewById(R.id.editText_address);
        PhoneNo=(EditText)findViewById(R.id.editText_phoneNo);
    }

    public void save(View view){

        ContentValues cv = new ContentValues();
        cv.put("name",Name.getText().toString());
        cv.put("phoneNo",PhoneNo.getText().toString());
        cv.put("address",Address.getText().toString());

        db.update("table01",cv,"_id="+position,null);
        Intent intent=new Intent();
        setResult(0,intent);
        this.finish();
    }

    public void SearchItem(View view) {
        Cursor cursor = db.query("table02",new String[]{"name"},"provider_id="+position,null,null,null,null,null);
        String str="";

        cursor.moveToFirst();
        for(int i=0;i<cursor.getCount();i++){
            //Toast.makeText(this,cursor.getString(0),Toast.LENGTH_SHORT).show();
            str+="\n"+cursor.getString(0);
            cursor.moveToNext();
        }
        Toast.makeText(this,"此店家提供的商品有："+str,Toast.LENGTH_SHORT).show();
    }
    public void cencel(View view){
        Intent intent=new Intent();
        setResult(-1,intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        setResult(-1,intent);
        this.finish();
        super.onBackPressed();
    }
}
