package com.sklagat46.mcrop.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.data.model.Vegetable;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.ui.VegetableMarketActivity;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class VegetablesAdapter extends RecyclerView.Adapter<VegetablesAdapter.MenuOptionsViewHolder> {
    private Context context;
    private List<Vegetable> vegetableList;
    CustomItemClickListener listener;

    public VegetablesAdapter(Context context, List<Vegetable> vegetableList, CustomItemClickListener listener) {
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
                Intent intent = new Intent(context, VegetableMarketActivity.class);
                context.startActivity(intent);
            }
        });
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuOptionsViewHolder holder, int position) {
        Vegetable menuItem = vegetableList.get(position);

        holder.vegetableName.setText(menuItem.getVegetableName());
        holder.productImage.setImageBitmap(getBitmapFromString(menuItem.getProductImage()));
        holder.description.setText(menuItem.getDescription());
        holder.productPrice.setText(String.valueOf(menuItem.getLocation()));

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

    //covert imagestring to bitmap
    private Bitmap getBitmapFromString(String input) {

        byte[] decodedString = Base64.decode(input, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

}
