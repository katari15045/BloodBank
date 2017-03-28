package com.example.root.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity
{
    private Button buttonSignup;
    private Button buttonSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeViews();

        buttonSignup.setOnClickListener(new View.OnClickListener()
        {
            private Intent intentSignup;

            @Override
            public void onClick(View v)
            {
                intentSignup = new Intent( Home.this, Signup.class );
                startActivity(intentSignup);
            }
        });

        buttonSignin.setOnClickListener(new View.OnClickListener()
        {
            private Intent intentSignin;

            @Override
            public void onClick(View v)
            {
                intentSignin = new Intent( Home.this, Login.class );
                startActivity(intentSignin);
            }
        });
    }

    private void initializeViews()
    {
        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
    }
}
