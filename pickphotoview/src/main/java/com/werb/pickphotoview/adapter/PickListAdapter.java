package com.werb.pickphotoview.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.werb.pickphotoview.R;
import com.werb.pickphotoview.model.DirImage;
import com.werb.pickphotoview.model.GroupImage;
import com.werb.pickphotoview.util.PickPreferences;

import java.util.List;

/**
 * Created by wanbo on 2017/1/3.
 */

public class PickListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private GroupImage groupImage;
    private DirImage dirImage;
    private View.OnClickListener listener;
    private Context context;

    public PickListAdapter(Context c, View.OnClickListener listener) {
        this.context = c;
        this.groupImage = PickPreferences.getInstance(context).getListImage();
        this.dirImage = PickPreferences.getInstance(context).getDirImage();
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GroupImageViewHolder holder = new GroupImageViewHolder(LayoutInflater.from(context).inflate(R.layout.pick_item_list_layout, parent, false));
        holder.itemView.setOnClickListener(listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (dirImage != null) {
            String dirName = dirImage.dirName.get(position);
            List<String> paths = groupImage.mGroupMap.get(dirName);
            GroupImageViewHolder groupImageViewHolder = (GroupImageViewHolder) holder;
            groupImageViewHolder.bindItem(dirName, paths);
        }

    }

    @Override
    public int getItemCount() {
        if (dirImage != null) {
            return dirImage.dirName.size();
        } else {
            return 0;
        }
    }

    private class GroupImageViewHolder extends RecyclerView.ViewHolder {

        private ImageView cover, open;
        private TextView dirNameText, photoSizeText;

        GroupImageViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.iv_cover);
            open = (ImageView) itemView.findViewById(R.id.iv_list_open);
            dirNameText = (TextView) itemView.findViewById(R.id.tv_dir_name);
            photoSizeText = (TextView) itemView.findViewById(R.id.tv_photo_size);

            Drawable drawable = context.getResources().getDrawable(R.mipmap.pick_list_open);
            drawable.setColorFilter(context.getResources().getColor(R.color.pick_gray), PorterDuff.Mode.SRC_ATOP);
            open.setBackgroundDrawable(drawable);
        }

        void bindItem(String dirName, List<String> paths) {
            dirNameText.setText(dirName);
            photoSizeText.setText(String.format(context.getString(R.string.pick_photo_size), paths.size() + ""));
            Glide.with(context).load(Uri.parse("file://" + paths.get(0))).thumbnail(0.3f).into(cover);
            itemView.setTag(R.id.pick_dir_name, dirName);
        }

    }


}
