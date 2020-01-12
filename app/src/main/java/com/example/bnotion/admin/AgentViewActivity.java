package com.example.bnotion.admin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgentViewActivity extends AppCompatActivity {
private String mUserId, name, documentType, document, profileImage;
private TextView mName, mDocumentType, agentToolbarText;
private ImageView mProfileImage, mDocument;
private Button mVerify, mUnverify;
private ProgressBar mProgress;
private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_view);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Agents");
        mName = findViewById(R.id.agent_name);
        mDocumentType = findViewById(R.id.document_type);
        mProfileImage = findViewById(R.id.profile_image);
        mDocument = findViewById(R.id.document);
        mVerify = findViewById(R.id.verify_btn);
        mUnverify = findViewById(R.id.unverify_btn);
        mProgress = findViewById(R.id.progressBar);
        agentToolbarText = findViewById(R.id.agents_text);
        //Get String Extras
        Intent intent = getIntent();
        mUserId = intent.getStringExtra("user_id");
        name = intent.getStringExtra("name");
        documentType = intent.getStringExtra("document_type");
        document = intent.getStringExtra("document");
        profileImage = intent.getStringExtra("image");
        mName.setText(name);
        mDocumentType.setText(documentType);
        agentToolbarText.setText(name);
        RequestOptions placeholderRequest = new RequestOptions();
        placeholderRequest.placeholder(R.drawable.avatar_image);
        Glide.with(this).setDefaultRequestOptions(placeholderRequest).load(profileImage).into(mProfileImage);
        Glide.with(this).setDefaultRequestOptions(placeholderRequest).load(document).into(mDocument);
        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         Intent intent1 = new Intent(AgentViewActivity.this, ImageLoadActivity.class);
         intent1.putExtra("imageURL", profileImage);
         startActivity(intent1);
            }
        });
        mDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AgentViewActivity.this, ImageLoadActivity.class);
                intent1.putExtra("imageURL", document);
                startActivity(intent1);
            }
        });
        mVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        mProgress.setVisibility(View.VISIBLE);
        databaseReference.child(mUserId).child("status").setValue("Verified").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AgentViewActivity.this, "User Verified Successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AgentViewActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
                mProgress.setVisibility(View.GONE);
            }
        });
            }
        });
        mUnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setVisibility(View.VISIBLE);
                databaseReference.child(mUserId).child("status").setValue("Pending verification").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AgentViewActivity.this, "This user has been unverified by you", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(AgentViewActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                        mProgress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
