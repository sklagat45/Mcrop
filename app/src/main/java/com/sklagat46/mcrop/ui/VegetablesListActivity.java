package com.sklagat46.mcrop.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.adapter.VegetablesAdapter;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.views.VegetableViews;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VegetablesListActivity extends AppCompatActivity {
    private List<VegetableViews> vegetableList = new ArrayList<>();
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private VegetablesAdapter mAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_list);
        ButterKnife.bind(this);
        setUpActionBar();
        setupListView();

    }

    private void setUpActionBar() {
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationIcon(upArrow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        onBackPressed();
        return true;
    }


    private void setupListView() {

        @SuppressLint("WrongConstant") LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new VegetablesAdapter(this, vegetableList(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //VegetableViews dispatchView = (VegetableViews) mAdapter.getItem(position);
            }
        });

        recyclerView.setAdapter(mAdapter);


    }

    private List<VegetableViews> vegetableList() {
        VegetableViews vegetableViews = new VegetableViews(0, "Cabbage", R.drawable.cabbage, 200, "Fresh,rich in vitamins");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(1, "spinach", R.drawable.spinach, 20, "fresh");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(2, "kale", R.drawable.kales, 100, "Fresh");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(3, "Onions", R.drawable.onions, 50, "Recently harvested");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(4, "Tomatoes", R.drawable.tomato, 150, "Fresh");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(0, "Cabbage", R.drawable.cabbage, 200, "Fresh,rich in vitamins");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(1, "spinach", R.drawable.spinach, 20, "fresh");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(2, "kale", R.drawable.kales, 100, "Fresh");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(3, "Onions", R.drawable.onions, 50, "Recently harvested");
        vegetableList.add(vegetableViews);

        vegetableViews = new VegetableViews(4, "Tomatoes", R.drawable.tomato, 150, "Fresh");
        vegetableList.add(vegetableViews);

        return vegetableList;


    }

    public void btnAddVeg(View view) {
        Intent intent = new Intent(getApplicationContext(), AddVegetableStockActivity.class);
        startActivity(intent);
    }
}
