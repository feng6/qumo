package com.example.lanouhn.qumo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.activities.Laragepic;
import com.example.lanouhn.qumo.adapters.GoddesAdapter;
import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.GoddesJson;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 * 女神列表
 */
public class goddess extends Fragment implements GoddesInterface {
    private XRecyclerView xrecyclerView;
    List<Goddes> list;
    private GoddesAdapter adapter;
    private ProgressBar pb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goodes,container,false);
        xrecyclerView= (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        pb= (ProgressBar) view.findViewById(R.id.pb);
//添加recyclerview头部
//        View head =LayoutInflater.from(getContext()).inflate(R.layout.godess_item_head,null);
//        xrecyclerView.addHeaderView(head);

        xrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xrecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                            initData();
                            handler.sendEmptyMessage(2);

            }
            @Override
            public void onLoadMore() {
                            initData();
                            handler.sendEmptyMessage(3);

            }
        });
        return  view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }


    //数据解析
    private void initData() {

        list = new ArrayList();
        new Thread(new Runnable() {
            @Override
            public void run() {

                String result = HttpUtils.doGet(Url.Goddessurl);

                if (null!=result&&result.length()>0){
                    list = GoddesJson.getgodessList(result);
                    handler.sendEmptyMessage(1);
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
                    adapter= new GoddesAdapter(getActivity(),list);
                    xrecyclerView.setAdapter(adapter);
                    pb.setVisibility(View.GONE);
                    //实现暴露的方法
                    adapter.godesspicId(goddess.this);
                    break;
                case 0:
                    Toast.makeText(getActivity(),R.string.fail,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                   adapter.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);
                    xrecyclerView.refreshComplete();//关闭下拉刷新
            break;
                case 3:
                    adapter.notifyDataSetChanged();
                    pb.setVisibility(View.GONE);
                    xrecyclerView.loadMoreComplete();//关闭上拉加载更多
                    break;
        }
        }
    };
//传值
    @Override
    public void godesspicClick(int i) {
            Intent intent = new Intent(getActivity(), Laragepic.class);
        //-1要注意
            intent.putExtra("url", list.get(i-1).getLarge_image().getUrl());
            startActivity(intent);
        }
}
