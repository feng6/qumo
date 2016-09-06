package com.example.lanouhn.qumo.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.adapters.WeimeiAdapter;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.constans.Weimeistl;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.WeimeiJson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 */
public class Weimei extends Activity  implements GoddesInterface {
    private XRecyclerView xrecyclerView;
    List<Weimeistl> list;
    private WeimeiAdapter adapter;
    ViewFlipper viewFlipper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish);
        xrecyclerView= (XRecyclerView) findViewById(R.id.xrecyclerview);
        //添加xrecyclerview头部
        final View head = LayoutInflater.from(Weimei.this).inflate(R.layout.godess_item_head,null);
        viewFlipper= (ViewFlipper) head.findViewById(R.id.viewFlipper);
        xrecyclerView.addHeaderView(head);

        viewFlipper.startFlipping();
        viewFlipper.setFlipInterval(500);
        viewFlipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



//设置layoutManager
        xrecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        initData();//解析数据
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                initData();
                handler.sendEmptyMessage(2);

            }
            @Override
            public void onLoadMore() {
                xrecyclerView.loadMoreComplete();//关闭上拉加载更多

            }
        });
    }


    //数据解析
    private void initData() {

        list = new ArrayList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.doGet(Url.Weimeiurl);
                if (null!=result&&result.length()>0){
                    list = WeimeiJson.getweimeiList(result);
                    if (list.size()>0) {
                        handler.sendEmptyMessage(1);
                    }else {
                        handler.sendEmptyMessage(0);
                    }
                }else{
                    handler.sendEmptyMessage(0);
                }

            }


        }).start();
    }

    //判断网络是否连接
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    adapter= new WeimeiAdapter(Weimei.this,list);
                    xrecyclerView.setAdapter(adapter);
                    //实现暴露的方法
                    adapter.godesspicId(Weimei.this);
                    break;
                case 0:
                    Toast.makeText(Weimei.this,R.string.fail,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    xrecyclerView.refreshComplete();//关闭下拉刷新
                    break;
            }
        }
    };
    //传值
    @Override
    public void godesspicClick(int i) {
        Intent intent = new Intent(Weimei.this, Weimeixiang.class);
        //-1要注意
        intent.putExtra("url", list.get(i-2).getLarge_image().getUrl());
        startActivity(intent);
    }

}
