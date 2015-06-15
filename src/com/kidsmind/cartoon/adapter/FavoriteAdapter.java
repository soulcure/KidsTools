package com.kidsmind.cartoon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kidsmind.cartoon.R;
import com.kidsmind.cartoon.entity.FavoriteItem;
import com.kidsmind.cartoon.entity.SerieItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


public class FavoriteAdapter extends BaseAdapter {

    private Context mContext;
    private List<FavoriteItem> mLists;
    private DisplayImageOptions options;   //图片下载配置


    public FavoriteAdapter(Context context, List<FavoriteItem> list) {
        mContext = context;
        mLists = list;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }


    public List<FavoriteItem> getLists() {
        return mLists;
    }

    public void setList(List<FavoriteItem> lists) {
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
        final FavoriteItem item = (FavoriteItem) getItem(position);
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
        holder.tvCollect.setText(item.getSerieName());

        return convertView;
    }


    private static class ViewHolder {
        ImageView imgFrame;
        TextView tvCollect;

    }


}
