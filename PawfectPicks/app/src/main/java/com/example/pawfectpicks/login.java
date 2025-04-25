

package com.example.pawfectpicks;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pawfectpicks.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    TextView btn,btnLogin;
    EditText username, password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();
        username=findViewById(R.id.inputUserName);
        password=findViewById(R.id.inputPassword);
        btn=findViewById(R.id.signUp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, Register.class));
            }
        });
        btnLogin=findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }
    private void loginUser(){
        String Username = username.getText().toString();
        String Password = password.getText().toString();
        if(TextUtils.isEmpty(Username)){
            Toast.makeText(this,"Username is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
        if(TextUtils.isEmpty(Password)){
            Toast.makeText(this,"Password is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
        if(Password.length()<6)
        {
            Toast.makeText(this,"Password length must be greater than 6 letter",Toast.LENGTH_SHORT).show();
            return;
        }

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("UserData");

        Query check_username=reference.orderByChild("username").equalTo(Username);
        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    String passwordCheck = snapshot.child(Username).child("password").getValue(String.class);
                    String email=snapshot.child(Username).child("email").getValue(String.class);
                    if(passwordCheck.equals(Password))
                    {
                        Toast.makeText(login.this,"Login Successfully",Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(login.this, HomeActivity.class);
                        i.putExtra("userName",Username);
                        i.putExtra("email",email);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(login.this,"Password wrong",Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                else
                {
                    Toast.makeText(login.this,"User doesn't exists",Toast.LENGTH_LONG).show();
                    return;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}