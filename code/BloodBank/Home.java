package com.example.root.home;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by root on 29/3/17.
 */

public class Home extends AppCompatActivity
{
    private Intent intent;
    private TextView textViewUserInfo;

    private DataBase database;
    private String command;
    private ResultSet resultSet;

    private String name;
    private String username;
    private String password;
    private String mobileNumber;
    private String emailId;
    private String country;
    private boolean isDonor;
    private String bloodGroup;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        intent = getIntent();
        username = intent.getStringExtra("labelUsername");
        textViewUserInfo = (TextView) findViewById(R.id.textViewUserInfo);

        getUserInfo();
    }

    private void getUserInfo()
    {
        database = new DataBase(Home.this);
        prepareCommands();
        database.executeQuery( command, false );
        resultSet = database.getResultSet();
        parseResultSet();
    }

    private void prepareCommands()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("select * from user where username='").append(username).append("';");
        command = stringBuilder.toString();
    }

    private void parseResultSet()
    {
        try
        {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            name = resultSet.getString(1);
            password = resultSet.getString(3);
            mobileNumber = resultSet.getString(4);
            emailId = resultSet.getString(5);
            country = resultSet.getString(6);
            isDonor = resultSet.getBoolean(7);
            bloodGroup = resultSet.getString(8);
        }

        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch ( item.getItemId() )
        {
            case R.id.itemProfile:
                handleProfileItem();
                return  true;
        }

        return true;
    }

    private void handleProfileItem()
    {
        Intent intent = new Intent( Home.this, Profile.class );
        startActivity(intent);
    }
}
