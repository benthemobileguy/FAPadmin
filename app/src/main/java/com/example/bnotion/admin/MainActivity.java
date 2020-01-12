package com.example.bnotion.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
private ProgressBar mProgress;
private DatabaseReference databaseReferenceAgents;
private DatabaseReference databaseReferenceUsers;
private int agentCount, userCount, newsCount = 0;
private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgress = findViewById(R.id.progressBar);
        firebaseFirestore = FirebaseFirestore.getInstance();
        final Button mAgentsButton = findViewById(R.id.estate_agents);
        final Button mNewsButton = findViewById(R.id.post_news);
        final Button mUsersButton = findViewById(R.id.all_users);
        databaseReferenceAgents = FirebaseDatabase.getInstance().getReference(Constants.AGENTS_KEY);
        databaseReferenceUsers = FirebaseDatabase.getInstance().getReference(Constants.USER_KEY);
        databaseReferenceAgents.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                agentCount = (int) dataSnapshot.getChildrenCount();
                mAgentsButton.setText("ESTATE AGENTS " + "(" + String.valueOf(agentCount) + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        databaseReferenceUsers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userCount = (int) dataSnapshot.getChildrenCount();
                mUsersButton.setText("ALL USERS " + "(" + String.valueOf(userCount) + ")");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        firebaseFirestore.collection("News").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                newsCount  = queryDocumentSnapshots.size();
                mNewsButton.setText("POST NEWS " + "(" + String.valueOf(newsCount) + ")");
                mProgress.setVisibility(View.GONE);
            }
        });
        mAgentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EstateAgentsActivity.class);
                startActivity(intent);
            }
        });
        mNewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PostNewsActivity.class);
                startActivity(intent);
            }
        });
        mUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AllUsersActivity.class);
                startActivity(intent);            }
        });
    }
}
