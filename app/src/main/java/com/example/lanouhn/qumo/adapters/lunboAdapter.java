package com.example.lanouhn.qumo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.constans.lunboImage;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/17.
 * 轮播图
 */
public class lunboAdapter extends BaseAdapter {
    private Context context;
    private List<lunboImage> lmagelist;


    public lunboAdapter(Context context, List<lunboImage> lmagelist) {
        this.context = context;
        this.lmagelist = lmagelist;
    }

    @Override
    public int getCount() {
        return lmagelist.size() ;
    }

    @Override
    public Object getItem(int i) {
        return lmagelist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.viewfilpper_item,null);
        ImageView vfimage;
        vfimage= (ImageView) view1.findViewById(R.id.vfimage);

        Picasso.with(context).load(String.valueOf(lmagelist.get(i))).into(vfimage);
        return view1;
    }

}