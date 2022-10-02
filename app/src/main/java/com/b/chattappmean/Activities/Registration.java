package com.b.chattappmean.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.b.chattappmean.R;
import com.b.chattappmean.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Registration extends AppCompatActivity implements View.OnClickListener {

    private EditText user_Email , user_password , user_name;
    private TextView signIn_tv;
    private Button reg_btn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //firebase initialization
        mAuth = FirebaseAuth.getInstance();

        // initialization
        user_Email = (EditText) findViewById(R.id.regmailid);
        user_password = (EditText) findViewById(R.id.regpassid);
        user_name = (EditText) findViewById(R.id.reg_fullName);
        signIn_tv = (TextView) findViewById(R.id.signIn_tvId);
        reg_btn = (Button) findViewById(R.id.reg_btnId);
        //set clickable
        signIn_tv.setOnClickListener(this);
        reg_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.reg_btnId:
                userRegistration();
                break;

            case R.id.signIn_tvId:
                Intent intent = new Intent(Registration.this, PhoneNumberActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userRegistration() {
        String email = user_Email.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        String name = user_name.getText().toString().trim();

        //Checking the validity of the email.
        if(email.isEmpty()){
            user_Email.setError("Enter an email Address");
            user_password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            user_Email.setError("Enter a valid Email address");
            user_Email.requestFocus();
            return;
        }
        //Checking the validity of the password.
        if(password.isEmpty()){
            user_password.setError("Enter a password");
            user_password.requestFocus();
            return;
        }
        if(password.length()<6){
            user_password.setError("Minimum lengh of a password should be 6");
            user_password.requestFocus();
            return;
        }
        //Checking name is empty or not
        if(name.isEmpty()){
            user_name.setError("Enter an full Name");
            user_name.requestFocus();
            return;
        }

        //create registering using email and phone.
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //reg_progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    //Toast.makeText(getApplicationContext(),"User Registration is succesfull",Toast.LENGTH_SHORT).show();
                    //this user value save in database
                    User user = new User(name,email);
                    // save this into realtime database
                    FirebaseDatabase.getInstance().getReference("users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        Toast.makeText(getApplicationContext(),"User Registration is succesfull",Toast.LENGTH_SHORT).show();
                                        //reg_progressBar.setVisibility(View.GONE);

                                        //after succesfull registration go to login page.
                                        Intent intent = new Intent(Registration.this, PhoneNumberActivity.class);
                                        startActivity(intent);
                                        finish();


                                    }else{
                                        Toast.makeText(getApplicationContext(),"User Registration is Failed",Toast.LENGTH_SHORT).show();
                                        //reg_progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                }
                else{
                    Toast.makeText(getApplicationContext(),"Registration is not Succesfull"+task.getException(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}