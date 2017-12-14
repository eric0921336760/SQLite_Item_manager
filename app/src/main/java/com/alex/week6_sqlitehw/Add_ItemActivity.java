package com.alex.week6_sqlitehw;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Add_ItemActivity extends AppCompatActivity {

    EditText Name,provider_id,buy_price,sell_price,stock;
    private SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Double width = dm.widthPixels * .8;
        Double height = dm.heightPixels * .6;

        getWindow().setLayout(width.intValue(),height.intValue());

        Intent it = getIntent();

        findview();

    }

    public void findview(){
        Name=(EditText)findViewById(R.id.editText_name);
        provider_id=(EditText)findViewById(R.id.editText_provider_id);
        buy_price=(EditText)findViewById(R.id.editText_buy_price);
        sell_price=(EditText)findViewById(R.id.editText_sell_price);
        stock=(EditText)findViewById(R.id.editText_stock);
    }

    public void save(View view){
        db=openOrCreateDatabase("db1.db", Context.MODE_PRIVATE,null);

        try{
            String str="INSERT INTO table02 (name,provider_id,buy_price,sell_price,stock) " +
                    "values('"+Name.getText().toString()+ "',"+
                    Integer.parseInt(provider_id.getText().toString())+ "," +
                    Integer.parseInt(buy_price.getText().toString())+ "," +
                    Integer.parseInt(sell_price.getText().toString())+ "," +
                    Integer.parseInt(stock.getText().toString()) + ")";
                    db.execSQL(str);
                    Intent intent=new Intent();
                    setResult(0,intent);
                    this.finish();
        }catch (Exception e){
            Toast.makeText(this,"尚未輸入廠商代碼（外來鍵），\n不能登記商品哦！",Toast.LENGTH_SHORT).show();
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
    }
}
