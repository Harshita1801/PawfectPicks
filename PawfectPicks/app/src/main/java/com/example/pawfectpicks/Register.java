package com.example.pawfectpicks;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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

public class Register extends AppCompatActivity {
    TextView btn, btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;
    EditText username, email,password,cpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        username=findViewById(R.id.inputUserName);
        email=findViewById(R.id.inputEmail);
        password=findViewById(R.id.inputPassword);
        cpassword=findViewById(R.id.inputConfirmPassword);
        btn=findViewById(R.id.alreadyhaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, login.class));
            }
        });
        btnRegister=findViewById(R.id.btnLogin);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser()
    {
        String Username = username.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        if(TextUtils.isEmpty(Username)){
            Toast.makeText(this,"Username is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
        if(TextUtils.isEmpty(Email) ){
            Toast.makeText(this,"Email address is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
            Toast.makeText(this,"Invalid email address",Toast.LENGTH_SHORT).show();
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

        String Username_S = username.getText().toString();
        String Email_S = email.getText().toString();
        String Password_S = password.getText().toString();

        Query check_username=reference.orderByChild("username").equalTo(Username_S);
        check_username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(Register.this,"Username already exists",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    UserModel usermodel=new UserModel(Username_S,Email_S,Password_S);
                    reference.child(Username_S).setValue(usermodel);
                    Toast.makeText(Register.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Register.this, HomeActivity.class);
                    i.putExtra("userName",Username);
                    i.putExtra("email",Email_S);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}