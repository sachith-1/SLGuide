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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuideLoginActivity extends AppCompatActivity {
    private EditText email,password;
    private Button guideLoginBtn;
    private TextView forgotPassword,donthaveacc;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_login);

        email = (EditText)findViewById(R.id.editText4);
        password = (EditText)findViewById(R.id.editText2);
        guideLoginBtn = (Button)findViewById(R.id.btnlogin2);
        forgotPassword = (TextView)findViewById(R.id.textView3);
        donthaveacc = (TextView)findViewById(R.id.textView4);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideLoginActivity.this,ForgotPasswordActivity.class));
            }
        });

        donthaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(GuideLoginActivity.this, GuideSigninActivity.class));
            }
        });

        guideLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(email.getText().toString(),password.getText().toString());
            }
        });



    }

    private void validate(String user_email,String user_password) {
        if (user_email.isEmpty() || user_password.isEmpty()) {
            Toast.makeText(GuideLoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setMessage("Validating");
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        //Toast.makeText(TravelerLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        checkEmailVerification();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(GuideLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void checkEmailVerification(){
        FirebaseUser firebaseUser = firebaseAuth.getInstance().getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();
        if(emailflag){
            checkTravelerData();
        }else{
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }

    private void checkTravelerData(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("Guides").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GuideProfile guideProfile = dataSnapshot.getValue(GuideProfile.class);
                if(guideProfile != null) {
                    if (guideProfile.getFullName() == null) {
                        Toast.makeText(GuideLoginActivity.this, "Please complete your profile", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(GuideLoginActivity.this, GuideCompleteProfileActivity.class));
                    } else {
                        finish();
                        startActivity(new Intent(GuideLoginActivity.this, GuideHomeActivity.class));
                    }
                }else{
                    firebaseAuth.signOut();
                    Toast.makeText(GuideLoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
