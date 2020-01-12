package com.example.bnotion.admin;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HousingDetailActivity extends AppCompatActivity {
    String username, title, location, postID, price_range, description, price, category, baths, toilets, state, phoneNumber, email, status, imageURL, imageURLS, profileURL, userId, dateString;
    private ImageView mPostedHouse, mCopyClipboard;
    private TextView mTitle, mLocation, mCategoryText, mPriceText, mStateText, mBathsText, mToiletsText, mDescriptionText, mUsernameText, mEmailText, mNumberText, mStatusText, mTimeText;
    private Button mShowPhone;
    private CircleImageView mProfileImage;
    private FloatingActionButton fab_fav;
    private String buttonState = "hid";
    private int image_no;
    private Button mDeleteButton;
    private ArrayList<String> image_urls;
    private ProgressBar mProgress;
    private FloatingActionButton fab;
    int id = 0;
    private FirebaseFirestore firebaseFirestore;
    private LinearLayout bathsLayout, toiletsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_housing_detail);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mProgress = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab_add);
        final TextView textView = findViewById(R.id.trending_text);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        bathsLayout = findViewById(R.id.bathsLayout);
        toiletsLayout = findViewById(R.id.toiletsLayout);
        //Get String Extras
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        title = intent.getStringExtra("title");
        location = intent.getStringExtra("location");
        description = intent.getStringExtra("description");
        price = intent.getStringExtra("price");
        category = intent.getStringExtra("category");
        baths = intent.getStringExtra("baths");
        postID = intent.getStringExtra("postID");
        toilets = intent.getStringExtra("toilets");
        dateString = intent.getStringExtra("dateString");
        state = intent.getStringExtra("state");
        phoneNumber = intent.getStringExtra("phoneNumber");
        email = intent.getStringExtra("email");
        status = intent.getStringExtra("status");
        imageURL = intent.getStringExtra("imageURL");
        price_range = intent.getStringExtra("price_range");
        image_urls = getIntent().getStringArrayListExtra("imageURLS");
        if (category.equals("Land") || title.contains("LAND") || title.contains("Land") || title.contains("land")) {
            bathsLayout.setVisibility(View.GONE);
            toiletsLayout.setVisibility(View.GONE);
        }
        if (image_urls != null) {
            image_no = image_urls.size();
        }
        String imageNumberString = String.valueOf(image_no);
        if (imageURL.equals("null") && image_urls.size() > 1) {
            textView.setText(imageNumberString + " Images Present");
        } else if (imageURL.equals("null") && image_urls.size() == 1) {
            textView.setText(imageNumberString + " Image Present");
        } else {
            textView.setText("1" + " Image Present");
        }
        if (image_urls != null) {
            imageURLS = image_urls.get(0);
        }

        profileURL = intent.getStringExtra("profileURL");
        userId = intent.getStringExtra("userID");
        //finding views by IDS
        mTitle = findViewById(R.id.title);
        mPostedHouse = findViewById(R.id.posted_house);
        mPostedHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!imageURL.equals("null")) {
                    Intent intent = new Intent(HousingDetailActivity.this, LoadImageActivity.class);
                    intent.putExtra("imageURL", imageURL);
                    intent.putExtra("toolbarText", textView.getText().toString());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(HousingDetailActivity.this, LoadImagesActivity.class);
                    intent.putExtra("imageURLS", image_urls);
                    intent.putExtra("toolbarText", textView.getText().toString());
                    startActivity(intent);
                }

            }
        });
        mLocation = findViewById(R.id.location);
        mCopyClipboard = findViewById(R.id.copy_clipboard);
        mShowPhone = findViewById(R.id.show_phone);
        mCategoryText = findViewById(R.id.category_text);
        mPriceText = findViewById(R.id.price_text);
        mStateText = findViewById(R.id.state_text);
        mBathsText = findViewById(R.id.baths_text);
        mToiletsText = findViewById(R.id.toilets_text);
        mDescriptionText = findViewById(R.id.description_text);
        mProfileImage = findViewById(R.id.profile_image);
        mUsernameText = findViewById(R.id.username_text);
        mEmailText = findViewById(R.id.email_text);
        mNumberText = findViewById(R.id.number_text);
        mStatusText = findViewById(R.id.status_text);
        mTimeText = findViewById(R.id.time_text);
        //setting widgets
        mTitle.setText(title);
        mLocation.setText(location);
        mUsernameText.setText(username);
        mDescriptionText.setText(description);
        mPriceText.setText(price);
        mCategoryText.setText(category);
        mBathsText.setText(baths);
        mToiletsText.setText(toilets);
        mStateText.setText(state);
        mEmailText.setText(email);
        mStatusText.setText(status);
        mNumberText.setText(phoneNumber);
        mTimeText.setText(dateString);
        RequestOptions placeholderRequest = new RequestOptions();
        RequestOptions placeholderRequest2 = new RequestOptions();
        placeholderRequest.placeholder(R.drawable.placeholder);
        placeholderRequest2.placeholder(R.drawable.circle_image);
        if (imageURL.equals("null")) {
            Glide.with(this).setDefaultRequestOptions(placeholderRequest).load(imageURLS).into(mPostedHouse);
        } else {
            Glide.with(this).setDefaultRequestOptions(placeholderRequest).load(imageURL).into(mPostedHouse);
        }

        Glide.with(this).setDefaultRequestOptions(placeholderRequest2).load(profileURL).into(mProfileImage);
        mShowPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShowPhone.getText().toString().equals("Show phone")) {
                    mShowPhone.setText(phoneNumber);
                } else {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + phoneNumber));
                    startActivity(intent);
                }
            }
        });
        mCopyClipboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("phone", phoneNumber);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(HousingDetailActivity.this, "copied to clipboard", Toast.LENGTH_LONG).show();
            }
        });
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        edit_post();
    }
});

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share:
                shareHousingInfo();
                break;
            case R.id.edit_post:
            edit_post();
                break;
            case R.id.delete_post:
                final AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setMessage("You are about to delete this post. Are you sure about this?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                          deletePost();
                                finish();
                            }
                        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                alertDialog.show();
                break;
        }
        return true;
    }

    private void edit_post() {
        Intent intent = new Intent(HousingDetailActivity.this, AddPostActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("title", title);
        intent.putExtra("location", location);
        intent.putExtra("description", description);
        intent.putExtra("price", price);
        intent.putExtra("category", category);
        intent.putExtra("baths", baths);
        intent.putExtra("toilets", toilets);
        intent.putExtra("state", state);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("email", email);
        intent.putExtra("status", status);
        intent.putExtra("imageURL", imageURL);
        intent.putExtra("profileURL", profileURL);
        intent.putExtra("dateString", dateString);
        intent.putExtra("userID", userId);
        intent.putExtra("price_range", price_range);
        intent.putExtra("postID", postID);
        intent.putExtra("data", "data");
        intent.putExtra("imageURLS", image_urls);
        startActivity(intent);
        finish();
    }


    private void deletePost() {
        mProgress.setVisibility(View.VISIBLE);
        firebaseFirestore.collection("Posts").document(postID).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(HousingDetailActivity.this, "Post Deleted Successfully", Toast.LENGTH_SHORT).show();
                }  else {
                    String error = task.getException().getMessage();
                    Toast.makeText(HousingDetailActivity.this, error, Toast.LENGTH_SHORT).show();

                }
                mProgress.setVisibility(View.GONE);
            }
        });
    }


    private void shareHousingInfo() {
        Bitmap bitmap = getBitmapFromView(mPostedHouse);
        try {
            File file = new File(this.getExternalCacheDir(),"image.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            intent .putExtra(android.content.Intent.EXTRA_SUBJECT, "Find A Place");
            intent.putExtra(android.content.Intent.EXTRA_TEXT, title.toUpperCase() + "\n\n" + description + "\n\nTap on the Link Below to View Full Property Details\n\nhttps://goo.gl/kmL9zi");
            intent.setType("image/png");
            startActivity(Intent.createChooser(intent, "Choose App"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HousingDetailActivity.this, EstateAgentsActivity.class);
        startActivity(intent);
        finish();
    }
}
