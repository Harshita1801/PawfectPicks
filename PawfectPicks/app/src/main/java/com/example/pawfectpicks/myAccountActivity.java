package com.example.pawfectpicks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class myAccountActivity extends AppCompatActivity {
    EditText username, email, password ;
    FirebaseDatabase database;
    DatabaseReference reference;
    Button btnSignout,btnUpdate;
    String Username1, email1, password1;
    String Username_input, email_input, password_input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Bundle b1 = getIntent().getExtras();
        Username1 = b1.getString("userName");

        username=findViewById(R.id.displayUsername);
        email=findViewById(R.id.displayEmail);
        password=findViewById(R.id.displayPassword);
        btnSignout=findViewById(R.id.signout);
        btnUpdate=findViewById(R.id.btnupdate);

        Username_input = username.getText().toString();
        password_input = password.getText().toString();
        email_input = email.getText().toString();

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("UserData");

        Query check_username=reference.orderByChild("username").equalTo(Username1);
        check_username.addListenerForSingleValueEvent(new ValueEventListener(){

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    password1 = snapshot.child(Username1).child("password").getValue(String.class);
                    email1 = snapshot.child(Username1).child("email").getValue(String.class);
                    username.setText(Username1);
                    email.setText(email1);
                    password.setText(password1);
                }
                else
                {
                    Toast.makeText(myAccountActivity.this,"User doesn't exists",Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Username1.equals(Username_input))
                {
                    Toast.makeText(myAccountActivity.this,"Username cannot be changed",Toast.LENGTH_SHORT).show();
                }
                if(emailchange() || passwordchange() )
                {
                    Toast.makeText(myAccountActivity.this, "Data Updated successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(myAccountActivity.this, "No changes made",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(myAccountActivity.this, login.class));

            }
        });

    }

    private boolean passwordchange() {
        if(!password1.equals(password_input)){
            reference.child(Username1).child("password").setValue(password.getText().toString());
            return true;
        }
        else
            return false;
    }

    private boolean emailchange() {
        if(!email1.equals(email_input)){
            reference.child(Username1).child("email").setValue(email.getText().toString());
            return true;
        }
        else
            return false;
    }
}