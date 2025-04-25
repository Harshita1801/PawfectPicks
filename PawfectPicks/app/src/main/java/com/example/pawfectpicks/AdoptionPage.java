package com.example.pawfectpicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pawfectpicks.models.PetModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AdoptionPage extends AppCompatActivity {

    AlertDialog.Builder builder;

    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView pic;
    EditText name,age,type,gender,postedby;
    String ppic,pname,page,ptype,pgender,Username,ppostedby;
    String ppic_i,page_i,ptype_i,pgender_i;
    Button adopt;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adoption_page);

        Bundle b1 = getIntent().getExtras();
        Username = b1.getString("userName");
        ppic=b1.getString("petPic");
        pname=b1.getString("name");
        page=b1.getString("age");
        ptype=b1.getString("type");
        pgender=b1.getString("gender");
        ppostedby=b1.getString("postedby");

        builder = new AlertDialog.Builder(this);

        pic=findViewById(R.id.petPic);
        name=findViewById(R.id.inputName);
        age=findViewById(R.id.inputAge);
        type=findViewById(R.id.inputType);
        gender=findViewById(R.id.inputGender);
        postedby=findViewById(R.id.inputPostedby);
        adopt=findViewById(R.id.btnadopt);

        Picasso.get().load(ppic).into(pic);
        name.setText(pname);
        age.setText(page);
        type.setText(ptype);
        gender.setText(pgender);
        postedby.setText(ppostedby);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("PetsData");

        adopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetModel petModel=new PetModel(ppic,Username, pname,page,ptype,pgender,ppostedby);

                reference.child(Username).child(pname).setValue(petModel);
                DatabaseReference ref=database.getReference("PetsData").child("NOUSER").child(pname);

                Task<Void> mtask = ref.removeValue();
                mtask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AdoptionPage.this,"Pet adopted successfully",Toast.LENGTH_SHORT).show();

                        builder.setTitle("Congratulations!!!")
                                .setMessage("Thank you for adoting the pet,")
                                .setCancelable(true)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent i=new Intent(AdoptionPage.this, HomeActivity.class);
                                        i.putExtra("userName",Username);
                                        startActivity(i);
                                        finish();
                                    }
                                }).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdoptionPage.this,"Unsuccessfull pet apdotion",Toast.LENGTH_SHORT).show();
                    }
                });            }
        });
    }
}