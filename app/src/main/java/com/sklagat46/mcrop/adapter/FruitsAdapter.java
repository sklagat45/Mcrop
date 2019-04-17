package com.sklagat46.mcrop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.ui.FruitMarketActivity;
import com.sklagat46.mcrop.views.FruitsViews;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class FruitsAdapter extends RecyclerView.Adapter<FruitsAdapter.MenuOptionsViewHolder> {
    private Context context;
    private List<FruitsViews> fruitList;
    CustomItemClickListener listener;
    private static final String TAG = "FruitsAdapter";

    public FruitsAdapter(Context context, List<FruitsViews> fruitList, CustomItemClickListener listener) {
        this.context = context;
        this.fruitList = fruitList;
        this.listener = listener;
    }

    @Override
    public MenuOptionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_fruits_item, null);
        final MenuOptionsViewHolder mViewHolder = new MenuOptionsViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
                Intent intent = new Intent(context, FruitMarketActivity.class);
                context.startActivity(intent);

            }
        });


        return mViewHolder;
    }


    @Override
    public void onBindViewHolder(MenuOptionsViewHolder holder, int position) {
        FruitsViews menuItem = fruitList.get(position);

        holder.fruitName.setText(menuItem.getfruitName());
        holder.productImage.setImageResource(menuItem.getproductImage());
        holder.description.setText(menuItem.getdescription());
        holder.productPrice.setText(String.valueOf(menuItem.getproductPrice()));



    }

    @Override
    public int getItemCount() {
        return fruitList.size();
    }



    class MenuOptionsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fruitNameTxtV)
        public TextView fruitName;

        @BindView(R.id.productImageV)
        public ImageView productImage;

        @BindView(R.id.descriptionTxtV)
        public TextView description;

        @BindView(R.id.priceTxtV)
        public TextView productPrice;

        public MenuOptionsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }}