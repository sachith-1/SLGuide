package com.androidproject.slguide.launching;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.androidproject.slguide.MainActivity;
import com.androidproject.slguide.R;

public class FirstActivity extends AppCompatActivity {
    private ImageView imageTourist,imageGuide;
    private Button buttonTourist,buttonGuide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        imageTourist = (ImageView)findViewById(R.id.ivTourist);
        imageGuide = (ImageView)findViewById(R.id.ivGuide);
        buttonTourist = (Button)findViewById(R.id.btnTourist);
        buttonGuide = (Button)findViewById(R.id.btnGuide);

        imageTourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touristloginopen();
            }
        });

        buttonTourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                touristloginopen();
            }
        });

        imageGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideloginopen();
            }
        });

        buttonGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideloginopen();
            }
        });

    }


    private void touristloginopen(){
        //finish();
        startActivity(new Intent(FirstActivity.this, TouristLoginActivity.class));
    }

    private void guideloginopen(){
        //finish();
        startActivity(new Intent(FirstActivity.this, GuideLoginActivity.class));
    }
}
