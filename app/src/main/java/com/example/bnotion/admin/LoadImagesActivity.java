package com.example.bnotion.admin;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;

public class LoadImagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_images);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        TextView mToolbarText = findViewById(R.id.toolbar_text);
        ViewPager viewPager = findViewById(R.id.view_pager);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent intentData = getIntent();
        ArrayList<String> image_urls = intentData.getStringArrayListExtra("imageURLS");
        String toolbar_text = intentData.getStringExtra("toolbarText");
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, image_urls);
        viewPager.setAdapter(viewPagerAdapter);
        mToolbarText.setText(toolbar_text);


    }
}
