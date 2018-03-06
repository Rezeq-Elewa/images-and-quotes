package com.quotation.quo.quot;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Rezeq on 3/5/2018.
 * Email : rezeq.elewa@gmail.com
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private List<Section> categoriesList;
    private NewMainActivity activity;

    class MyViewHolder extends RecyclerView.ViewHolder {
        CustomTextView title ;
        private View view;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.tv_category_name);
            this.view = view;
        }
    }


    public CategoriesAdapter(List<Section> categoriesList, NewMainActivity activity) {
        this.activity = activity;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_list_item, parent, false);
        return new CategoriesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoriesAdapter.MyViewHolder holder, int position) {
        final Section section = categoriesList.get(position);
        holder.title.setText(section.getSectionName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.sectionClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }
}