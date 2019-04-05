package com.sklagat46.mcrop.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.views.CategoriesViews;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MenuOptionsViewHolder> {
    private Context context;
    private List<CategoriesViews> categoriesList;
    CustomItemClickListener listener;

    public CategoriesAdapter(Context context, List<CategoriesViews> categoriesList, CustomItemClickListener listener) {
        this.context = context;
        this.categoriesList = categoriesList;
        this.listener = listener;
    }

    @Override
    public MenuOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_category_item, null);
        final MenuOptionsViewHolder mViewHolder = new MenuOptionsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuOptionsViewHolder holder, int position) {
        CategoriesViews menuItem = categoriesList.get(position);

        holder.textView.setText(menuItem.getProductName());
        holder.imageView.setImageResource(menuItem.getImageName());

    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class MenuOptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        public ImageView imageView;

        @BindView(R.id.textView)
        public TextView textView;


        public MenuOptionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}