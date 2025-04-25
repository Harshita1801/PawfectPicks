package com.example.pawfectpicks;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pawfectpicks.models.PetModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class addPets extends AppCompatActivity {

    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;

    Uri selectedImageUri;
    ImageView petPicture;
    EditText pname, pgender, page;
    Spinner s_ptype;
    RadioButton male,female;
    ActivityResultLauncher<Intent> imagePickLauncher;
    String Username1,postedby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);

        storageReference= FirebaseStorage.getInstance().getReference();

        petPicture = findViewById(R.id.petPic);
        pname=findViewById(R.id.inputName);
        page=findViewById(R.id.inputAge);
        s_ptype=findViewById(R.id.spinner_type);
        male=findViewById(R.id.radioButton);
        female=findViewById(R.id.radioButton2);

        Bundle b1 = getIntent().getExtras();
        Username1 = b1.getString("userName");
        postedby = b1.getString("postedby");

        Button btnadd = findViewById(R.id.btndelete);
        imagePickLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result->{
                        if(result.getResultCode()== Activity.RESULT_OK){
                            Intent data = result.getData();
                            if(data!=null && data.getData()!=null){
                                selectedImageUri =data.getData();
                                Glide.with(this).load(selectedImageUri).into(petPicture);
                            }
                        }
                        else
                            Toast.makeText(addPets.this,"Please select an image",Toast.LENGTH_SHORT).show();
                });
        petPicture.setOnClickListener((v)-> {
            ImagePicker.with(this).compress(512).maxResultSize(512, 512)
                    .createIntent(new Function1<Intent, Unit>() {
                        @Override
                        public Unit invoke(Intent intent) {
                            imagePickLauncher.launch(intent);
                            return null;
                        }
                    });
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // uploadImage(selectedImageUri);
                addMyPets(selectedImageUri);
            }
        });
    }
    private void addMyPets(Uri image)
    {
        String Username=Username1;
        String pname_1=pname.getText().toString();
        String page_1=page.getText().toString();
        String pgender_1="";
        String ptype_1="";

        if(male.isChecked())
            pgender_1="male";
        else if(female.isChecked())
            pgender_1="female";
        else
            Toast.makeText(addPets.this, "Choose any", Toast.LENGTH_SHORT).show();

        int x=s_ptype.getSelectedItemPosition();
        if(x==1)
            ptype_1="Dog";
        else if(x==2)
            ptype_1="Cat";
        else if(x==3)
            ptype_1="Bird";
        else if(x==4)
            ptype_1="Rabbit";
        else
            Toast.makeText(addPets.this, "Choose any", Toast.LENGTH_SHORT).show();



        if(TextUtils.isEmpty(pname_1)){
            Toast.makeText(this,"Pet name is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
        if(TextUtils.isEmpty(page_1)){
            Toast.makeText(this,"Pet age is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
        if(TextUtils.isEmpty(pgender_1)){
            Toast.makeText(this,"Pet gender is Empty",Toast.LENGTH_SHORT).show();;
            return;
        }
//        if(TextUtils.isEmpty(ptype_1)){
//            Toast.makeText(this,"Pet type is Empty",Toast.LENGTH_SHORT).show();;
//            return;
//        }

        if(image!=null)
        {
            uploadToFirebase(image,Username,pname_1,page_1,ptype_1,pgender_1);
        }
        else
        {
            Toast.makeText(addPets.this,"Please select Image",Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri muri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(muri));
    }

    private void uploadToFirebase(Uri uri,String username, String pname_1, String page_1, String ptype_1, String pgender_1)
    {
         StorageReference fileRef = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
         fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
             @Override
             public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                     @Override
                     public void onSuccess(Uri uri) {
                         database=FirebaseDatabase.getInstance();
                         reference=database.getReference("PetsData");
                         PetModel petModel=new PetModel(uri.toString(),username, pname_1,page_1,ptype_1,pgender_1,postedby);
//                         if(username.equals("NOUSER"))
//                             adopt="No";
//                         else
//                             adopt="Yes";

                         reference.child(username).child(pname_1).setValue(petModel);

                         Toast.makeText(addPets.this,"Uploaded successfully",Toast.LENGTH_SHORT).show();
                         Intent i = new Intent(addPets.this,HomeActivity.class);
                         i.putExtra("userName",postedby);
                         startActivity(i);

                     }
                 });
             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(addPets.this,"Image not uploaded",Toast.LENGTH_SHORT).show();
             }
         });
    }

}