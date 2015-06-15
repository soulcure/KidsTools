package com.kidsmind.cartoon.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.kidsmind.cartoon.R;
import com.kidsmind.cartoon.entity.SerieItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


public class FreePlayAdapter extends BaseAdapter {

    private Context mContext;
    private List<SerieItem> mLists;
    private DisplayImageOptions options;   //图片下载配置


    public FreePlayAdapter(Context context, List<SerieItem> list) {
        mContext = context;
        mLists = list;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)  // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)    // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)       // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    public List<SerieItem> getLists() {
        return mLists;
    }

    public void setList(List<SerieItem> lists) {
        mLists.addAll(lists);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public Object getItem(int position) {
        return mLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final SerieItem item = (SerieItem) getItem(position);
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.free_play_item, null);
            holder = new ViewHolder();

            holder.imgFrame = (ImageView) convertView.findViewById(R.id.img_frame); // 图片
            holder.tvCollect = (TextView) convertView.findViewById(R.id.tv_collect);//描述

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageLoader.getInstance().displayImage(item.getImgUrl(), holder.imgFrame, options);
        holder.tvCollect.setText(item.getTitle());

        return convertView;
    }


    private static class ViewHolder {
        ImageView imgFrame;
        TextView tvCollect;

    }


}
