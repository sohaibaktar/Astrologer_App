package com.b.chattappmean.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.b.chattappmean.R;
import com.b.chattappmean.databinding.ActivityChoosingBinding;
import com.b.chattappmean.databinding.ActivityMainBinding;

import java.util.Map;
import java.util.Random;

public class ChoosingActivity extends AppCompatActivity {

    private ActivityChoosingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoosingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            String type = bundle.getString("radio_btn");
            Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();

            showdetails(type);
        }

    }

    private void showdetails(String type) {

        if (type.equals("Astrologer")){
            binding.btn.setText(R.string.astrologerbtn);
            binding.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ChoosingActivity.this, MainActivity.class);
                    intent.putExtra("radio_btn1",type);
                    startActivity(intent);
                    finish();

                }
            });
        }


        if (type.equals("Clients")){
            binding.btn.setText(R.string.clientbtn);
            binding.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Random random = new Random();


                    Intent intent = new Intent(ChoosingActivity.this, MainActivity.class);
                    intent.putExtra("radio_btn1",type);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }
}