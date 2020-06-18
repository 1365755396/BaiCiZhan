package com.example.lin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    //??????
    public static final String NAME=
            "com.example.lin.myapplication";
    public static Intent newIntent(Context packageContext, String name){
        Intent intent = new Intent(packageContext, MainActivity.class);
        intent.putExtra(NAME,name );
        return intent;
    }
    //声明Button
    private Button mButton1;
    private Button mButton2;
    private EditText  mName;
    private EditText  mMima;
    //新增一个 TGA 常量
    private static final String TAG = "MainActivity";
    //声明 SharedPreferences 对象
    private SharedPreferences mPreferences;
    //给图片添加一个按钮
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"调用 onCreate(Bundle) 方法");
        setContentView(R.layout.activity_main);
        mButton1= findViewById(R.id.button1);
        mButton2= findViewById(R.id.button2);
        mName= findViewById(R.id.name);
        mMima= findViewById(R.id.mima);
        //preferences
        mPreferences =getSharedPreferences("main2_data",Context.MODE_PRIVATE);
        mName.setText(getIntent().getStringExtra(NAME));
        //按键2（登录）的内容
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //?????????
                if ((mName.getText().toString().trim().equals(
                        mPreferences.getString("name", "")))
                        && (mMima.getText().toString().trim().equals(
                        mPreferences.getString("mima", "")))) {
                    //当密码正确时启动主页窗口（启动zhuye Activity）
                    Intent intent=new Intent(MainActivity.this,zhuyeActivity.class);
                    startActivity(intent);
                } else {        Toast.makeText(MainActivity.this,
                        "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //按键1（注册）的内容
        mButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                // 启动 Register Activity
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);             }

    });
        ///给图片添加一个按钮

        mImageView=findViewById(R.id.imageView);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,zhuyeActivity.class);
                startActivity(intent);
            }
        });

}}



