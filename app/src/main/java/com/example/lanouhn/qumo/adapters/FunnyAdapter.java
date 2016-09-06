package com.example.lanouhn.qumo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;

import com.example.lanouhn.qumo.R;

import com.example.lanouhn.qumo.activities.GifFunny;
import com.example.lanouhn.qumo.activities.NofunnyClick;
import com.example.lanouhn.qumo.constans.Funny;
import com.example.lanouhn.qumo.interfaces.FunnyInterface;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.interfaces.NofunnyInterface;
import com.example.lanouhn.qumo.utils.SPUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 * 好笑不
 */
public class FunnyAdapter extends RecyclerView.Adapter<FunnyAdapter.viewholder> {

    private Context context;
    List<Funny> list;//实体类
    private GoddesInterface godesspicClick;//定义的接口

    public FunnyAdapter(Context context, List<Funny> list) {
        this.context = context;
        this.list = list;
    }

    //构造器
    public FunnyAdapter( GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }

    //向外暴露的方法
    public  void  godesspicId(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }


    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.funny_item,null);
        viewholder vh = new viewholder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        //保存数据
//  SPUtils.put(context,"laragepic",list.get(position).getLarge_image().getUrl());
        //图片
        Picasso.with(context).load(list.get(position).getUser().getAvatar_url()).into(holder.avatar_url);

        if (list.get(position).getLarge_image()!=null) {
            Picasso.with(context).load(list.get(position).getLarge_image().getList_url().get(0).getUrl()).into(holder.url_list_url);

        }//找到控件位置用来在fragment中实现点击
        if (godesspicClick != null) {
            holder.url_list_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取点击的位置
                    int pos = holder.getLayoutPosition();
                    //通过接口传过去
                    godesspicClick.godesspicClick(pos);
                }
            });

        //好笑
        holder.tv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Intent intent = new Intent(context, GifFunny.class);
                context.startActivity(intent);
                }
            });
            //不好笑
            holder.tv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, NofunnyClick.class);
                    context.startActivity(intent);
                }
            });
//文本
            holder.name.setText(list.get(position).getUser().getName());
            holder.content.setText(list.get(position).getContent());



}
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        private ImageView avatar_url;
        private TextView  name;
        private TextView  content;
        private ImageView url_list_url;

        private ImageView tv1;//好笑
        private ImageView tv2;//不好笑

        public viewholder(View itemView) {
            super(itemView);

            avatar_url= (ImageView) itemView.findViewById(R.id.avatar_url);
            name= (TextView) itemView.findViewById(R.id.name);
            content= (TextView) itemView.findViewById(R.id.content);
            //主图
            url_list_url= (ImageView) itemView.findViewById(R.id.url_list_url);

            tv1= (ImageView) itemView.findViewById(R.id.tv1);
            tv2= (ImageView) itemView.findViewById(R.id.tv2);

        }
    }
}
