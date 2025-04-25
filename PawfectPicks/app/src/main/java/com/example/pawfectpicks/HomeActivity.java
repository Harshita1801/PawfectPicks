package com.example.pawfectpicks;

import android.annotation.SuppressLint;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle b1 = getIntent().getExtras();
        String Username = b1.getString("userName");
        String Email=b1.getString("email");
        Toast.makeText(HomeActivity.this,"Welcome "+Username,Toast.LENGTH_SHORT).show();


        ImageView btnAccount = findViewById(R.id.myAccountImage);
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this, myAccountActivity.class);
                i.putExtra("userName",Username);
                startActivity(i);
            }
        });
        ImageView btnpets = findViewById(R.id.myPetsImage);
        btnpets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this, mypetsActivity.class);
                i.putExtra("userName",Username);
                startActivity(i);
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView btnshop= findViewById(R.id.petCareImage);
        btnshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, PetCare.class));
            }
        });

        ImageView btnadopt = findViewById(R.id.adoption);
        btnadopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this, adoptPet.class);
                i.putExtra("username",Username);
                i.putExtra("email",Email);
                i.putExtra("defaultUsername","NOUSER");
                startActivity(i);
            }
        });
    }
}