package com.appentus.claptone.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.appentus.claptone.LayMain;
import com.appentus.claptone.R;

public class ActivitySignIn extends AppCompatActivity {

    TextView tvLoginForgotPswd;
    ImageView btnLogIn,backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        tvLoginForgotPswd=(TextView)findViewById(R.id.tvLoginForgotPswd);
        btnLogIn=(ImageView) findViewById(R.id.btnLogIn);
        backBtn=(ImageView) findViewById(R.id.backBtn);

        tvLoginForgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivitySignIn.this,ActivityForgotPassword.class);
                startActivity(i);
            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ActivitySignIn.this,ActivityHome.class);
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
