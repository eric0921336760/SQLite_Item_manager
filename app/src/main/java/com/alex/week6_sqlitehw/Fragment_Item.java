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
import android.widget.Toast;

public class Fragment_Item extends Fragment {
    //宣告db
    private SQLiteDatabase db = null;

    private final static String CREATE_TABLE = "CREATE TABLE" +
            " table02(_id INTEGER PRIMARY KEY,name TEXT NOT NULL,provider_id INTEGER NOT NULL,"+
            "buy_price INTEGER,sell_price INTEGER,stock INTEGER," +
            "FOREIGN KEY(provider_id) REFERENCES table01(_id) )";

    ListView listView01;
    Button btnDo,btnADD,btnArrange_ByID,btnArrange_Bystock,btn_searchnum,btn_searchall;
    EditText edtSQL,edtsearchnum;
    String str,name;
    int provider_id,buy_price,sell_price,stock;
    int n=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        listView01=(ListView)view.findViewById(R.id.listview01);
        btnDo=(Button)view.findViewById(R.id.btnDo);
        btnADD=(Button)view.findViewById(R.id.btnAdd);
        btnArrange_ByID=(Button)view.findViewById(R.id.btn_arrangebyid);
        btnArrange_Bystock=(Button)view.findViewById(R.id.btn_arrangebystock);
        btn_searchnum=(Button)view.findViewById(R.id.btn_searchnum) ;
        btn_searchall=(Button)view.findViewById(R.id.btn_searchall);

        edtSQL=(EditText)view.findViewById(R.id.edtSQL);
        edtsearchnum=(EditText)view.findViewById(R.id.editText_Searchnum) ;

        btnDo.setOnClickListener(btnDoClick);
        btnADD.setOnClickListener(btnAddClick);
        btnArrange_ByID.setOnClickListener(btnArrange_ByID_Click);
        btnArrange_Bystock.setOnClickListener(btnArrange_Bystock_Click);
        btn_searchnum.setOnClickListener(btn_searchnum_Click);
        btn_searchall.setOnClickListener(btn_searchall_Click);
        //預設SQL指令為新增資料
        name="Item"+n;
        provider_id=n;//要記得改外來鍵
        buy_price=n;
        sell_price=n;
        stock=n;
        str="INSERT INTO table02 (name,provider_id,buy_price,sell_price,stock) " +
                "values('"+name+ "',"+ provider_id + "," +buy_price + "," + sell_price + "," +stock+")";
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

                        name="Item"+n;
                        provider_id=n;//要記得改外來鍵
                        buy_price=n;
                        sell_price=n;
                        stock=n;
                        str="INSERT INTO table02 (name,provider_id,buy_price,sell_price,stock) " +
                                "values('"+name+ "','"+ provider_id + "','" +buy_price + "','" + sell_price + "','" +stock+"')";
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
                    it.setClass(getActivity(),Add_ItemActivity.class);
                    startActivityForResult(it,1);
                }
            };

     private Button.OnClickListener btnArrange_ByID_Click = new
             Button.OnClickListener(){
               @Override
                 public void onClick(View view){
                   UpdateAdapter();
               }
             };

    private Button.OnClickListener btnArrange_Bystock_Click = new
            Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    Cursor cursor = db.query("table02",null,null,null,null,null,"stock desc",null);
                    if(cursor != null && cursor.getCount()>=0){
                        SimpleCursorAdapter adapter = new
                                SimpleCursorAdapter(getActivity(),
                                R.layout.listdata_item,
                                cursor,
                                new String[] {"name","provider_id","buy_price","sell_price","stock"},
                                new int[] { R.id.txtTitleName,
                                        R.id.txtProvider_id,
                                        R.id.txtBuy_Price,
                                        R.id.txtSell_Price,
                                        R.id.txtStock,},
                                0);
                        listView01.setAdapter(adapter);
                    }
                }
            };

    private Button.OnClickListener btn_searchall_Click = new
            Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    UpdateAdapter();
                }
            };

    private Button.OnClickListener btn_searchnum_Click = new
            Button.OnClickListener(){
                @Override
                public void onClick(View view){
                    try {
                        int searchnum = Integer.parseInt(edtsearchnum.getText().toString());
                        Cursor cursor = db.query("table02", null, "provider_id=" + searchnum, null, null, null, null, null);
                        if (cursor != null && cursor.getCount() >= 0) {
                            SimpleCursorAdapter adapter = new
                                    SimpleCursorAdapter(getActivity(),
                                    R.layout.listdata_item,
                                    cursor,
                                    new String[]{"name", "provider_id", "buy_price", "sell_price", "stock"},
                                    new int[]{R.id.txtTitleName,
                                            R.id.txtProvider_id,
                                            R.id.txtBuy_Price,
                                            R.id.txtSell_Price,
                                            R.id.txtStock,},
                                    0);
                            listView01.setAdapter(adapter);
                        }
                    }catch (Exception e){
                        Toast.makeText(getActivity(),"請輸入廠商id",Toast.LENGTH_SHORT).show();
                    }
                }
            };
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
        Cursor cursor = db.rawQuery("SELECT * FROM table02",null);
        if(cursor != null && cursor.getCount()>=0){
            SimpleCursorAdapter adapter = new
                    SimpleCursorAdapter(getActivity(),
                    R.layout.listdata_item,
                    cursor,
                    new String[] {"name","provider_id","buy_price","sell_price","stock"},
                    new int[] { R.id.txtTitleName,
                                R.id.txtProvider_id,
                                R.id.txtBuy_Price,
                                R.id.txtSell_Price,
                                R.id.txtStock,},
                    0);
            listView01.setAdapter(adapter);
        }
    }

    public ListView.OnItemClickListener listviewlistener = new
            ListView.OnItemClickListener(){
                public void onItemClick(AdapterView<?> parent,View view,
                                        int position,long id){

                    //Toast.makeText(getActivity(),"short"+id,Toast.LENGTH_SHORT).show();
                    Intent it = new Intent();
                    it.setClass(getActivity(),Modify_ItemActivity.class);
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
                                    db.delete("table02","_id="+Id,null);
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