package com.example.pawfectpicks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyPetUpdateDelete extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ImageView pic;
    EditText name,age,type,gender;
    String ppic,pname,page,ptype,pgender,Username;
    String ppic_i,page_i,ptype_i,pgender_i;
    Button update,delete;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pet_update_delete);
        Bundle b1 = getIntent().getExtras();
        Username = b1.getString("userName");
        ppic=b1.getString("petPic");
        pname=b1.getString("name");
        page=b1.getString("age");
        ptype=b1.getString("type");
        pgender=b1.getString("gender");

        pic=findViewById(R.id.petPic);
        name=findViewById(R.id.inputName);
        age=findViewById(R.id.inputAge);
        type=findViewById(R.id.inputType);
        gender=findViewById(R.id.inputGender);
        update=findViewById(R.id.btnupdate);
        delete=findViewById(R.id.btndelete);

        Picasso.get().load(ppic).into(pic);
        name.setText(pname);
        age.setText(page);
        type.setText(ptype);
        gender.setText(pgender);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("PetsData");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( agechange() || typechange() || genderchange() )
                {
                    Toast.makeText(MyPetUpdateDelete.this, "Data Updated successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MyPetUpdateDelete.this, "No changes made",Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(MyPetUpdateDelete.this,HomeActivity.class);
                i.putExtra("userName",Username);
                startActivity(i);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref=database.getReference("PetsData").child(Username).child(pname);

                Task<Void> mtask = ref.removeValue();
                mtask.addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MyPetUpdateDelete.this,"Pet deleted successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(MyPetUpdateDelete.this, HomeActivity.class);
                        i.putExtra("userName",Username);
                        startActivity(i);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MyPetUpdateDelete.this,"Unsuccessfull deletetion",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    private boolean agechange() {
        page_i=age.getText().toString();
        if(!page_i.equals(page)){
            reference.child(Username).child(pname).child("page").setValue(page_i);
            return true;
        }
        else
            return false;
    }
    private boolean typechange() {
        ptype_i=type.getText().toString();
        if(!ptype_i.equals(ptype)){
            reference.child(Username).child(pname).child("ptype").setValue(ptype_i);
            return true;
        }
        else
            return false;
    }
    private boolean genderchange() {
        pgender_i=gender.getText().toString();
        if(!pgender_i.equals(pgender)){
            reference.child(Username).child(pname).child("pgender").setValue(pgender_i);
            return true;
        }
        else
            return false;
    }
}