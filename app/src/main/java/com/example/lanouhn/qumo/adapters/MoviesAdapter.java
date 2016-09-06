package com.example.lanouhn.qumo.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.constans.Goddes;
import com.example.lanouhn.qumo.constans.Movies;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.SPUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 * 情感视频适配器
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.viewholder> {

    private Context context;
    List<Movies> list;//实体类
    private GoddesInterface godesspicClick;//定义的接口
    //构造器
    public MoviesAdapter(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }
    //向外暴露的方法
    public  void  godesspicId(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }

    public MoviesAdapter(Context context, List<Movies> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.movies_item,null);
        viewholder vh = new viewholder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        //保存数据
      SPUtils.put(context,"laragepic",list.get(position).getLarge_cover().getUrl());
        //图片
        Picasso.with(context).load(list.get(position).getUser().getAvatar_url()).into(holder.avatar_url);
//主图
        Picasso.with(context).load(list.get(position).getLarge_cover().getUrl()).into(holder.iv);
        //点击暂停符号主图隐藏
        holder.press.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.press.setVisibility(View.GONE);
                holder.iv.setVisibility(View.GONE);
                holder.url_list_url.start();
            }
        });
       //找到控件位置用来在fragment中实现点击
//        //点击全屏体跳转全屏界面
//        holder.quanping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (godesspicClick != null) {
//                    holder.url_list_url.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            //获取点击的位置
//                            int pos = holder.getLayoutPosition();
//                            //通过接口传过去
//                            godesspicClick.godesspicClick(pos);
//                        }
//                    });
//                }
//            }
//        });

//文本

        holder.bury_count.setText(list.get(position).getBury_count());
        holder.digg_count.setText(list.get(position).getDigg_count());
        holder.name.setText(list.get(position).getUser().getName());
        holder.share_count.setText(list.get(position).getShare_count());
        holder.comment_count.setText(list.get(position).getComment_count());
        holder.content.setText(list.get(position).getContent());
//vodeo

        holder.url_list_url.setMediaController(new MediaController(context));
        holder.url_list_url.setVideoURI(Uri.parse(list.get(position).getUrl_mp4()));
//        holder.url_list_url.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.url_list_url.start();
//
//            }
//        });
        holder.url_list_url.pause();
        holder.url_list_url.requestFocus();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        private ImageView avatar_url;
        private TextView  name;
        private TextView  content;
        private TextView   digg_count;
        private TextView   bury_count;
        private TextView   comment_count;
        private TextView   share_count;
        private VideoView url_list_url;
        private ImageView  iv;//显示的图片
        private ImageView  press;//暂停符号
        public viewholder(View itemView) {
            super(itemView);

            avatar_url= (ImageView) itemView.findViewById(R.id.avatar_url);
            name= (TextView) itemView.findViewById(R.id.name);
            content= (TextView) itemView.findViewById(R.id.content);
            //主图
            iv= (ImageView) itemView.findViewById(R.id.iv);
            url_list_url= (VideoView) itemView.findViewById(R.id.url_list_url);
            digg_count= (TextView) itemView.findViewById(R.id.digg_count);
            bury_count= (TextView) itemView.findViewById(R.id.bury_count);
            comment_count= (TextView) itemView.findViewById(R.id.comment_count);
            share_count= (TextView) itemView.findViewById(R.id.share_count);

            press= (ImageView) itemView.findViewById(R.id.press);
        }
    }
}
