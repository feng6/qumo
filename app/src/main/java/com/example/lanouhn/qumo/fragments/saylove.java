package com.example.lanouhn.qumo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.activities.Laragepic;
import com.example.lanouhn.qumo.adapters.GoddesAdapter;
import com.example.lanouhn.qumo.adapters.SayloveAdapter;
import com.example.lanouhn.qumo.constans.Saylove;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.GoddesJson;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.SayloveJson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 * 表白N+1
 */
public class saylove extends Fragment implements GoddesInterface {
    private XRecyclerView xrecyclerView;
    List<Saylove> list;
    private SayloveAdapter adapter;
    private ProgressBar pb;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saylove,container,false);
        xrecyclerView= (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        pb= (ProgressBar) view.findViewById(R.id.pb);


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

                String result = HttpUtils.doGet(Url.Sayloveurl);

                if (null!=result&&result.length()>0){
                    list = SayloveJson.getsayloveList(result);
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
                    adapter= new SayloveAdapter(getActivity(),list);
                    xrecyclerView.setAdapter(adapter);
                    pb.setVisibility(View.GONE);
                    //实现暴露的方法
                    adapter.godesspicId(saylove.this);
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
        if (list.get(i-1).getLarge_image()!=null)
            intent.putExtra("url", list.get(i-1).getLarge_image().getList_url().get(0).getUrl());
            startActivity(intent);
        }
}
