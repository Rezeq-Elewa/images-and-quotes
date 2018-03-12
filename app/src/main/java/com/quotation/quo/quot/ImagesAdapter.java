package com.quotation.quo.quot;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by Rezeq on 2/23/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.MyViewHolder> {

    private List<Image> imageList;
    private NewMainActivity activity;
    private LoadMoreListener loadMoreListener;
    private String type;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        private View view;

        MyViewHolder(View view) {
            super(view);
            text = view.findViewById(R.id.tv_text);
            image = view.findViewById(R.id.iv_image);
            this.view = view;
        }
    }


    ImagesAdapter(List<Image> imageList, NewMainActivity activity, String type) {
        this.type = type;
        this.activity = activity;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Image image = imageList.get(position);
//        Picasso.with(activity)
//                .load(image.getImageUrl())
//                .placeholder(R.color.cardview_dark_background)
//                .resize(200, 0)
//                .centerCrop()
//                .into(holder.image);

        RequestOptions options;
        if (type.equalsIgnoreCase("list")){
            options = new RequestOptions().placeholder(R.color.cardview_dark_background)
                    .error(R.color.cardview_dark_background)
                    .override(holder.image.getWidth(),0)
                    .centerCrop();
        }else{
            options = new RequestOptions()
                    .placeholder(R.color.cardview_dark_background)
                    .error(R.color.cardview_dark_background)
                    .override(holder.image.getWidth(),0)
                    .centerInside();
        }

        Glide.with(activity)
                .load(image.getImageUrl())
                .apply(options)
                .into(holder.image);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.imageClicked(holder.getAdapterPosition());
            }
        });

        if(position + 1 == imageList.size() && (position + 1) % 20 == 0){
            if(loadMoreListener != null){
                loadMoreListener.loadMore();
            }
        }

        if(image.getText().isEmpty() || image.getText().equalsIgnoreCase(" ")){
            holder.text.setVisibility(View.GONE);
        } else{
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(image.getText());
        }
    }

    void setLoadMoreListener(LoadMoreListener loadMoreListener){
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}
