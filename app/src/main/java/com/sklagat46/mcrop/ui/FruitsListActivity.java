package com.sklagat46.mcrop.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.adapter.FruitsAdapter;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.views.FruitsViews;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FruitsListActivity extends AppCompatActivity {
    private List<FruitsViews> fruitList = new ArrayList<>();
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    private FruitsAdapter mAdapter;

   // FloatingActionButton sellFab = (FloatingActionButton) findViewById(R.id.sellFab);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruits_list);
        ButterKnife.bind(this);
        setupListView();

    }

    private void setupListView() {

        @SuppressLint("WrongConstant") LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new FruitsAdapter(this, fruitList(), new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //FruitViews dispatchView = (FruitViews) mAdapter.getItem(position);
            }
        });

        recyclerView.setAdapter(mAdapter);


    }

    private List<FruitsViews> fruitList() {
        FruitsViews fruitViews = new FruitsViews(0, "Apple", R.drawable.apple, 200, "Fresh,rich in vitamins");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(1, "Avocado", R.drawable.avocado, 20, "fresh");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(2, "Banana", R.drawable.bananas, 100, "Fresh");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(3, "Mango", R.drawable.mango, 50, "Recently harvested");
        fruitList.add(fruitViews);


        fruitViews = new FruitsViews(4, "Oranges", R.drawable.oranges, 150, "Fresh");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(0, "Pineapples", R.drawable.pineapple, 200, "Fresh,rich in vitamins");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(1, "Watermelon", R.drawable.watermelon, 20, "fresh");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(2, "Mango", R.drawable.mango, 100, "Fresh");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(3, "Avocado", R.drawable.avocado, 50, "Recently harvested");
        fruitList.add(fruitViews);

        fruitViews = new FruitsViews(4, "Apple", R.drawable.apple, 150, "Fresh");
        fruitList.add(fruitViews);

        return fruitList;


    }

}






