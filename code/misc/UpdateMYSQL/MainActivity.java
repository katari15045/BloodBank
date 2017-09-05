
package com.example.root.accessmysql;
import android.app.ProgressDialog;
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


public class MainActivity extends AppCompatActivity
{
    private EditText editTextURL;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextCommand;
    private Button buttonSend;

    private String url;
    private String userName;
    private String password;
    private String command;

    private MyOnClickListener myOnClickListener;
    private ProgressDialog progressDialog;
    private BackGroundThread backGroundThread;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        myOnClickListener = new MyOnClickListener();
        buttonSend.setOnClickListener(myOnClickListener);

    }

    private void initializeViews()
    {
        editTextURL = (EditText) findViewById(R.id.editTextURL);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextCommand = (EditText) findViewById(R.id.editTextCommand);
        buttonSend = (Button) findViewById(R.id.buttonSend);
    }

    private class MyOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            getUserInput();
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Connecting to Database...", true);
            backGroundThread = new BackGroundThread();
            backGroundThread.execute();
        }

        private void getUserInput()
        {
            url = editTextURL.getText().toString();
            userName = editTextUsername.getText().toString();
            password = editTextPassword.getText().toString();
            command = editTextCommand.getText().toString();
        }

    }

    private class BackGroundThread extends AsyncTask<String, Void, String>
    {
        private Connection connection;
        private Statement statement;

        @Override
        protected String doInBackground(String... parameters)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, userName, password);
                statement = connection.createStatement();
                statement.executeUpdate(command);

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

        @Override
        protected void onPostExecute(String parameter)
        {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Command sent!!!", Toast.LENGTH_SHORT).show();
        }
    }

}
