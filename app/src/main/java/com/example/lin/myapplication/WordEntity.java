package com.example.lin.myapplication;

import java.io.Serializable;
import java.util.UUID;
//，为了便于传递对象，这里实现了一个 Serializable 序列化的接口
public class WordEntity  implements Serializable {
    //6 个成员变量和一个构造函数
    private UUID  mId;
    boolean mKilled;
    private String  mEnglish;
    private String  mPartOfSpeech;
    private String  mChinese;
    private String mExample;
    //我们暂时将图片保存在 res/drawable 中。资源 ID 总是 int 类型
    private String mImgID;
public WordEntity(){
    // 创建唯一标识符
   // mId=UUID.randomUUID();

    ////////
    //返回具有 UUID 的 WordEntity
    this(UUID.randomUUID());
    }
    public WordEntity(UUID uuid){
    mId=uuid;
    }
    ////////
public WordEntity(String ve,String vp,String vc, String eg,String imgld) {
    // 创建唯一标识符
    mId = UUID.randomUUID();
    mEnglish = ve;
    mPartOfSpeech = vp;
    mChinese = vc;
    mExample = eg;
    mImgID = imgld;
}
    //选择 Generate... → Getter 菜单项，然后选择 mId 变量。
// 再选择 Generate… → Getter and Setter，选中其余所有的变量
    public UUID getId() {
        return mId;
    }

    public boolean isKilled() {
      return mKilled;
    }

    public void setKilled(boolean killed) {
        mKilled = killed;
    }

    public String getEnglish() {
        return mEnglish;
    }

    public void setEnglish(String english) {
        mEnglish = english;
    }

    public String getPartOfSpeech() {
        return mPartOfSpeech;
    }

    public void setPartOfSpeech(String PartOfSpeech) {
        mPartOfSpeech = PartOfSpeech;
    }

    public String getChinese() {
        return mChinese;
    }

    public void setChinese(String chinese) {
        mChinese = chinese;
    }

    public String getExample() {
        return mExample;
    }

    public void setExample(String example) {
        mExample = example;
    }

    public String getImgID() {
        return mImgID;
    }

    public void setImgID(String imgID) {
        mImgID = imgID;
    }
}

