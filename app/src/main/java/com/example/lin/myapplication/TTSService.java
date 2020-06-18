package com.example.lin.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

// 实现 TextToSpeech.OnInitListener 接口
public class TTSService extends Service implements TextToSpeech.OnInitListener {
    public TTSService() {
    }
//////添加定时器    //
    private Handler handler;
    ///////////////
    // 实现 TextToSpeech.OnInitListener 接口
    private TextToSpeech mTs;
    /////////////////////
    //设置语言参数，
    private boolean isInit;
    ///////////////
    //开始说话‘’
    public static final String EXTRA_WORD="word";
    private String word;
    /////////////
    //生命周期
    @Override
    public void onCreate() {
        super.onCreate();
        // 实现 TextToSpeech.OnInitListener 接口
        mTs=new TextToSpeech(getApplicationContext(),this);
        /////////////
        //添加定时器
        handler=new Handler();
        //////////////////
        Log.d("TTSService", "执行 onCreate");
    }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
        //添加定时器
            handler.removeCallbacksAndMessages(null);
         //开始说话
            word =intent.getStringExtra(EXTRA_WORD);
            if (isInit){
                speak();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                stopSelf();
                }
            },15*1000);
            //////////////////////////
        Log.d("TTSService", "执行 onStartCommand");
        //return super.onStartCommand(intent, flags, startId);
            return TTSService.START_NOT_STICKY;
    }
    // 实现 TextToSpeech.OnInitListener 接口
    @Override
    public void onInit(int status){
        if (status==TextToSpeech.SUCCESS){
//并判断是否支持该语言            //
            int result=mTs.setLanguage(Locale.UK);
            mTs.setPitch(0.2f);
            mTs.setSpeechRate(0.7f);
            if (result!=TextToSpeech.LANG_MISSING_DATA&&
                    result!=TextToSpeech.LANG_NOT_SUPPORTED){
                isInit=true;
            }
        }

    }
    private void speak(){
        if (mTs!=null){
            mTs.speak(word,TextToSpeech.QUEUE_FLUSH,null,null);
        }
    }
    ////////////////////////////////////
        @Override
        public void onDestroy() {
        //回收 TTS
        if (mTs!=null){
            mTs.stop();
            mTs.shutdown();
        }
        ///////////////
        super.onDestroy();
        Log.d("TTSService", "执行 onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
}
