package com.example.sakethkatari.bloodbank;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Startup extends AppCompatActivity
{
    private Button buttonSignup;
    private Button buttonSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        customizeActionBar();
        customizeStatusBar();
        initializeViews();

        buttonSignup.setOnClickListener(new View.OnClickListener()
        {
            private Intent intentSignup;

            @Override
            public void onClick(View v)
            {
                intentSignup = new Intent( Startup.this, Signup.class );
                startActivity(intentSignup);
            }
        });

        buttonSignin.setOnClickListener(new View.OnClickListener()
        {
            private Intent intentSignin;

            @Override
            public void onClick(View v)
            {
                intentSignin = new Intent( Startup.this, Login.class );
                startActivity(intentSignin);
            }
        });
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(Startup.this, R.color.blood) );
        }
    }

    private void initializeViews()
    {
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignin = (Button) findViewById(R.id.buttonLogin);
    }
}
