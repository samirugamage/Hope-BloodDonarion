package com.maverickz.mad_project_hope;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class NewPostActivity extends AppCompatActivity {

    private TextInputEditText postHeading, postDescription;
    private Button submitbtn,backbtn;
    private ImageView postimage;
    private Uri imageUri;
//    private ProgressBar progressBar;
    private String modelId;

    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_NAME = "name";

    private DatabaseReference userDatabaseRef;
    private StorageReference imgreference = FirebaseStorage.getInstance().getReference();

    Post post;

    public NewPostActivity() {
    }


//    -----------------------------------

//    -----------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);



        postHeading = findViewById(R.id.newpostheading);
        postDescription = findViewById(R.id.newpostdescription);
        submitbtn = findViewById(R.id.submitnewpost);
        backbtn = findViewById(R.id.backnewpost);
        postimage = findViewById(R.id.postimage);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        final String id = sharedPreferences.getString(KEY_NAME,null);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Post_Wall_New_Activity.class);
                startActivity(i);
            }
        });

//        progressBar.setVisibility(View.INVISIBLE);
        userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Post");
        post = new Post();

        postimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(TextUtils.isEmpty(postHeading.getText().toString())){
                    postHeading.setError("Enter a Name");
                    return;
                } if(TextUtils.isEmpty(postDescription.getText().toString())){
                    postDescription.setError("Enter a Name");
                    return;
                }else {

                    post.setHeading(postHeading.getText().toString().trim());
                    post.setDescription(postDescription.getText().toString().trim());
                    post.setID("");
                    post.setuid(id);

//                String modelId = userDatabaseRef.push().getKey();
//                userDatabaseRef.child(modelId).setValue(post);
//                String modelId = userDatabaseRef.push().getKey();

                    uploadToFirebase(imageUri);
                    Toast.makeText(NewPostActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            postimage.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){

        StorageReference fileRef = imgreference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
//                        Post post = new Post(uri.toString());
                        post.setImageUri(uri.toString());
//                        String modelId = userDatabaseRef.push().getKey();
                        userDatabaseRef.push().setValue(post);
//                        progressBar.setVisibility(View.INVISIBLE);

                        Toast.makeText(NewPostActivity.this,"Uploaded Successfully",Toast.LENGTH_LONG).show();
                    }
                });

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(NewPostActivity.this, "Uploading Failed!!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
}