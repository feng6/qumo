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
import com.example.lanouhn.qumo.activities.Laragepic;
import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.SPUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 */
public class GoddesAdapter extends RecyclerView.Adapter<GoddesAdapter.viewholder> {

    private Context context;
    List<Goddes> list;//实体类
    private GoddesInterface godesspicClick;//定义的接口
    //构造器
    public GoddesAdapter(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }
    //向外暴露的方法
    public  void  godesspicId(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }

    public GoddesAdapter(Context context, List<Goddes> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.godess_item,null);
        viewholder vh = new viewholder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        //保存数据
  SPUtils.put(context,"laragepic",list.get(position).getLarge_image().getUrl());
        //图片
        Picasso.with(context).load(list.get(position).getUser().getAvatar_url()).into(holder.avatar_url);
        Picasso.with(context).load(list.get(position).getLarge_image().getUrl()).into(holder.url_list_url);
       //找到控件位置用来在fragment中实现点击
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
        }
//文本

        holder.bury_count.setText(list.get(position).getBury_count());
        holder.digg_count.setText(list.get(position).getDigg_count());
        holder.name.setText(list.get(position).getUser().getName());
        holder.share_count.setText(list.get(position).getShare_count());
        holder.comment_count.setText(list.get(position).getComment_count());
        holder.content.setText(list.get(position).getContent());


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
        private TextView   digg_count;
        private TextView   bury_count;
        private TextView   comment_count;
        private TextView   share_count;

        public viewholder(View itemView) {
            super(itemView);

            avatar_url= (ImageView) itemView.findViewById(R.id.avatar_url);
            name= (TextView) itemView.findViewById(R.id.name);
            content= (TextView) itemView.findViewById(R.id.content);
            //主图
            url_list_url= (ImageView) itemView.findViewById(R.id.url_list_url);
            digg_count= (TextView) itemView.findViewById(R.id.digg_count);
            bury_count= (TextView) itemView.findViewById(R.id.bury_count);
            comment_count= (TextView) itemView.findViewById(R.id.comment_count);
            share_count= (TextView) itemView.findViewById(R.id.share_count);

        }
    }
}
