package com.alex.week6_sqlitehw;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add_OrderActivity extends AppCompatActivity {

    EditText item_id,buy_or_sell,number,datetime;
    private SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Double width = dm.widthPixels * .8;
        Double height = dm.heightPixels * .6;

        getWindow().setLayout(width.intValue(),height.intValue());

        Intent it = getIntent();

        findview();

    }

    public void findview(){
        item_id=(EditText)findViewById(R.id.editText_item_id);
        buy_or_sell=(EditText)findViewById(R.id.editText_buy_or_sell);
        number=(EditText)findViewById(R.id.editText_number);
        datetime=(EditText)findViewById(R.id.editText_datetime);
    }

    public void save(View view){
        db=openOrCreateDatabase("db1.db", Context.MODE_PRIVATE,null);


        try{
            String str="INSERT INTO table03 (item_id,buy_or_sell,number,datetime) " +
                    "values("+Integer.parseInt(item_id.getText().toString())+ "," +
                    Integer.parseInt(buy_or_sell.getText().toString())+ "," +
                    Integer.parseInt(number.getText().toString())+ ",'" +
                    datetime.getText().toString() + "')";
            db.execSQL(str);
            Intent intent=new Intent();
            setResult(0,intent);
            this.finish();
        }catch (Exception e){
            Toast.makeText(this,"尚未輸入商品代碼（外來鍵），\n不能登記訂單哦！",Toast.LENGTH_SHORT).show();
        }
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
    }}
