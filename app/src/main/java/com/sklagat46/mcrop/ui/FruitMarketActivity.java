package com.sklagat46.mcrop.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sklagat46.mcrop.R;
import com.sklagat46.mcrop.views.AddStockViews;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class FruitMarketActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference databaseFruitDetails;
    ArrayList<AddStockViews> list;
    ArrayAdapter<AddStockViews> adapter;
    AddStockViews addStockViews;


    @BindView(R.id.imgv_photo)
    ImageView img_photo;
    @BindView(R.id.editTextProductName)
    EditText productName;
    @BindView(R.id.locationETxt)
    EditText location;
    @BindView(R.id.descriptionETxt)
    EditText description;
    @BindView(R.id.infoSection)
    EditText cropInfor;
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_market);

        addStockViews =new AddStockViews();

        database = FirebaseDatabase.getInstance();
        databaseFruitDetails = database.getReference("fruitDetails");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<AddStockViews>(this,R.layout.activity_fruit_market,R.id.infoSection,list);

        databaseFruitDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    addStockViews = ds.getValue(AddStockViews.class);
                    list.add(addStockViews);

                }
//                x

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }}


//    private class addValueEventListener extends ArrayAdapter<AddStockViews> {
//        public addValueEventListener(ValueEventListener valueEventListener) {
//            super();
//        }
//    }
//}recyclerView.setAdapter(adapter);
