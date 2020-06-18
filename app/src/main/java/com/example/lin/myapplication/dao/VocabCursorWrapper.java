package com.example.lin.myapplication.dao;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.lin.myapplication.WordEntity;
import com.example.lin.myapplication.dao.VocabDbSchema.VocabTable;

import java.util.UUID;

//需要调用Integer，用Short不能装，内容过多

public class VocabCursorWrapper extends CursorWrapper {
    public VocabCursorWrapper(Cursor cursor){
        super(cursor);
    }
    public WordEntity getVocab(){
        String uuidString=getString(getColumnIndex(VocabTable.Cols.UUID));
        String vocab=getString(getColumnIndex(VocabTable.Cols.VOCAB));
        String pos = getString(getColumnIndex(VocabTable.Cols.POS));
        String meaning = getString(getColumnIndex(VocabTable.Cols.MEANING));
        String example = getString(getColumnIndex(VocabTable.Cols.EXAMPLE));
        String image = getString(getColumnIndex(VocabTable.Cols.IMAGEID));
        int isKilled = getInt(getColumnIndex(VocabTable.Cols.KILLED));


        //完成 getVocab()方法
        WordEntity wordEntity =new WordEntity(UUID.fromString(uuidString));
        wordEntity.setEnglish(vocab);
        wordEntity.setPartOfSpeech(pos);
        wordEntity.setChinese(meaning);
        wordEntity.setExample(example);
        wordEntity.setImgID(image);
        wordEntity.setKilled(isKilled != 0);

        return wordEntity;
    }
}
