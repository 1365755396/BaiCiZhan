package com.example.lin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.jar.Attributes;

public class
Main2Activity extends AppCompatActivity {
    private Button mButton3;
    //声明 SharedPreferences 对象

    private EditText mName;
    private EditText mShoujihaoma;
    private EditText mMima;
    private SharedPreferences mPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mButton3=findViewById(R.id.button3);
        mName=findViewById(R.id.name);
        mShoujihaoma=findViewById(R.id.shoujihaoma);
        mMima=findViewById(R.id.mima);
        //SharedPreferences
        mPreferences =getSharedPreferences("main2_data",Context.MODE_PRIVATE);

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.button3:
                        //当该ID为R.id.ok_btutton，即“确定”按钮时，首先获取一个SharedPreferences.Editor 对象 edit，见标记⑤。
                        SharedPreferences.Editor edit = mPreferences.edit();
                        edit.putString("name", mName.getText().toString().trim());
                        edit.putString("shoujihaoma", mShoujihaoma.getText().toString().trim());
                        edit.putString("mima", mMima.getText().toString().trim());
                        edit.commit();
                }
                Intent intent=  MainActivity.newIntent(Main2Activity.this,mName.getText().toString().trim());
                startActivity(intent);
            }

        });
}
//检查错误









}
