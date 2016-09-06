package com.example.lanouhn.qumo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.activities.zhiboVideoPlayer;
import com.example.lanouhn.qumo.adapters.ZhiboAdapter;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.constans.Zhibo;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.ZhiboJson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 */
public class ZhiboFragment extends Fragment implements GoddesInterface{
    private XRecyclerView xrecyclerview;
    private List<Zhibo> list;
    private ZhiboAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zhibo,container,false);
        xrecyclerview= (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        //设置layoutManager
        xrecyclerview.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        xrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                list.clear();
                initData();
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onLoadMore() {
                xrecyclerview.loadMoreComplete();
            }
        });

        return view;
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

                String result = HttpUtils.doGet(Url.Zhibourl);
                list=new ArrayList<>();
                if (null != result && result.length() > 0) {
                    list=ZhiboJson.getzhiboList(result);
                    if (list.size()>0) {
                        handler.sendEmptyMessage(1);
                    }else {
                        handler.sendEmptyMessage(0);
                    }

                } else {
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
                    adapter = new ZhiboAdapter(getActivity(), list);
                    xrecyclerview.setAdapter(adapter);
                    //实现暴露的方法
                    adapter.godesspicId(ZhiboFragment.this);
                    break;
                case 0:
                    Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    xrecyclerview.refreshComplete();//关闭下拉刷新
                    break;
            }
        }
    };
//接口对接，实现界面跳转
    @Override
    public void godesspicClick(int i) {
        Intent intent = new Intent(getActivity(), zhiboVideoPlayer.class);
        //-1要注意
        int id = list.get(i-1).getChannel().getId();
        intent.putExtra("id", id );
        startActivity(intent);
    }
}
