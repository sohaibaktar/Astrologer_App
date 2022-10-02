package com.b.chattappmean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.b.chattappmean.R;
import com.b.chattappmean.User;
import com.b.chattappmean.adapters.UsersAdapter;
import com.b.chattappmean.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UsersAdapter usersAdapter;
    FirebaseAuth mAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();

        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        //recylerview
        usersAdapter = new UsersAdapter(this, users);
        binding.recyclerView.setAdapter(usersAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));


                //fetch data from database
                database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                users.clear();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                        User user = snapshot1.getValue(User.class);
                                        if(!user.getUid().equals(FirebaseAuth.getInstance().getUid()))
                                                users.add(user);
                                       
                                }
                                usersAdapter.notifyDataSetChanged();
                        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("TAG", "onCancelled: "+error.toString());
            }
        });

    }


        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                        case R.id.search:
                                Toast.makeText(this,"Search Clicked",Toast.LENGTH_SHORT).show();
                                break;
                        case R.id.group:
                                Toast.makeText(this,"Groups Clicked",Toast.LENGTH_SHORT).show();
                                break;
                        case R.id.invite:
                                Toast.makeText(this,"Invite Clicked",Toast.LENGTH_SHORT).show();
                                break;
                        case R.id.settings:
                                Toast.makeText(this,"Settings Clicked",Toast.LENGTH_SHORT).show();
                                break;
                }
                return super.onOptionsItemSelected(item);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.topmenu,menu);
                return super.onCreateOptionsMenu(menu);
        }
}
