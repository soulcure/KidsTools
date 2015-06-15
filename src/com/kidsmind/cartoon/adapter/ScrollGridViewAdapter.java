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
import com.kidsmind.cartoon.entity.CartoonItem;
import com.kidsmind.cartoon.uitls.ScreenUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ScrollGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CartoonItem> mList;
    private DisplayImageOptions options;   //图片下载配置

    public ScrollGridViewAdapter(Context context, List<CartoonItem> list) {
        this.mContext = context;
        this.mList = list;
        int roundPx = (int) ScreenUtils.dpToPx(context, 15);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)  // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)    // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)       // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(roundPx))  // 设置成圆角图片
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

    }


    public void setList(List<CartoonItem> list) {
        mList.addAll(list);

        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CartoonItem item = (CartoonItem) getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.gird_item, null);
            holder = new ViewHolder();
            holder.imgPlayer = (ImageView) convertView.findViewById(R.id.img_player);
            holder.imgTag = (ImageView) convertView.findViewById(R.id.img_tag);
            holder.tvPlayer = (TextView) convertView.findViewById(R.id.tv_player);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(item.getImgUrl(), holder.imgPlayer, options);

        holder.tvPlayer.setText(item.getTitle());

        return convertView;
    }


    private class ViewHolder {
        ImageView imgPlayer;
        ImageView imgTag;
        TextView tvPlayer;

    }

}
