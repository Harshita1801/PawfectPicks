package com.example.pawfectpicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pawfectpicks.models.MyAdapter;
import com.example.pawfectpicks.models.PetModel;
import com.example.pawfectpicks.models.RecyclerViewItemClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class mypetsActivity extends AppCompatActivity implements RecyclerViewItemClick {

    String Username;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    MyAdapter myAdapter;
    ArrayList<PetModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypets);

        Bundle b1 = getIntent().getExtras();
        Username = b1.getString("userName");

        recyclerView = findViewById(R.id.petList);
        databaseReference = FirebaseDatabase.getInstance().getReference("PetsData");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot s=snapshot.child(Username);
                for(DataSnapshot dataSnapshot : s.getChildren()){
                    PetModel pet=dataSnapshot.getValue(PetModel.class);
                    list.add(pet);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) FloatingActionButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mypetsActivity.this, addPets.class);
                i.putExtra("userName",Username);
                i.putExtra("postedby",Username);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemClick(PetModel petModel) {
        Intent i = new Intent(mypetsActivity.this, MyPetUpdateDelete.class);
        i.putExtra("userName",Username);
        i.putExtra("petPic",petModel.getPetPic());
        i.putExtra("name",petModel.getPname());
        i.putExtra("age",petModel.getPage());
        i.putExtra("type",petModel.getPtype());
        i.putExtra("gender",petModel.getPgender());
//        Toast.makeText(mypetsActivity.this,"Name : "+petModel.getPname(),Toast.LENGTH_SHORT).show();
        startActivity(i);
    }
}