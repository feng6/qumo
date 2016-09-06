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
import android.widget.Toast;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.activities.Laragepic;
import com.example.lanouhn.qumo.activities.MoviesVideoPlayer;
import com.example.lanouhn.qumo.adapters.GoddesAdapter;
import com.example.lanouhn.qumo.adapters.MoviesAdapter;
import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.constans.Movies;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.GoddesJson;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.MoviesJson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 * 情感电影
 */
public class movies extends Fragment implements GoddesInterface {

    private XRecyclerView xRecyclerView;

    List<Movies> list;
    private MoviesAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.moves,container,false);
        xRecyclerView= (XRecyclerView) view.findViewById(R.id.xrecyclerview);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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

                String result = HttpUtils.doGet(Url.Moviesurl);

                if (null!=result&&result.length()>0){
                    list = MoviesJson.getmoviesList(result);
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
                    adapter= new MoviesAdapter(getActivity(),list);
                    xRecyclerView.setAdapter(adapter);
                    //实现暴露的方法
                    adapter.godesspicId(movies.this);
                    break;
                case 0:
                    Toast.makeText(getActivity(),R.string.fail,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    adapter.notifyDataSetChanged();
                    xRecyclerView.refreshComplete();//关闭下拉刷新
                    break;
                case 3:
                    adapter.notifyDataSetChanged();
                    xRecyclerView.loadMoreComplete();//关闭上拉加载更多
                    break;
            }
        }
    };
    //传值
    @Override
    public void godesspicClick(int i) {
        Intent intent = new Intent(getActivity(), MoviesVideoPlayer.class);
        //-1要注意
        intent.putExtra("url", list.get(i-1).getUrl_mp4());
        startActivity(intent);
    }


}
