package com.example.bnotion.admin;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AgentPostsActivity extends AppCompatActivity {

    private TextView toolbarText;
    private RecyclerView mRecyclerView;
    private List<AgentPost> post_list;
    private FirebaseFirestore firebaseFirestore;
    private AgentPostRecyclerAdapter agentPostRecyclerAdapter;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private TextView mTextView;
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_posts);
        Intent intent = getIntent();
        final String user_id = intent.getStringExtra("user_id");
        final String username = intent.getStringExtra("username");
        toolbarText = findViewById(R.id.toolbar_text);
        mImageView = findViewById(R.id.image);
        mTextView = findViewById(R.id.info_text);
        fab = findViewById(R.id.fab_add);
        toolbarText.setText(username + " (Posts)");
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        post_list = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recyclerView);
        mProgressBar = findViewById(R.id.progressBarMain);
        agentPostRecyclerAdapter = new AgentPostRecyclerAdapter(post_list, AgentPostsActivity.this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(agentPostRecyclerAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Query firstQuery = firebaseFirestore.collection("Posts").whereEqualTo("user_id", user_id);;
        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        String blogPostID = doc.getDocument().getId();
                        AgentPost agentPost = doc.getDocument().toObject(AgentPost.class).withId(blogPostID);
                        post_list.add(agentPost);
                        agentPostRecyclerAdapter.notifyDataSetChanged();
                        mProgressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
        Query mQuery = firebaseFirestore.collection("Posts").whereEqualTo("user_id", user_id);
        mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.getResult().isEmpty()){
                    mProgressBar.setVisibility(View.GONE);
                    mImageView.setVisibility(View.VISIBLE);
                    mTextView.setVisibility(View.VISIBLE);
                }
            }
        });

fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(AgentPostsActivity.this, AddPostActivity.class);
        intent.putExtra("userID", user_id);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
});
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
