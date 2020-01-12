package com.example.bnotion.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AllUsersActivity extends AppCompatActivity {
    private Query query;
    private ProgressBar mProgress;
    private DatabaseReference mDatabase;
    private RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        mProgress = findViewById(R.id.progressBarMain);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //setting database and recycler view
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);
        mRecyclerView = findViewById(R.id.all_users_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        query = mDatabase;

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Users, UsersViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(Users.class, R.layout.users_view, UsersViewHolder.class,query){
            @Override
            protected void populateViewHolder(UsersViewHolder viewHolder, Users model, int position) {
                final String nameText = model.getUsername();
                final String emailText = model.getEmail();
                final String phoneText = model.getPhone();
                viewHolder.setName(nameText);
                viewHolder.setEmail(emailText);
                mProgress.setVisibility(View.GONE);
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(AllUsersActivity.this, UserDataActivity.class);
                        intent.putExtra("name", nameText);
                        intent.putExtra("email", emailText);
                        intent.putExtra("phone", phoneText);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }
    public static class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        private ItemClickListener itemClickListener;

        public UsersViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemView.setOnClickListener(this);
        }

        public void setName(String name) {
            TextView user_name = mView.findViewById(R.id.user_name);
            user_name.setText(name);
        }

        public void setEmail(String email) {
            TextView email_text = mView.findViewById(R.id.email);
            email_text.setText(email);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
