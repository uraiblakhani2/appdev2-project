package com.example.healthpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class EditProfile extends AppCompatActivity {

    EditText editName, editEmail, editUsername, editPassword;
    ImageView imageView;
    private FirebaseStorage storage;
    private FirebaseStorage storage2;
    private StorageReference storageReference;
    private StorageReference storageReference2;


    public Uri imageUri;
    Button update;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://healthpal-1905e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editName = findViewById(R.id.editName);

        editEmail = findViewById(R.id.editEmail);
        editUsername = findViewById(R.id.editUsername);
        imageView = findViewById(R.id.imageView);
        update = findViewById(R.id.update);
        String uri = "@drawable/user";  // where myresource (without the extension) is the file

        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        Drawable res = getResources().getDrawable(imageResource);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference2 = storage.getReference();

        editPassword = findViewById(R.id.editPassword);

        String userids = getIntent().getStringExtra("user");




        storageReference2 = FirebaseStorage.getInstance().getReference("images/" + userids);
        try {
            File localfile = File.createTempFile("tempfile", "");
            storageReference2.getFile(localfile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                            imageView.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            imageView.setImageDrawable(res);

                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String getPassword = snapshot.child(userids).child("password").getValue(String.class);
                final String getName = snapshot.child(userids).child("fullname").getValue(String.class);
                final String getEmail = snapshot.child(userids).child("email").getValue(String.class);
                editPassword.setText(getPassword);
                editEmail.setText(getEmail);
                editName.setText(getName);
                editUsername.setText(userids);

                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choosePicture();

                
            }
        });
        
        
        
        
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("users").child(userids).child("fullname").setValue(editName.getText().toString());
                        editName.setText(editName.getText().toString());
                        //databaseReference.child("users").child(userids).setValue(editUsername.getText().toString());
                        databaseReference.child("users").child(userids).child("email").setValue(editEmail.getText().toString());
                        databaseReference.child("users").child(userids).child("password").setValue(editPassword.getText().toString());
                        Toast.makeText(EditProfile.this, "User Profile updated successfully", Toast.LENGTH_SHORT).show();

                        final String getPassword = snapshot.child(userids).child("password").getValue(String.class);
                        final String getName = snapshot.child(userids).child("fullname").getValue(String.class);
                        final String getEmail = snapshot.child(userids).child("email").getValue(String.class);
                        editPassword.setText(editPassword.getText().toString());
                        editEmail.setText(editEmail.getText().toString());
                        editName.setText(editName.getText().toString());
                        editUsername.setText(editUsername.getText().toString());


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            uploadPicture();

        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image please be patient ...");
        pd.show();
        final String user = getIntent().getStringExtra("user");;
        StorageReference riversRef = storageReference.child("images/"+user);
        riversRef.putFile(imageUri)

        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                    }
                }).
        addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), "Failed to upload", Toast.LENGTH_LONG).show();


            }
        })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        double progressPercent = (100.00 * snapshot.getBytesTransferred()/ snapshot.getTotalByteCount());
                        pd.setMessage("Progress: " + (int) progressPercent + "%");
                    }
                });

    }
}