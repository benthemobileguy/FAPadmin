package com.example.bnotion.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class UserDataActivity extends AppCompatActivity {
private TextView mName, mEmail, mPhone, mToolbarText;
private String name, email, phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mName = findViewById(R.id.user_name);
        mEmail = findViewById(R.id.email);
        mPhone = findViewById(R.id.phone);
        mToolbarText = findViewById(R.id.users_text);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        email = intent.getStringExtra("email");
        phone = intent.getStringExtra("phone");
        mName.setText(name);
        mEmail.setText(email);
        mPhone.setText(phone);
        mToolbarText.setText(name);
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
