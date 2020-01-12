package com.example.bnotion.admin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PostNewsActivity extends AppCompatActivity {
private EditText mTitle, mContent;
private String title, content;
private ProgressBar mProgressBar;
private FirebaseFirestore firebaseFirestore;
private Button mPostNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_news);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        firebaseFirestore = FirebaseFirestore.getInstance();
        mPostNews = findViewById(R.id.post);
        mTitle = findViewById(R.id.title);
        mContent = findViewById(R.id.content);
        mProgressBar = findViewById(R.id.progressBar);
        mPostNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        // fetching string values
         title = mTitle.getText().toString().trim();
         content = mContent.getText().toString().trim();
                if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(content)) {
                    mProgressBar.setVisibility(View.VISIBLE);
                    Map<String, Object> map = new HashMap<>();
                    map.put("title",title);
                    map.put("timestamp", FieldValue.serverTimestamp());
                    map.put("content",content);
               firebaseFirestore.collection("News").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {

                       if (task.isSuccessful()){
                           finish();
                           Toast.makeText(PostNewsActivity.this, "New Post Added", Toast.LENGTH_SHORT).show();
                       } else {
                           String errorMessage = task.getException().getMessage();
                           Toast.makeText(PostNewsActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                       }
                       mProgressBar.setVisibility(View.GONE);
                   }
               });

                } else if (TextUtils.isEmpty(title)) {
                    mTitle.setError("Empty Field");
                } else if (TextUtils.isEmpty(content)) {
                   mContent.setError("Empty Field");
                }
            }
        });

    }
}
