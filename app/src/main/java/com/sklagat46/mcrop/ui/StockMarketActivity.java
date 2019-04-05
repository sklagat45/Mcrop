package com.sklagat46.mcrop.ui;

import android.os.Bundle;
import android.util.Log;

import com.sklagat46.mcrop.R;

import javax.annotation.Nullable;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 */
public class StockMarketActivity extends AppCompatActivity {
    private static final String TAG = "StockMarketActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_market);
        Log.d(TAG,"onCreate:started.");

    }
}

