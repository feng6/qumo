package com.example.lanouhn.qumo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lanouhn.qumo.R;

import com.example.lanouhn.qumo.activities.NofunnyClick;
import com.example.lanouhn.qumo.constans.Funny;
import com.example.lanouhn.qumo.constans.Zhibo;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 * 直播
 */
public class ZhiboAdapter extends RecyclerView.Adapter<ZhiboAdapter.ZhiboViewholder> {

    private Context context;
    List<Zhibo> list;//实体类
    private GoddesInterface godesspicClick;//接口
    //构造器

    public ZhiboAdapter(GoddesInterface goddesInterface) {
        this.godesspicClick = goddesInterface;
    }

    //向外暴露的方法
    public  void  godesspicId(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }



    public ZhiboAdapter(Context context, List<Zhibo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onViewRecycled(ZhiboViewholder holder) {
        super.onViewRecycled(holder);
        Log.e("TAG", "onViewRecycled: " + holder.isRecyclable() );
    }

    @Override
    public ZhiboViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        ZhiboViewholder vh = new ZhiboViewholder(LayoutInflater
                .from(context)
                .inflate(R.layout.zhibo_item, null));
        vh.setIsRecyclable(true);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ZhiboViewholder holder, int position) {
        //保存数据
//  SPUtils.put(context,"laragepic",list.get(position).getLarge_image().getUrl());
        //主图片
        Picasso.with(context).load(list.get(position).getPreview()).noPlaceholder().into(holder.image);

        //找到控件位置用来在fragment中实现点击
        if (godesspicClick != null) {
            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取点击的位置
                    int pos = holder.getLayoutPosition();
                    //通过接口传过去
                    godesspicClick.godesspicClick(pos);
                }
            });
        }
            //文本
        holder.name.setText(list.get(position).getUser().getName());
        holder.views.setText(list.get(position).getViewers());
        holder.tag.setText(list.get(position).getTag().getTag());



}


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ZhiboViewholder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView  name;
        private TextView  tag;
        private TextView  views;


        public ZhiboViewholder(View itemView) {
            super(itemView);

            name= (TextView) itemView.findViewById(R.id.name);
            image= (ImageView) itemView.findViewById(R.id.image);
            tag= (TextView) itemView.findViewById(R.id.tag);
            views= (TextView) itemView.findViewById(R.id.views);

        }
    }
}
