package com.example.bnotion.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class LoadImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        TextView mToolbarText = findViewById(R.id.toolbar_text);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        Intent intentData = getIntent();
        String imageURL = intentData.getStringExtra("imageURL");
        String toolbar_text = intentData.getStringExtra("toolbarText");
        ImageView mImageView = findViewById(R.id.image_preview);
        ProgressBar mProgresssBar = findViewById(R.id.progressBarPreview);
        Glide.with(this).load(imageURL).into(mImageView);
        mToolbarText.setText(toolbar_text);
        if(mImageView.isShown()){
            mProgresssBar.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
}
