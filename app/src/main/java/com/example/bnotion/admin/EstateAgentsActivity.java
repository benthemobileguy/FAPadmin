package com.example.bnotion.admin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class EstateAgentsActivity extends AppCompatActivity {
private RecyclerView mRecyclerView;
private DatabaseReference mDatabase;
private Dialog mDialog;
private ProgressBar mProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estate_agents);
        mDialog = new Dialog(this);
        mProgress = findViewById(R.id.progressBarMain);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Agents");
        mDatabase.keepSynced(true);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        mRecyclerView = findViewById(R.id.all_users_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<Agents, AgentsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Agents,
                AgentsViewHolder>(Agents.class,
                R.layout.agents_single_layout,
                AgentsViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(final AgentsViewHolder viewHolder, final Agents model, int position) {
                final String user_uid = model.getUser_id();
                viewHolder.setName(model.getName());
                viewHolder.setStatus(model.getStatus());
                viewHolder.setImage(model.getImage(), getApplicationContext());
                mProgress.setVisibility(View.GONE);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Agents");
                DatabaseReference finalRef = databaseReference.child(user_uid).child("status");
                finalRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String status = dataSnapshot.getValue(String.class);
                        if (status.equals("Pending verification")) {
                       viewHolder.mVerifyIndicator.setImageResource(R.drawable.dot);
                        } else if (status.equals("Verified")){
                           viewHolder.mVerifyIndicator.setImageResource(R.drawable.approved);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mDialog.setContentView(R.layout.custom_dialog);
                        Button postsBtn = mDialog.findViewById(R.id.posts);
                        Button infoBtn = mDialog.findViewById(R.id.info);
                        TextView agentName = mDialog.findViewById(R.id.agent_name);
                        agentName.setText(model.getName());
                        ImageView close = mDialog.findViewById(R.id.close_dialog);
                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mDialog.dismiss();
                            }
                        });
                        postsBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(EstateAgentsActivity.this, AgentPostsActivity.class);
                                intent.putExtra("user_id", model.getUser_id());
                                intent.putExtra("username", model.getName());
                                startActivity(intent);
                                mDialog.dismiss();
                            }
                        });
                       infoBtn.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent = new Intent(EstateAgentsActivity.this, AgentViewActivity.class);
                               intent.putExtra("name", model.getName());
                               intent.putExtra("image", model.getImage());
                               intent.putExtra("document_type", model.getDocument_type());
                               intent.putExtra("document", model.getDocument());
                               intent.putExtra("user_id", model.getUser_id());
                               startActivity(intent);
                               mDialog.dismiss();
                           }
                       });
                       mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                       mDialog.show();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public static class AgentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View mView;
        private ItemClickListener itemClickListener;
        private ImageView mVerifyIndicator;
        public AgentsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mVerifyIndicator = mView.findViewById(R.id.mDot);
            itemView.setOnClickListener(this);
        }

        public void setName(String name) {
            TextView name_agent = mView.findViewById(R.id.user_name);
            name_agent.setText(name);
        }

        public void setStatus(String status) {
            TextView name_agent = mView.findViewById(R.id.user_status);
            name_agent.setText(status);
        }
        public void setImage(String image, Context ctx) {
            CircleImageView image_agent = mView.findViewById(R.id.profile_image);
            RequestOptions placeHolderRequest = new RequestOptions();
            placeHolderRequest.placeholder(R.drawable.avatar_image);
            Glide.with(ctx).setDefaultRequestOptions(placeHolderRequest).load(image).into(image_agent);
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
}
