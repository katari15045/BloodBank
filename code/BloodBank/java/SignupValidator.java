package com.example.root.home;

import android.content.Context;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Created by root on 2/4/17.
 */

public class SignupValidator
{
    private Context context;

    private String name;
    private String username;
    private String password;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    private String commandCountUsername;


    private int countUserNames;


    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    private DataBase dataBase;
    private String toastMessage;

    public SignupValidator(Context inpContext, String inpName, String inpUsername, String inpPassword, String inpCountry, String inpBloodGroup, boolean inpIsDonor )
    {
        context = inpContext;
        name = inpName;
        username = inpUsername;
        password = inpPassword;
        country = inpCountry;
        bloodGroup = inpBloodGroup;
        isDonor = inpIsDonor;
    }

    public boolean validate()
    {
        initializeCommands();
        sendCommands();

        if( countUserNames == 1 )
        {
            toastMessage = "Username Already Exists!!!";
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
            return false;
        }

        else
        {
            return true;
        }

    }

    private void initializeCommands()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("select count(*) from user where username='").append(username).append("';");
        commandCountUsername = stringBuilder.toString();
    }

    private void sendCommands()
    {
        dataBase = new DataBase(context);

        try
        {
            dataBase.executeQuery(commandCountUsername, false);
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countUserNames = resultSet.getInt(1);

            if(countUserNames == 1)
            {
                return;
            }
        }

        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
