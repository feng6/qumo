package com.example.lanouhn.qumo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.activities.GifFunny;
import com.example.lanouhn.qumo.activities.Jiade;
import com.example.lanouhn.qumo.activities.Login;
import com.example.lanouhn.qumo.adapters.vpAdapter;
import com.example.lanouhn.qumo.constans.lunboImage;
import com.example.lanouhn.qumo.constans.Url;
import com.example.lanouhn.qumo.utils.HttpUtils;
import com.example.lanouhn.qumo.utils.LunboJson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 */
public class Qushi extends Fragment {


    private vpAdapter vpadapter;
    private ViewPager vp ;
    private TabLayout tab;
    private ViewFlipper viewfilpper;
    private ImageView image;
    private ImageView login1;//空心
    private ViewFlipper vf;
    private List<String> tabNames;
    private List<Fragment> fragments;
    private List<lunboImage> lmagelist;
    private ImageView vfimage;
    private  List<lunboImage> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.qushi,container,false);
        image= (ImageView) view.findViewById(R.id.login);
        login1= (ImageView) view.findViewById(R.id.login1);

        vp= (ViewPager) view.findViewById(R.id.vp);
        tab= (TabLayout) view.findViewById(R.id.tab);
        vf= (ViewFlipper) view.findViewById(R.id.vf);

        vf.startFlipping();//开启自动播放

        viewfilpper= (ViewFlipper) view.findViewById(R.id.viewfilpper);
        viewfilpper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //   Intent it = new Intent(getActivity(), Jiade.class);
                Intent it = new Intent(getActivity(), GifFunny.class);
                startActivity(it);
            }
        });



        initData();//解析数据
        initLinshener();//点击事件
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inData();//数据

        setDataToView();
    }



    //点击事件
    //跳转登陆界面
    private void initLinshener() {
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                Toast.makeText(getActivity(), getResources().getText(R.string.hehe), Toast.LENGTH_SHORT).show();
            }
        });
        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getResources().getText(R.string.haha), Toast.LENGTH_SHORT).show();

            }
        });
    }


    //tablayout和viewpager数据
    private void inData() {
        //tab标题
        tabNames = new ArrayList();
        //Fragment集合
        fragments = new ArrayList();
        //tablayout
        tabNames.add(getResources().getString(R.string.doum));
        tabNames.add(getResources().getString(R.string.godess));
        tabNames.add(getResources().getString(R.string.conntation));
        tabNames.add(getResources().getString(R.string.funny));
        tabNames.add(getResources().getString(R.string.movietrailer));

        // fragment列表
        fragments.add(new saylove());
        fragments.add(new goddess());
        fragments.add(new conntaition());
        fragments.add(new funny());
        fragments.add(new movies());

    }
    //viewpager绑定数据
    private void setDataToView() {
        vpadapter = new vpAdapter(getFragmentManager(),tabNames,fragments);
        vp.setAdapter(vpadapter);


        //tabLayout绑定viewpager数据
        tab.setupWithViewPager(vp);


        //预加载fragment里面内容
      //  vp.setOffscreenPageLimit(5);
    }
    //绑定数据
    private void initData() {

        list = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = HttpUtils.doPost(Url.Lunbourl, Url.Lunboparam);
                list = LunboJson.getlunboList(result);
                if (list.size() > 0) {

                    handler.sendEmptyMessage(1);

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
                    vfimage=null;
                    for (int i = 0; i <list.size() ; i++) {
                        ImageView iv=new ImageView(getActivity());
                        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        viewfilpper.addView(iv);

                        Picasso.with(getContext()).load(list.get(i).getUrl()).into(iv);
                    }
                    viewfilpper.startFlipping();
                    viewfilpper.setFlipInterval(1000);//开启轮播图自动播放 每次2秒
                    break;
                case 0:
                    Toast.makeText(getActivity(),getResources().getString(R.string.fail),Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
}