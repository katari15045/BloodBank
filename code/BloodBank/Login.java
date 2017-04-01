package com.example.root.home;

/**
 * Created by root on 28/3/17.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Login extends AppCompatActivity
{
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonSignIn;

    private String username;
    private String password;

    private ProgressDialog progressDialog;

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
        private BackgroundThread backgroundThread;

        @Override
        public void onClick( View v )
        {
            getUserInput();
            backgroundThread = new BackgroundThread();
            progressDialog = ProgressDialog.show( Login.this, "", "Connecting to Database...",true );
            backgroundThread.execute();

        }

        private void getUserInput()
        {
            username = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
        }
    }

    private class BackgroundThread extends AsyncTask<String, Void, String>
    {
        private Connection connection;
        private Statement statement;
        private ResultSet resultSet;
        private ResultSetMetaData resultSetMetaData;
        private StringBuilder result;

        private String serverDatabase;
        private String serverIpAddress;
        private String serverPortNumber;
        private String mYSQLDatabaseName;
        private String serverUrl;
        private String serverUsername;
        private String serverPassword;

        private String commandCountUsernames;
        private String commandCountPasswords;

        private int countUsernames;
        private int countPasswords;
        private String toastMessage;

        @Override
        protected String doInBackground(String... parameters)
        {
            try
            {
                initializeServerDetails();
                initializeCommands();

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(serverUrl, serverUsername, serverPassword);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(commandCountUsernames);
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countUsernames = resultSet.getInt(1);

                if( countUsernames == 0 )
                {
                    return null;
                }

                resultSet = statement.executeQuery(commandCountPasswords);
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countPasswords = resultSet.getInt(1);
            }

            catch( ClassNotFoundException e )
            {
                e.printStackTrace();
            }

            catch ( SQLException e)
            {
                e.printStackTrace();
            }

            return null;
        }

        private void initializeServerDetails()
        {
            StringBuilder stringBuilder = new StringBuilder();
            serverDatabase = "mysql";
            serverIpAddress = "192.168.56.134";
            serverPortNumber = "3306";
            mYSQLDatabaseName = "test_db";

            stringBuilder.append("jdbc:").append(serverDatabase).append("://").append(serverIpAddress).append(":").append(serverPortNumber).append("/").append(mYSQLDatabaseName);
            serverUrl = stringBuilder.toString();
            serverUsername = "test_user";
            serverPassword = "Test_user9977";
        }

        private void initializeCommands()
        {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("select count(*) from user where username='").append(username).append("';");
            commandCountUsernames = stringBuilder.toString();

            stringBuilder.setLength(0);
            stringBuilder.append("select count(*) from user where username='").append(username).append("' and password='").append(password).append("';");
            commandCountPasswords = stringBuilder.toString();

            countUsernames = -1;
            countPasswords = -1;
        }

        @Override
        protected void onPostExecute(String parameter)
        {
            progressDialog.dismiss();

            if( countUsernames == 0 )
            {
                toastMessage = "No such user Exists!!!";
            }

            if( countPasswords == 1 )
            {
                toastMessage = "Login Successful!!!";
            }

            else if(countPasswords == 0)
            {
                toastMessage = "Invalid Password!!!";
            }

            Toast.makeText(Login.this, toastMessage, Toast.LENGTH_SHORT).show();

            if( countPasswords == 1 )
            {
                Intent intent = new Intent( Login.this, Home.class );
                startActivity(intent);
            }
        }
    }
}