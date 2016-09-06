package com.example.lanouhn.qumo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.constans.Weimeistl;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.WeimeiJson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/25.
 */
public class Weimeixiang extends Activity {

    private ImageView avatar_url;
    private TextView name;
    private TextView  content;
    private ImageView url_list_url;
    private TextView   digg_count;
    private TextView   bury_count;
    private TextView   comment_count;
    private TextView   share_count;



//    private XRecyclerView xrecyclerView;
    List<Weimeistl> wlist;
  //  private WeimeixiangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weimeixiang_item);
        initview();//绑定数据
        initData();
        initLinshener();
//        xrecyclerView= (XRecyclerView) findViewById(R.id.xrecyclerview);
////设置layoutManager
//        xrecyclerView.setLayoutManager(
//                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
//        initData();//解析数据
//        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//                handler.sendEmptyMessage(2);
//
//            }
//            @Override
//            public void onLoadMore() {
//                initData();
//                handler.sendEmptyMessage(3);
//
//            }
//        });
    }
//主图跳转
    private void initLinshener() {
        //主图跳转事件
        url_list_url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String url=intent.getStringExtra("url");
                Intent intent1 = new Intent(Weimeixiang.this, Laragepic.class);
                //-1要注意
                intent1.putExtra("url", url);
                startActivity(intent1);
            }
        });


    }

    //绑定数据
    private void initview() {
        avatar_url= (ImageView) findViewById(R.id.avatar_url);
        name= (TextView) findViewById(R.id.name);
        content= (TextView) findViewById(R.id.content);
        //主图
        url_list_url= (ImageView) findViewById(R.id.url_list_url);
        digg_count= (TextView)findViewById(R.id.digg_count);
        bury_count= (TextView)findViewById(R.id.bury_count);
        comment_count= (TextView)findViewById(R.id.comment_count);
        share_count= (TextView)findViewById(R.id.share_count);
    }

    //数据解析
    private void initData() {

        wlist = new ArrayList();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.doGet(Url.Weimeiurl);
                if (null!=result&&result.length()>0){
                    wlist = WeimeiJson.getweimeiList(result);
                    if (wlist.size()>0) {
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

                    for (int i = 0; i <wlist.size() ; i++) {
                        //图片
                        Intent intent = getIntent();
                        String url=intent.getStringExtra("url");
                        Picasso.with(Weimeixiang.this).load(url).into(url_list_url);
                        Picasso.with(Weimeixiang.this).load(wlist.get(i).getUser().getAvatar_url()).into(avatar_url);
                        //文本
                        bury_count.setText(wlist.get(i).getBury_count());
                        digg_count.setText(wlist.get(i).getDigg_count());
                        name.setText(wlist.get(i).getUser().getName());
                        share_count.setText(wlist.get(i).getShare_count());
                        comment_count.setText(wlist.get(i).getComment_count());
                        content.setText(wlist.get(i).getContent());

                    }

//                    adapter= new WeimeixiangAdapter(Weimeixiang.this,wlist);
//                    xrecyclerView.setAdapter(adapter);
                    //实现暴露的方法
                 //   adapter.godesspicId(Weimeixiang.this);
                    break;
                case 0:
                    Toast.makeText(Weimeixiang.this,R.string.fail,Toast.LENGTH_SHORT).show();
                    break;
//                case 2:
//                    adapter.notifyDataSetChanged();
//                    xrecyclerView.refreshComplete();//关闭下拉刷新
//                    break;
//                case 3:
//                    adapter.notifyDataSetChanged();
//                    xrecyclerView.loadMoreComplete();//关闭上拉加载更多
//                    break;
            }
        }
    };

    }
