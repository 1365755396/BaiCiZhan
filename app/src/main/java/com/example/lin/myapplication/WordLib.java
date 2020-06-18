package com.example.lin.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lin.myapplication.dao.VocabBaseHelper;
import com.example.lin.myapplication.dao.VocabCursorWrapper;
import com.example.lin.myapplication.dao.VocabDbSchema.VocabTable;
import com.example.lin.myapplication.data.LoadFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class WordLib {
    private static WordLib sWordLib;
     //private List<WordEntity> mWordList;//创建存储数量类型为 WordEntity对象的序列

//判读是否是初次进入“词汇”Tab
    private  final String SHARE_APP_TAG= "firstOpen";
    private Boolean first;
    private SharedPreferences setting;
    private static final String TAG = "WordLib";
/////////
//在 Wordlib 中利用 VocabBaseHelper 创建数据库。
    private Context mContext;
    private SQLiteDatabase mDatabase;
    //
   //将从 JSON 文件中获取的词汇数据插入数据库
    private List<WordEntity> mWordList;
    /////
    public static WordLib get(Context context){
        if (sWordLib==null){
            sWordLib=new WordLib(context);
        }
        return sWordLib;
    }
    private WordLib(Context context){
        //在 Wordlib 中利用 VocabBaseHelper 创建数据库
        mContext=context.getApplicationContext();

        setting=context.getSharedPreferences(SHARE_APP_TAG,0);
        first=setting.getBoolean("FLRST",true);
        Log.i(TAG,"第1次进入="+first);
        if(first){
            setting.edit().putBoolean("FIRST",false).commit();
        }
        mDatabase=new VocabBaseHelper(mContext).getWritableDatabase();
        //


        //将从 JSON 文件中获取的词汇数据插入数据库
        mWordList=new ArrayList<>();
        mWordList=LoadFile.getJsonList(mContext);
        ////////////

        if(first)
              addVocabList();
        // 删除 mWordList 相关代码
       // mWordList=new ArrayList<>();
      //  mWordList.addAll(sWords);

    }





    public List<WordEntity>  getWordList(){
        // 获取 WordEntity 词汇表
        // 删除 mWordList 相关代码  return  mWordList;


//，完善 getVocabs()方法：遍历查询取出所有的 WordEntity，返回 WordEntity 数组对象
        List<WordEntity> wordList=new ArrayList<>();
        VocabCursorWrapper cursorWrapper=queryVocabs(null,null);
        try{cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                wordList.add(cursorWrapper.getVocab());
                cursorWrapper.moveToNext();
            }
        }finally{
            cursorWrapper.close();
        }

        return wordList;
    }
////////////

    public WordEntity getWordEntity(UUID id){
        //通过 UUID 获取单个词汇
      /*  for (WordEntity wordEntity:mWordList){
            if (wordEntity.getId().equals(id)){
                return wordEntity;
            }
        }          删除 mWordList 相关代码*/

        //重写（取出已存在的 首条记录）
        VocabCursorWrapper cursorWrapper=queryVocabs(
                VocabTable.Cols.UUID+"=?",
                new String[] {id.toString()}
        );
        try {
            if (cursorWrapper.getCount() == 0) {
                return null;
            }
            cursorWrapper.moveToFirst();
            return cursorWrapper.getVocab();
        }finally {
            cursorWrapper.close();
        }
    }




    //多次创建 ContentValues 实例。为此，添加一个私有方法来，
    // 以便多 次在 ContentValues 中放入 WordEntity 变量
    //(在 ContentValues 类的协助下完成写入和更新数据库工作)
    private static ContentValues getContentValues(WordEntity wordEntity){
        ContentValues contentValues=new ContentValues();
        contentValues.put(VocabTable.Cols.UUID,wordEntity.getId().toString().trim());
        contentValues.put(VocabTable.Cols.VOCAB,wordEntity.getEnglish().trim());
        contentValues.put(VocabTable.Cols.POS,wordEntity.getPartOfSpeech().trim());
        contentValues.put(VocabTable.Cols.MEANING,wordEntity.getChinese().trim());
        contentValues.put(VocabTable.Cols.EXAMPLE,wordEntity.getExample().trim());
        contentValues.put(VocabTable.Cols.IMAGEID, wordEntity.getImgID());
        contentValues.put(VocabTable.Cols.KILLED,wordEntity.isKilled() ? 1: 0);
        return contentValues;
    }
    //，addVocabList()方法则向数据库插入一组数据
    public void addVocab(WordEntity wordEntity){
        ContentValues contentValues=getContentValues(wordEntity);
        mDatabase.insert(VocabTable.NAME,null,contentValues);
    }
    public  void addVocabList(){
        for(WordEntity wordEntity:mWordList /*sWords*/){
            addVocab(wordEntity);
        }
    }
    //利用 ContentValues 完成更新数据库行
    public void updateVocab(WordEntity wordEntity){
        String uuidString=wordEntity.getId().toString();
        ContentValues contentValues=getContentValues(wordEntity);
        mDatabase.update(VocabTable.NAME,contentValues,
                VocabTable.Cols.UUID + "=?",
                new String[] {uuidString});
    }
    //调用 query(...)方法查询 VocabTable 中的记录
   // private Cursor queryWords(String whereClause,String[] whereArgs){


    //让 queryVocabs (...)方法返回 VocabCursorWrapper 对象
    // 使用 cursor 封装
      private VocabCursorWrapper queryVocabs(String whereClause,String[] whereArgs){
        Cursor cursor=mDatabase.query(
                VocabTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
       // return cursor;
          return new VocabCursorWrapper(cursor);
    }









/*删除静态块中的词汇数据

    public static List<WordEntity> sWords=new ArrayList<>();
    static{
        for(int i=1;i<=20;i++){
            sWords.add(new WordEntity("beautiful","adj","美丽的","a beautiful woman",R.drawable.beautiful));
            sWords.add(new WordEntity("chat","vi.","谈话","What were you chatting about?",R.drawable.chat));
            sWords.add(new WordEntity("classic","adj.","经典的","a classic novel",R.drawable.classicbook));
            sWords.add(new WordEntity("dance","v.","跳舞","They stayed up all night singing and dancing.",R.drawable.dance));
            sWords.add(new WordEntity("fly","v.","飞行","The dog is learning to fly.",R.drawable.fly));
            sWords.add(new WordEntity("loudly","adv."," 大声地", "They were talking loudly.",R.drawable.loudly));
        }
    }*/
}
