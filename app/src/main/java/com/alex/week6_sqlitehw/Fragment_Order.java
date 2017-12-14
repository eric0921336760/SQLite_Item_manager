package com.alex.week6_sqlitehw;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class Fragment_Order extends Fragment {
    //宣告db
    private SQLiteDatabase db = null;

    private final static String CREATE_TABLE = "CREATE TABLE" +
            " table03 (_id INTEGER PRIMARY KEY,item_id INTEGER NOT NULL,"+
            "buy_or_sell INTEGER,number INTEGER,datetime TEXT," +
            "FOREIGN KEY(item_id) REFERENCES table02(_id))";

    ListView listView01;
    Button btnDo,btnADD;
    EditText edtSQL;
    String str, datetime;
    int item_id,buy_or_sell,number;
    int n=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        listView01=(ListView)view.findViewById(R.id.listview01);
        btnDo=(Button)view.findViewById(R.id.btnDo);
        btnADD=(Button)view.findViewById(R.id.btnAdd);
        edtSQL=(EditText)view.findViewById(R.id.edtSQL);

        btnDo.setOnClickListener(btnDoClick);
        btnADD.setOnClickListener(btnAddClick);
        //預設SQL指令為新增資料
        item_id =n;//要記得改外來鍵
        buy_or_sell=n;
        number=n;
        datetime ="Date"+n;
        str="INSERT INTO table03 (item_id,buy_or_sell,number,datetime) " +
                "values("+ item_id + "," +buy_or_sell + "," + number + ",'" + datetime +"')";
        edtSQL.setText(str);

        //getActivity().deleteDatabase("db1.db");
        //建立資料庫,若資料庫已經存在則將之開啟
        db= getActivity().openOrCreateDatabase("db1.db",Context.MODE_PRIVATE,null);
        try{
            db.execSQL(CREATE_TABLE);
        }catch(Exception e){
            UpdateAdapter();
        }


        listView01.setOnItemClickListener(listviewlistener);
        listView01.setOnItemLongClickListener(listviewlongclicklistener);
        // Inflate the layout for this fragment
        return view;
    }

    private Button.OnClickListener btnDoClick = new
            Button.OnClickListener(){
                @Override
                public void onClick(View view) {
                    try{
                        db.execSQL(edtSQL.getText().toString());
                        UpdateAdapter();
                        n++;

                        item_id =n;//要記得改外來鍵
                        buy_or_sell=n;
                        number=n;
                        datetime ="Date"+n;
                        str="INSERT INTO table03 (item_id,buy_or_sell,number,datetime) " +
                                "values("+ item_id + "," +buy_or_sell + "," + number + ",'" + datetime +"')";
                        edtSQL.setText(str);


                    }catch(Exception e){
                        getActivity().setTitle("SQL 語法錯誤");
                    }
                }
            };


    private Button.OnClickListener btnAddClick = new
            Button.OnClickListener(){
                @Override
                public  void onClick(View view){
                    Intent it = new Intent();
                    it.setClass(getActivity(),Add_OrderActivity.class);
                    startActivityForResult(it,1);
                }
            };

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==1){
            if(resultCode==0){
                UpdateAdapter();
            }
            else if(resultCode == -1){

            }
        }

        if(requestCode==2){
            if(resultCode==0){
                UpdateAdapter();
            }
            else if(resultCode == -1){

            }
        }
    }

    public void UpdateAdapter(){
        Cursor cursor = db.rawQuery("SELECT * FROM table03",null);
        if(cursor != null && cursor.getCount()>=0){
            SimpleCursorAdapter adapter = new
                    SimpleCursorAdapter(getActivity(),
                    R.layout.listdata_order,
                    cursor,
                    new String[] {"item_id","buy_or_sell","number","datetime"},
                    new int[] { R.id.txtItem_id,
                            R.id.txtBuy_or_Sell,
                            R.id.txtNumber,
                            R.id.txtDatetime},
                    0);
            listView01.setAdapter(adapter);
        }
    }

    public ListView.OnItemClickListener listviewlistener = new
            ListView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id){

                    //Toast.makeText(getActivity(),"short"+id,Toast.LENGTH_SHORT).show();
                    Intent it = new Intent();
                    it.setClass(getActivity(),Modify_OrderActivity.class);
                    it.putExtra("id",id);
                    startActivityForResult(it,2);

                }

            };

    public ListView.OnItemLongClickListener listviewlongclicklistener = new
            ListView.OnItemLongClickListener(){
                public boolean onItemLongClick(AdapterView<?> parent,View view,
                                               int position,long id){
                    //Toast.makeText(getActivity(),"long"+id,Toast.LENGTH_SHORT).show();

                    final long Id=id;
                    new AlertDialog.Builder(getActivity())
                            .setTitle("刪除資料")
                            .setMessage("確定刪除資料嗎?")
                            .setPositiveButton("刪除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.delete("table03","_id="+Id,null);
                                    UpdateAdapter();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return true;
                }
            };





    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //db.execSQL("DROP TABLE table01");
        db.close();
    }

}
