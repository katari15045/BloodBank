package com.example.root.home;

/**
 * Created by root on 28/3/17.
 */


import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity
{
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignIn;

    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        customizeActionBar();
        customizeStatusBar();
        initializeViews();
        buttonSignIn.setOnClickListener( new MyOnClickListener() );
    }

    private void customizeActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable( new ColorDrawable(ContextCompat.getColor(Login.this, R.color.blood)) );
        actionBar.setTitle("Login");
    }

    private void customizeStatusBar()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor( ContextCompat.getColor(Login.this, R.color.blood) );
        }
    }

    private void initializeViews()
    {
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
    }

    private class MyOnClickListener implements View.OnClickListener
    {
        private LoginValidator loginValidator;

        @Override
        public void onClick( View v )
        {
            getUserInput();
            loginValidator = new LoginValidator(Login.this, username, password);
            loginValidator.validate();
        }

        private void getUserInput()
        {
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch( item.getItemId() )
        {
            case android.R.id.home :
                this.finish();
                break;
        }

        return true;
    }

}