package com.example.lanouhn.qumo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.lanouhn.qumo.R;
import com.example.lanouhn.qumo.constans.Weimeistl;
import com.example.lanouhn.qumo.interfaces.GoddesInterface;
import com.example.lanouhn.qumo.utils.SPUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lanouhn on 2016/8/18.
 */
public class WeimeiAdapter extends RecyclerView.Adapter<WeimeiAdapter.viewholder> {

    private Context context;
    List<Weimeistl> list;//实体类
    private GoddesInterface godesspicClick;//定义的接口
    //构造器
    public WeimeiAdapter(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }
    //向外暴露的方法
    public  void  godesspicId(GoddesInterface godesspicClick) {
        this.godesspicClick = godesspicClick;
    }

    public WeimeiAdapter(Context context, List<Weimeistl> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.weimei_item,null);
        viewholder vh = new viewholder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        //图片
        Picasso.with(context).load(list.get(position).getLarge_image().getUrl()).into(holder.weimei_image);
        //找到控件位置用来在fragment中实现点击
        if (godesspicClick != null) {
            holder.weimei_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //获取点击的位置
                    int pos = holder.getLayoutPosition();
                    //通过接口传过去
                    godesspicClick.godesspicClick(pos);
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    class viewholder extends RecyclerView.ViewHolder{
        private ImageView weimei_image;
        public viewholder(View itemView) {
            super(itemView);
            //主图
            weimei_image= (ImageView) itemView.findViewById(R.id.weimei_image);
        }
    }
}
