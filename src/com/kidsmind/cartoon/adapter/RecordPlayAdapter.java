package com.kidsmind.cartoon.adapter;

import java.util.List;

import com.kidsmind.cartoon.R;
import com.kidsmind.cartoon.entity.RecordItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RecordPlayAdapter extends BaseAdapter {
    private Context mContext;
    private List<RecordItem> mList;
    private DisplayImageOptions options; // 图片下载配置

    public RecordPlayAdapter(Context context, List<RecordItem> list) {
        this.mContext = context;
        this.mList = list;
        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(20))  // 设置成圆角图片
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setList(List<RecordItem> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 元素的个数
     */
    public int getCount() {
        return mList.size();
    }

    public Object getItem(int position) {
        return mList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // 用以生成在ListView中展示的一个个元素View
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordItem item = (RecordItem) getItem(position);
        ViewHolder holder;
        // 优化ListView
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.record_item, null);
            holder = new ViewHolder();

            holder.tvPreferences1 = (TextView) convertView
                    .findViewById(R.id.preferences1);
            holder.tvPreferences2 = (TextView) convertView
                    .findViewById(R.id.preferences2);
            holder.tvPreferences3 = (TextView) convertView
                    .findViewById(R.id.preferences3);
            holder.tvEpisodeName = (TextView) convertView
                    .findViewById(R.id.name);
            holder.tvPlayTime = (TextView) convertView
                    .findViewById(R.id.istime);
            holder.mImageView = (ImageView) convertView
                    .findViewById(R.id.image);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // 设置文本和图片，然后返回这个View，用于ListView的Item的展示
        holder.tvEpisodeName.setText(item.getEpisodeName());
        holder.tvPreferences1.setText(item.getPreferences()[0]);
        holder.tvPreferences2.setText(item.getPreferences()[1]);
        holder.tvPreferences3.setText(item.getPreferences()[2]);
        holder.tvPlayTime.setText(item.getPlayTime());

        ImageLoader.getInstance().displayImage(item.getImgUrl(),
                holder.mImageView, options);

        return convertView;
    }

    private class ViewHolder {
        public TextView tvEpisodeName;
        public TextView tvPreferences1;
        public TextView tvPreferences2;
        public TextView tvPreferences3;
        public TextView tvPlayTime;
        public ImageView mImageView;
    }

}
