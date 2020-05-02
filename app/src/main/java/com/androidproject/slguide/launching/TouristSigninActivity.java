package com.androidproject.slguide.launching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.slguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TouristSigninActivity extends AppCompatActivity {
    private EditText touristEmail, password, confirmPassword;
    private Button touristSigninBtn;
    private TextView alreadyHaveAcc;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_signin);

        touristEmail = (EditText)findViewById(R.id.touristSigninEmail);
        password = (EditText)findViewById(R.id.touristSigninPassword);
        confirmPassword = (EditText)findViewById(R.id.touristConfirmPassword);
        touristSigninBtn = (Button)findViewById(R.id.touristSigninBtn);
        alreadyHaveAcc = (TextView)findViewById(R.id.touristAlready);

        alreadyHaveAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(TouristSigninActivity.this,TouristLoginActivity.class));
            }
        });

        touristSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.fetchSignInMethodsForEmail(touristEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        boolean check = !task.getResult().getSignInMethods().isEmpty();
                        if (!check) {
                            run();
                        } else {
                            Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }



    public void run() {
        if(validate()){
            progressDialog.setMessage("Please wait");
            progressDialog.show();
            String user_email =touristEmail.getText().toString().trim();
            String user_password = password.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String mail = touristEmail.getText().toString().trim();
                        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                        DatabaseReference databaseReference = firebaseDatabase.getReference();
                        databaseReference.child("Travelers").child(firebaseAuth.getUid()).child("type").setValue("traveler");
                        progressDialog.dismiss();
                        sendemailverification();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(TouristSigninActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private boolean validate() {
        Boolean result = false;
        String password1 = password.getText().toString();
        String password2 = confirmPassword.getText().toString();
        String email = touristEmail.getText().toString();

        if (password1.isEmpty() || password2.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show();
        } else if (!password1.equals(password2)) {
            Toast.makeText(this, "Passwords not matching", Toast.LENGTH_SHORT).show();
        } else if (password1.length() < 6) {
            Toast.makeText(this, "Minimum need 6 characters for password", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }

        return result;


    }

    private void sendemailverification(){
        FirebaseUser firebaseUser  = firebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(TouristSigninActivity.this, "Successfully registerd, Verification mail sent!", Toast.LENGTH_LONG).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(TouristSigninActivity.this,TouristLoginActivity.class));
                    }else {
                        Toast.makeText(TouristSigninActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
