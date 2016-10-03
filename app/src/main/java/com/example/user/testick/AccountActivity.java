package com.example.user.testick;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class AccountActivity extends AppCompatActivity {

    private RecyclerView mProfile;

    private DatabaseReference mDatabase;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private Button mLogOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Profiles");

        mProfile = (RecyclerView) findViewById(R.id.profile);
        mProfile.setHasFixedSize(true);
        mProfile.setLayoutManager(new LinearLayoutManager(this));

        mAuth = FirebaseAuth.getInstance();

        mLogOutBtn = (Button) findViewById(R.id.logOutBtn);

        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.signOut();
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Profile, ProfileViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Profile, ProfileViewHolder>(

                Profile.class,
                R.layout.profile_row,
                ProfileViewHolder.class,
                mDatabase

        ) {
            @Override
            protected void populateViewHolder(ProfileViewHolder viewHolder, Profile model, int position) {

                viewHolder.setName(model.getName());
                viewHolder.setProfession(model.getProfession());
                viewHolder.setAboutYourselfField(model.getAbout());
                viewHolder.setProfileImage(getApplicationContext(), model.getImage());
                //viewHolder.setUsername(model.getUsername());

            }
        };

        mProfile.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProfileViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ProfileViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setName(String name) {

            TextView nameField = (TextView) mView.findViewById(R.id.nameField);
            nameField.setText(name);
        }

        public void setProfession(String profession) {

            TextView professionField = (TextView) mView.findViewById(R.id.professionField);
            professionField.setText(profession);
        }

        public void setAboutYourselfField(String about) {

            TextView aboutYourselfField = (TextView) mView.findViewById(R.id.aboutYourselfField);
            aboutYourselfField.setText(about);
        }

        /*public void setUsername(String username) {

            TextView usernameField = (TextView) mView.findViewById(R.id.usernameField);
            usernameField.setText(username);
        }*/

        public void setProfileImage(Context ctx, String image) {

            ImageView profileImage = (ImageView) mView.findViewById(R.id.profileImage);
            Picasso.with(ctx).load(image).into(profileImage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.account_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {

            startActivity(new Intent(AccountActivity.this, AddProfileActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
