package com.b.chattappmean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

        private ActivityMainBinding binding;
        FirebaseDatabase database;
        ArrayList<User> users;
        UsersAdapter usersAdapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                binding = ActivityMainBinding.inflate(getLayoutInflater());
                setContentView(binding.getRoot());

                database = FirebaseDatabase.getInstance();
                users = new ArrayList<>();

                usersAdapter = new UsersAdapter(this, users);
                binding.recyclerView.setAdapter(usersAdapter);

                String you_are = getIntent().getStringExtra("radio_btn1");
                Toast.makeText(getApplicationContext(),you_are,Toast.LENGTH_SHORT).show();

                if (you_are.equals("Astrologer")){
                        database.getReference().child("Profiles").child("Clients").addValueEventListener(new ValueEventListener() {
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

                                }
                        });
                }
                if (you_are.equals("Clients")){
                        database.getReference().child("Profiles").child("Astrologer").addValueEventListener(new ValueEventListener() {
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

                                }
                        });
                }
                //fetch data from database


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
