package com.example.root.home;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by root on 2/4/17.
 */

public class LoginValidator
{
    private Context context;
    private String username;
    private String password;

    private DataBase dataBase;
    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    private StringBuilder result;

    private String commandCountUsernames;
    private String commandCountPasswords;

    private int countUsernames;
    private int countPasswords;
    private String toastMessage;

    public LoginValidator(Context inpContext, String inpUsername, String inpPassword)
    {
        context = inpContext;
        username = inpUsername;
        password = inpPassword;
    }

    public void validate()
    {
        dataBase = new DataBase(context);
        initializeCommands();
        sendCommands();

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

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

        if( countPasswords == 1 )
        {
            Intent intent = new Intent( context, Home.class );
            intent.putExtra("labelUsername", username);
            context.startActivity(intent);
        }
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

    private void sendCommands()
    {
        try
        {
            dataBase.executeQuery( commandCountUsernames, false );
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countUsernames = resultSet.getInt(1);

            if( countUsernames == 0 )
            {
                return;
            }

            dataBase.executeQuery( commandCountPasswords, false );
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countPasswords = resultSet.getInt(1);
        }

        catch ( SQLException e)
        {
            e.printStackTrace();
        }
    }
}
