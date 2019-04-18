package com.sklagat46.mcrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.adapter.CategoriesAdapter;
import com.sklagat46.mcrop.listener.CustomItemClickListener;
import com.sklagat46.mcrop.util.CustomGridLayoutManager;
import com.sklagat46.mcrop.util.ItemOffsetDecoration;
import com.sklagat46.mcrop.views.CategoriesViews;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.recycleViewCards)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setupCards();

//       Button loginBtn = (Button) findViewById(R.id.loginBtn)
//        loginBtn.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//               Intent startIntent = new Intent(getApplicationContext(), LoginActivity.class);
//                //show how to pass information to next activity
//                startActivity(startIntent);
//    }
//        });
//                Button registerBtn = (Button) findViewById(R.id.registerBtn);
//              registerBtn.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick(View v) {
//                       Intent startIntent = new Intent(getApplicationContext(), RegisterActivity.class);
//                        //show how to pass information to next activity
//                        startActivity(startIntent);
//
//            }
//        });
   }

    private void setupCards(){
        //Scroll full screen
        CustomGridLayoutManager layoutManager = new CustomGridLayoutManager(this, 2);
        layoutManager.setScrollEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        recyclerView.addItemDecoration(itemDecoration);

        final List<CategoriesViews> cardItems = getCardItems();


        CategoriesAdapter categoriesAdapter = new CategoriesAdapter(this, cardItems, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                CategoriesViews card = cardItems.get(position);
                String cardName = card.getProductName();
                switch (cardName) {
                    case "Fruits":
                        Intent intent = new Intent(getApplicationContext(), FruitsListActivity.class);
                        startActivity(intent);
                        break;
                    case "Vegetables":
                        Intent intentBuying = new Intent(getApplicationContext(), VegetablesListActivity.class);
                        startActivity(intentBuying);
                        break;
                    case "Demand":
                        Intent intentGragh = new Intent(getApplicationContext(), DemandGraphActivity.class);
                        startActivity(intentGragh);
                        break;

                    default:
                        Toast.makeText(MainActivity.this, "Sorry, It's Under development", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        recyclerView.setAdapter(categoriesAdapter);
    }


    private List<CategoriesViews> getCardItems() {
        List<CategoriesViews> listViewItems = new ArrayList<CategoriesViews>();
        listViewItems.add(new CategoriesViews(1, "Vegetables", R.drawable.vegetables_image));
        listViewItems.add(new CategoriesViews(2, "Fruits", R.drawable.fruits_image));
        listViewItems.add(new CategoriesViews(3, "Demand", R.drawable.demand_curve));
        return listViewItems;
    }
}
