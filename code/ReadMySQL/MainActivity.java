package com.example.root.accessmysqltwoway;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    private TextView textViewResult;

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
        textViewResult = (TextView) findViewById(R.id.textViewResult);
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
        private ResultSet resultSet;
        private ResultSetMetaData resultSetMetaData;
        private StringBuilder result;

        @Override
        protected String doInBackground(String... parameters)
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(url, userName, password);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(command);
                resultSetMetaData = resultSet.getMetaData();
                displayResult();

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

        private void displayResult()
        {
            result = new StringBuilder();

            try
            {
                int cols = resultSetMetaData.getColumnCount();
                int count;
                String type;

                while( resultSet.next() )
                {
                    count = 1;


                    while( count <= cols )
                    {
                        type = resultSetMetaData.getColumnTypeName(count);

                        if( type.equalsIgnoreCase("int") || type.equalsIgnoreCase("bigint") )
                        {
                            result.append( Integer.toString( resultSet.getInt(count) ) );
                        }

                        else if( type.equalsIgnoreCase("float") || type.equalsIgnoreCase("real") )
                        {
                            result.append( Float.toString( resultSet.getFloat(count) ) );
                        }

                        else if( type.equals("VARCHAR") )
                        {
                            result.append( resultSet.getString(count) );
                        }

                        else if( type.equalsIgnoreCase("TINYINT") ) // BOOLEAN
                        {
                            result.append( resultSet.getBoolean(count) );
                        }

                        if( count != cols )
                        {
                            result.append(" -> ");
                        }

                        count = count + 1;
                    }

                    result.append("\n");
                }

            }

            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String parameter)
        {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, "Command Sent!!!", Toast.LENGTH_SHORT).show();
            textViewResult.setText(result);
        }
    }

}