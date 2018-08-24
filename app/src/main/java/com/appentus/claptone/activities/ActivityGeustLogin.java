package com.appentus.claptone.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.claptone.R;

public class ActivityGeustLogin extends AppCompatActivity {
    TextInputLayout usernameWrapper,emailWrapper,pswdWrapper,cnfPswdWrapper;
    TextView loginTv;
    ImageView   backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geust_login);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/futura_heavy_font.ttf");
      usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
      emailWrapper= (TextInputLayout) findViewById(R.id.emailWrapper);
      pswdWrapper= (TextInputLayout) findViewById(R.id.pswdWrapper);
      cnfPswdWrapper= (TextInputLayout) findViewById(R.id.cnfPswdWrapper);
        usernameWrapper.setHint("Name");
        usernameWrapper.setTypeface(typeface);
        emailWrapper.setHint("Email");
        emailWrapper.setTypeface(typeface);
        pswdWrapper.setHint("Password");
        pswdWrapper.setTypeface(typeface);
        cnfPswdWrapper.setHint("Confirm Password");
        cnfPswdWrapper.setTypeface(typeface);;

        loginTv= (TextView) findViewById(R.id.tvLogin);
        loginTv.setTypeface(typeface);
        backBtn=(ImageView) findViewById(R.id.backBtn);
        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivityGeustLogin.this,ActivitySignIn.class);
                startActivity(i);
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
