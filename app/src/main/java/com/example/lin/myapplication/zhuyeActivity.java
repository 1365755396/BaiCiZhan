package com.example.lin.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class zhuyeActivity extends AppCompatActivity
        implements VocabularyItemFragment.OnListFragmentInteractionListener{
    //新增一个常量 index 和一个变量 mCurrentIndex 作为将 要存储在 bundle 中的键值对的键和
    private  static final  String TAG="ZhuyeActivity";
    private static final String KEY_INDEX="index";
    private int mCurrentindex=0;
    //
  // 由于删除了组件  private TextView mTextMessage;
//首先定义了一个 BottomNavigationView.OnNavigationItemSelectedListener 类型
// 的成员变量 mOnNavigationItemSelectedListener。
// 该变量通过覆盖 onNavigationItemSelected 方法，
// 在 OnnavigationItemSelectedListener 内实现页面的切换
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            //让 mCurrentIndex“记住”item.getItemId()所指示的值
            mCurrentindex=item.getItemId();
            //
            switch (item.getItemId()) {
                case R.id.navigation_home:
                  //由于删除了组件  mTextMessage.setText(R.string.title_home);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new ZhuyeFragment())
                            .commit();
                    return true;
                case R.id.navigation_voclib:
                  //由于删除了组件  mTextMessage.setText(R.string.title_voclib);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,VocabularyItemFragment.newInstance(1))
                            .commit();
                    return true;
                case R.id.navigation_discover:
                //由于删除了组件    mTextMessage.setText(R.string.title_discover);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new FaxianFragment())
                            .commit();
                    return true;
                case R.id.mine:
                 //由于删除了组件   mTextMessage.setText(R.string.title_mine);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container,new WodeFragment())
                            .commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuye);

  //由于删除了组件    mTextMessage = (TextView) findViewById(R.id.message);
        //设置了默认显示内容  要实现转屏保存数据所以删除：mTextMessage.setText(R.string.title_home);
        //根据 mCurrentIndex 的值来确定显示内容
        mCurrentindex = R.id.navigation_home;
        if (savedInstanceState == null){
             //    mCurrentindex = savedInstanceState.getInt(KEY_INDEX, 0);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new ZhuyeFragment())
                    .commit();
        }



       /* switch (mCurrentindex) {
            case R.id.navigation_home:
                //由于删除了组件             mTextMessage.setText(R.string.title_home);

                break;
                case R.id.navigation_voclib:
                    //由于删除了组件      mTextMessage.setText(R.string.title_voclib);
                    break;
                    case R.id.navigation_discover:
                        //由于删除了组件         mTextMessage.setText(R.string.title_discover);
                        break;
                        case R.id.mine:
                            //由于删除了组件             mTextMessage.setText(R.string.title_mine);
                            break;
        }*/
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //给 BottomNavigationView的对象navigation
        // 设置一个OnNavigationItemSelectedListener用来响应切 换的动作
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
@Override
    public void onListFragmentInteraction(WordEntity item) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,HomeFragment.newInstance(item))
                .addToBackStack(null)
                .commit();
    }


    //新增的常量值作为键，将 mCurrentIndex 变量值保存到 bundle 中
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX,mCurrentindex);
    }

    //
}
