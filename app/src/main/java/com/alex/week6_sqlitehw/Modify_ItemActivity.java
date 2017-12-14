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

public class Modify_ItemActivity extends AppCompatActivity {

    EditText Name,provider_id,buy_price,sell_price,stock;
    long position;
    private SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_item);

        db=openOrCreateDatabase("db1.db", Context.MODE_PRIVATE,null);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Double width = dm.widthPixels * .8;
        Double height = dm.heightPixels * .6;

        getWindow().setLayout(width.intValue(),height.intValue());

        Intent it = getIntent();
        position=it.getLongExtra("id",0);
        findview();

        Cursor cursor = db.rawQuery("SELECT * FROM table02 WHERE _id="+position,null);
        cursor.moveToFirst();

        Name.setText(cursor.getString(1));
        provider_id.setText(cursor.getString(2));
        buy_price.setText(cursor.getString(3));
        sell_price.setText(cursor.getString(4));
        stock.setText(cursor.getString(5));
        //Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }

    public void findview(){
        Name=(EditText)findViewById(R.id.editText_name);
        provider_id=(EditText)findViewById(R.id.editText_provider_id);
        buy_price=(EditText)findViewById(R.id.editText_buy_price);
        sell_price=(EditText)findViewById(R.id.editText_sell_price);
        stock=(EditText)findViewById(R.id.editText_stock);
    }

    public void save(View view){

        ContentValues cv = new ContentValues();
        cv.put("name",Name.getText().toString());
        cv.put("provider_id",Integer.parseInt(provider_id.getText().toString()));
        cv.put("buy_price",Integer.parseInt(buy_price.getText().toString()));
        cv.put("sell_price",Integer.parseInt(sell_price.getText().toString()));
        cv.put("stock",Integer.parseInt(stock.getText().toString()));
        db.update("table02",cv,"_id="+position,null);
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
