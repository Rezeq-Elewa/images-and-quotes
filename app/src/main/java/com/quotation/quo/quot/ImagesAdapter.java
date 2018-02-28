package com.quotation.quo.quot;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rezeq on 2/23/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private List<Image> imageList;
    private NewMainActivity activity;
    private LoadMoreListener loadMoreListener;
    String type;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        LinearLayout llText;
        private View view;

        MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.tv_text);
            image = view.findViewById(R.id.iv_image);
            this.view = view;
        }
    }


    public ImagesAdapter(List<Image> imageList, NewMainActivity activity, String type) {
        this.type = type;
        this.activity = activity;
        this.imageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_list_item, parent, false);
        if (type.equalsIgnoreCase("list")) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            Resources resources = activity.getResources();
            DisplayMetrics metrics = resources.getDisplayMetrics();
            float px = 200 * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            params.height = (int) px;
            itemView.setLayoutParams(params);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Image image = imageList.get(position);
        holder.text.setText(image.getTextEn());
        Picasso.with(activity)
                .load(image.getUrl())
                .placeholder(R.color.cardview_dark_background)
                .into(holder.image);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.imageClicked(holder.getAdapterPosition());
            }
        });

        if(position % 20 == 0){
            if(loadMoreListener != null){
                loadMoreListener.loadMore();
            }
        }
    }

    public void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
