package com.example.lin.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lin.myapplication.data.LoadFile;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//实现 OnClickListener 接口方法
public class HomeFragment extends Fragment implements View.OnClickListener  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   /* private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    // TODO: Rename and change types of parameters
  /* private String mParam1;
    private String mParam2;*/

    private static final String ARG_PARAM = "param";
    private WordEntity mWord;
    private TextView mDanci;
    private TextView mCixing;
    private TextView mFanyi;
    private TextView mLiju;
    private ImageView mTupian;
    private CheckBox mCheckBox;


//声明一个List<WordEntity>的列表 mWordEntityList，获取单列WordLib 中的所有词汇
    // 获取词条在 WordLib 中的位置
    private List<WordEntity> mWordEntityList;
    private int mCurrentIndex=0;
//////
//实现 OnClickListener 接口方法
    private ImageButton mNextWord;
    private ImageButton mLastWord;
///////

//添加服务启动

     private Intent mIntentService;
/////////////////
//先定义两个常量用于表示更新到“上一页”和“下一页”这个两个动作
    public static final int LAST_WORD=0;
    public static final int NEXT_WORD=1;

    public HomeFragment() {
        // Required empty public constructor
    }

    /* /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(WordEntity param) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM, param);
        // args.putString(ARG_PARAM1, param1);
        //  args.putString(ARG_PARAM2, param2);
        //通过fragment.setArguments(args)保存在它自己身上
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //调用 setRetainInstance(true) 保留 fragment
        setRetainInstance(true);
        //////


        if (getArguments() != null) {
         /*   mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
            mWord = (WordEntity) getArguments().getSerializable(ARG_PARAM);
        } else {
            mWord = new WordEntity();
        }




 // 获取词条在 WordLib 中的位置
 //后通过循环 mWordEntityList 得到该词条在词汇表中的位置
        mWordEntityList=WordLib.get(getActivity()).getWordList();
        for (int i=0;i<mWordEntityList.size();i++){
            if (mWordEntityList.get(i).getId().equals(mWord.getId())){
                mCurrentIndex=i;
            }
        }
/////////// //////
    }
//清除 Message
    @Override
    public void onDestroy(){
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
///////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mDanci = (TextView) view.findViewById(R.id.danci);
        mCixing = (TextView) view.findViewById(R.id.cixing);
        mFanyi = (TextView) view.findViewById(R.id.fanyi);
        mLiju = (TextView) view.findViewById(R.id.liju);
        mTupian = (ImageView) view.findViewById(R.id.tupian);
        mCheckBox = (CheckBox) view.findViewById(R.id.checkBox);

////实现 OnClickListener 接口方法
        mLastWord=(ImageButton)view.findViewById(R.id.last_button);
        mNextWord=(ImageButton)view.findViewById(R.id.next_button);
        mLastWord.setOnClickListener(this);
        mNextWord.setOnClickListener(this);
//////////

// 为 TextView 设置监听器
        mDanci.setClickable(true);
        mLiju.setClickable(true);
        mDanci.setOnClickListener(this);
         mLiju.setOnClickListener(this);
/////////////////////////////////////
        //添加服务启动
        mIntentService=new Intent(getActivity(),TTSService.class);
//////////////////////
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isKilled) {
                mWord.setKilled(isKilled);
            }
        });
        updateWords();
        return view;
   }
// 创建 Handler 类型的类成员变量
        private Handler mHandler=new Handler(){
                public void handleMessage(Message msg){
                    switch (msg.what){
                        case LAST_WORD:
                            updateWords();
                            break;
                        case NEXT_WORD:
                            updateWords();
                            break;
                            default:break;
                    }
                }
};

/////////实现 OnClickListener 接口方法
             @Override
             public void onClick(View v){
                 switch (v.getId()){
                     case R.id.danci:
                         //启动语音
                         mIntentService.putExtra(TTSService.EXTRA_WORD,mWord.getEnglish());
                         /////////
                         getActivity().startService(mIntentService);
                         break;
                     case R.id.liju :
                         //启动语音
                         mIntentService.putExtra(TTSService.EXTRA_WORD,mWord.getExample());
                         //////////////////////////
                         getActivity().startService(mIntentService);
                         break;
//////////////////////////////////////////////////////////////////////
            case R.id.last_button:
    //创建新线程
                 new  Thread(new Runnable() {
                     @Override
                     public void run() {
                         //创建一个 message
                         // 设置 what 字段的值为 LAST_WORD, 主要是为了区分不同的 message
                         // 调用 Handler 的 message 对象
                         // handler 中的 handlermessage 对象是在主线程中运行的
                         if(mCurrentIndex>0){
                             mCurrentIndex--;
                             mWord=mWordEntityList.get(mCurrentIndex);
                         }
                         Message message=new Message();
                         message.what=LAST_WORD;
                         mHandler.sendMessage(message);
                     }
                 }).start();
                  break;

            case R.id.next_button:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //设置 what字段的值为 NEXT_WORD , 用于 区分不同的 message
                        if(mCurrentIndex<mWordEntityList.size()-1){
                            mCurrentIndex++;
                            mWord=mWordEntityList.get(mCurrentIndex);
                        }
                        Message message=new Message();
                        message.what=NEXT_WORD;
                        mHandler.sendMessage(message);
                    }
                }).start();
                break;
                default:break;
        }
             }
////////////////////////

    private void updateWords() {
     /*   mWord.setEnglish("dolphin");
        mWord.setPartOfpeech("noun.");
        mWord.setChinese("海豚");
        mWord.setImgID(R.drawable.haitun);
        mWord.setExample("A dolphin is a mammal which lives in the sea.");
        mWord.setKilled(false);*/
        mCheckBox.setChecked(mWord.isKilled());
        mDanci.setText(mWord.getEnglish());
        mCixing.setText(mWord.getPartOfSpeech());
        mFanyi.setText(mWord.getChinese());
        mLiju.setText(mWord.getExample());
      // 此改用 setImageBitmap(…)方法接收
     // mTupian.setImageResource(mWord.getImgID());
       mTupian.setImageBitmap(LoadFile.loadImages(getActivity(),mWord.getImgID()));
    }
    //第七讲   WordEntity实例在VocabularyFragment中更改，
    // 所以添加覆盖其onPause()，更新WordEntity 的 Wordlib 副本。
    @Override
    public void onPause(){
        super.onPause();
        WordLib.get(getActivity()).updateVocab(mWord);
    }

}
