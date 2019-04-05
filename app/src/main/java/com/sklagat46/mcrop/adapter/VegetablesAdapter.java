package com.sklagat46.mcrop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.views.VegetableViews;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class VegetablesAdapter extends RecyclerView.Adapter<VegetablesAdapter.MenuOptionsViewHolder> {
    private Context context;
    private List<VegetableViews> vegetableList;
    CustomItemClickListener listener;

    public VegetablesAdapter(Context context, List<VegetableViews> vegetableList, CustomItemClickListener listener) {
        this.context = context;
        this.vegetableList = vegetableList;
        this.listener = listener;
    }

    @Override
    public MenuOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_vegetables_item, null);
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
        VegetableViews menuItem = vegetableList.get(position);

        holder.vegetableName.setText(menuItem.getvegetableName());
        holder.productImage.setImageResource(menuItem.getproductImage());
        holder.description.setText(menuItem.getdescription());
        holder.productPrice.setText(String.valueOf(menuItem.getproductPrice()));

    }

    @Override
    public int getItemCount() {
        return vegetableList.size();
    }



    class MenuOptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vegetableNameETxt)
        public TextView vegetableName;

        @BindView(R.id.productImageV)
        public ImageView productImage;

        @BindView(R.id.descriptionETxt)
        public TextView description;

        @BindView(R.id.priceETxt)
        public TextView productPrice;




        public MenuOptionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
