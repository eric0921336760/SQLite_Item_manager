package com.alex.week6_sqlitehw;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class Modify_OrderActivity extends AppCompatActivity {

    EditText item_id,buy_or_sell,number,datetime;
    long position;
    private SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_order);

        db=openOrCreateDatabase("db1.db", Context.MODE_PRIVATE,null);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Double width = dm.widthPixels * .8;
        Double height = dm.heightPixels * .6;

        getWindow().setLayout(width.intValue(),height.intValue());

        Intent it = getIntent();
        position=it.getLongExtra("id",0);
        findview();

        Cursor cursor = db.rawQuery("SELECT * FROM table03 WHERE _id="+position,null);
        cursor.moveToFirst();

        item_id.setText(cursor.getString(1));
        buy_or_sell.setText(cursor.getString(2));
        number.setText(cursor.getString(3));
        datetime.setText(cursor.getString(4));

        //Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }

    public void findview(){
        item_id=(EditText)findViewById(R.id.editText_item_id);
        buy_or_sell=(EditText)findViewById(R.id.editText_buy_or_sell);
        number=(EditText)findViewById(R.id.editText_number);
        datetime=(EditText)findViewById(R.id.editText_datetime);

    }

    public void save(View view){

        ContentValues cv = new ContentValues();

        cv.put("item_id",Integer.parseInt(item_id.getText().toString()));
        cv.put("buy_or_sell",Integer.parseInt(buy_or_sell.getText().toString()));
        cv.put("number",Integer.parseInt(number.getText().toString()));
        cv.put("datetime",datetime.getText().toString());
        db.update("table03",cv,"_id="+position,null);
        Intent intent=new Intent();
        setResult(0,intent);
        this.finish();
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
