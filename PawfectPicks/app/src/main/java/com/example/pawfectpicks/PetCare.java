package com.example.pawfectpicks;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PetCare extends AppCompatActivity {
    ImageView dogN,catN,birdN,pettrain,emerC;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_care);
        dogN=findViewById(R.id.dogNutrition);
        catN=findViewById(R.id.catNutrition);
        birdN=findViewById(R.id.birdNutrition);
        pettrain=findViewById(R.id.petTraining);
        emerC=findViewById(R.id.Emergency);

        dogN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.webmd.com/pets/dogs/dog-nutrition";
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        catN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://vcahospitals.com/know-your-pet/nutrition-feeding-guidelines-for-cats";
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        birdN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.avianandanimal.com/bird-nutrition.html#:~:text=%2D%20All%20birds%20have%20a%20protein,(%20in%20very%20small%20amounts).";
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        pettrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.petplate.com/blog/10-tips-for-training-a-dog-most-important-dog-training-rules/";
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        emerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://www.justdial.com/Bangalore/Veterinary-Hospitals/nct-10519292";
                Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

    }
}