package com.example.lanouhn.qumo.activities;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.fragments.Qushi;
import com.example.lanouhn.qumo.fragments.ZhiboFragment;


import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends FragmentActivity {


    private RadioGroup rg;
    private FrameLayout  framlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initview();
        initLinister();
    }
    //初始化数据
    private void initview() {
        setContentView(R.layout.activity_main);
        rg= (RadioGroup) findViewById(R.id.rg);
        framlayout= (FrameLayout) findViewById(R.id.framlayout);
        //设置默认的布局文件
        FragmentManager me = getSupportFragmentManager();
        FragmentTransaction tran = me.beginTransaction();
        tran.replace(R.id.framlayout, new Qushi()).commit();
    }
//点击事件
    private void initLinister() {
            //radiogroup的点击事件
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    switch (i) {
                        case R.id.rb1:

                            FragmentManager me = getSupportFragmentManager();
                            FragmentTransaction tran = me.beginTransaction();
                            tran.replace(R.id.framlayout, new Qushi()).commit();

                            break;
                        case R.id.rb2:
                            FragmentManager me1 = getSupportFragmentManager();
                            FragmentTransaction tran1 = me1.beginTransaction();
                            tran1.replace(R.id.framlayout, new ZhiboFragment()).commit();

                            break;
                        case R.id.rb3:
                            Intent it = new Intent(MainActivity.this, Weimei.class);
                            startActivity(it);
                            break;
                        case R.id.rb4:
                            Intent it1 = new Intent(MainActivity.this, Message.class);
                            startActivity(it1);
                            break;
                    }
                }
            });
        }


    /**
     * 双击退出应用
     * @param keyCode
     * @param event
     * @return
     */
        public boolean onKeyDown(int keyCode, KeyEvent event) {

            exitB2Click();
            return false;
        }

        private static boolean isExit = false;

        private void exitB2Click() {

            Timer tExit = null;
            if (isExit == false) {
                isExit = true;

              Toast.makeText(MainActivity.this, "再按一次退出！", Toast.LENGTH_SHORT).show();
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {

                        isExit = false;
                    }
                }, 2000);


            } else {

                finish();

            }

        }

    }
