package com.alex.week6_sqlitehw;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class Add_ProviderActivity extends AppCompatActivity {

    EditText Name,Address,PhoneNo;
    private SQLiteDatabase db = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prodiver);

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
        Address=(EditText)findViewById(R.id.editText_address);
        PhoneNo=(EditText)findViewById(R.id.editText_phoneNo);
    }

    public void save(View view){
        db=openOrCreateDatabase("db1.db", Context.MODE_PRIVATE,null);
        String str="INSERT INTO table01 (name,phoneNo,address) " +
                "values('"+Name.getText().toString()+ "','"+
                          PhoneNo.getText().toString() + "','" +
                          Address.getText().toString() + "')";
        db.execSQL(str);
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
