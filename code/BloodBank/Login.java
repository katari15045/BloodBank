package com.example.root.home;

/**
 * Created by root on 28/3/17.
 */


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity
{
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignIn;

    private String username;
    private String password;

    private LoginValidator loginValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        buttonSignIn.setOnClickListener( new MyOnClickListener() );
    }

    private void initializeViews()
    {
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonSignIn = (Button) findViewById(R.id.buttonSignIn);
    }

    private class MyOnClickListener implements View.OnClickListener
    {
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

}