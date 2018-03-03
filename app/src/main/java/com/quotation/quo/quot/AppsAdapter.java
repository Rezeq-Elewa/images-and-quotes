package com.quotation.quo.quot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Rezeq on 3/2/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.MyViewHolder> {

    private List<App> appsList;
    private AppsActivity activity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView title, description;
        ImageView image;
        private View view;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.app_name);
            description = view.findViewById(R.id.app_description);
            image = view.findViewById(R.id.app_image);
            this.view = view;
        }
    }


    public AppsAdapter(List<App> appsList, AppsActivity activity) {
        this.activity = activity;
        this.appsList = appsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AppsAdapter.MyViewHolder holder, int position) {
        final App app = appsList.get(position);
        holder.title.setText(app.getName());
        holder.description.setText(app.getDescriptionEn());
        Picasso.with(activity)
                .load(app.getImg())
                .placeholder(R.color.cardview_dark_background)
                .into(holder.image);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.appClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }
}