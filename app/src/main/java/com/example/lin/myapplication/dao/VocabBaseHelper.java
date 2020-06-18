package com.example.lin.myapplication.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lin.myapplication.dao.VocabDbSchema.VocabTable;


public class VocabBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION=1;
    private static final String DATABASE_NAME="VocabBase.db";

    public VocabBaseHelper(Context context){
        super(context,DATABASE_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
 //创建数据库表（引用 VocabDbSchema 的内部类 VocabTable)
        db.execSQL("create TABLE "+VocabTable.NAME+"("+
                //
 //引用 VocabDbSchema.VocabTable 中的字符串常量，
        "_id integer primary key autoincrement,"+
                VocabTable.Cols.UUID + ", " +
                VocabTable.Cols.VOCAB + ", " +
                VocabTable.Cols.POS + ", " +
                VocabTable.Cols.MEANING + ", " +
                VocabTable.Cols.EXAMPLE + ", " +
                VocabTable.Cols.IMAGEID + ", " +
                VocabTable.Cols.KILLED + ")"         );
 //

    }
    @Override
    public void onUpgrade(SQLiteDatabase ad,int oldVerdion,int newVwesion){

    }
}
