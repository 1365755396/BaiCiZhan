package com.example.lin.myapplication.data;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lin.myapplication.WordEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public class LoadFile {
    private static final String VOCAB_JSON = "vocab_json/";
    //添加 JSON 解析代码
    public static List<WordEntity> getJsonList (Context context){
        String mJson;
        mJson = getJsonFile(context,VOCAB_JSON + "vocabulary.json");
        Gson gson=new Gson();
        Type type=new TypeToken<List<WordEntity>>(){}.getType();
        return gson.fromJson(mJson,type);
    }

    private static String getJsonFile(Context context, String fileName)  {
        StringBuilder stringBuilder=new StringBuilder();
        // 获得 assets资源管理器
        AssetManager assetManager=context.getAssets();
        // 使用 IO流读取 json文件内容 ， 指定字符集编码 utf - 8
        try{
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader
                    (assetManager.open(fileName),"utf-8"));
            String line;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
        }catch(IOException e){e.printStackTrace();}
        return stringBuilder.toString();
    }
    private static final String VOCAB_PNG="vocab_png";
    public static Bitmap loadImages(Context context,String image){
        String imageNames;
        InputStream open = null;
        Bitmap bitmap = null;
        try{imageNames=VOCAB_PNG+"/"+image+".png";
        open=context.getAssets().open(imageNames);
        bitmap=BitmapFactory.decodeStream(open);
        }catch(IOException e1){
            e1.printStackTrace();
        }
        finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return bitmap;
    }
}