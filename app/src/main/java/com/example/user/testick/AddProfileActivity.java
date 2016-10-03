package com.example.user.testick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddProfileActivity extends AppCompatActivity {

    private ImageButton mSelectImage;

    private EditText mNameField;
    private EditText mProfessionField;
    private EditText mAboutYourselfField;

    private Button mSubmitBtn;

    private Uri mImageUri = null;

    private StorageReference mStorage;

    private DatabaseReference mDatabase;

    private DatabaseReference mDatabaseUser;

    private FirebaseAuth mAuth;

    //private FirebaseUser mCurrentUser;

    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile);

        mAuth = FirebaseAuth.getInstance();

      //  mCurrentUser = mAuth.getCurrentUser();

        //mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profiles");

        mProgress = new ProgressDialog(this);

        mSelectImage = (ImageButton) findViewById(R.id.imageBtn);

        mNameField = (EditText) findViewById(R.id.nameField);
        mProfessionField = (EditText) findViewById(R.id.professionField);
        mAboutYourselfField = (EditText) findViewById(R.id.aboutYourselfField);

        mSubmitBtn = (Button) findViewById(R.id.submitBtn);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);

            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startAddingProfile();
            }
        });
    }

    private void startAddingProfile() {

        mProgress.setMessage("Profile is in progress...");
        mProgress.show();

        final String name = mNameField.getText().toString().trim();
        final String profession = mProfessionField.getText().toString().trim();
        final String about = mAboutYourselfField.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(profession) && !TextUtils.isEmpty(about) && mImageUri != null) {

            mProgress.show();

            StorageReference filepath = mStorage.child("Profile Photo").child(mImageUri.getLastPathSegment());

            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Uri downloadUri = taskSnapshot.getDownloadUrl();

                    final DatabaseReference newProfile = mDatabase.push();


                    mDatabaseUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            newProfile.child("name").setValue(name);
                            newProfile.child("profession").setValue(profession);
                            newProfile.child("about").setValue(about);
                            newProfile.child("image").setValue(downloadUri.toString());
        //                    newProfile.child("uid").setValue(mCurrentUser.getUid());
          /*                  newProfile.child("username").setValue(dataSnapshot.child("name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        startActivity(new Intent(AddProfileActivity.this, AccountActivity.class));
                                    }

                                }
                            });
*/
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {


                        }
                    });

                    mProgress.dismiss();

                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {


            mImageUri = data.getData();

            mSelectImage.setImageURI(mImageUri);

        }
    }
}
