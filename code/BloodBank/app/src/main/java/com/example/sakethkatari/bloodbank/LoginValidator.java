package com.example.sakethkatari.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
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

        Log.d("SAKETH 1", "->" + "After sending commands in Login Validator");

        if( countUsernames == 0 )
        {
            Log.d("SAKETH 1", "->" + "countUsernames=0 in LoginValidator");
            toastMessage = "No such user Exists!!!";
        }

        if( countPasswords == 1 )
        {
            Log.d("SAKETH 1", "->" + "countPasswords=1 in LoginValidator");
            toastMessage = "Login Successful!!!";
        }

        else if(countPasswords == 0)
        {
            Log.d("SAKETH 1", "->" + "countPasswords=0 in LoginValidator");
            toastMessage = "Invalid Password!!!";
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();

        if( countPasswords == 1 )
        {
            ActivityLauncher activityLauncher = new ActivityLauncher();
            activityLauncher.launch();

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

            if( resultSet == null )
            {
                countUsernames = 0;
            }

            else
            {
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countUsernames = resultSet.getInt(1);
            }

            if( countUsernames == 0 )
            {
                return;
            }

            dataBase.executeQuery( commandCountPasswords, false );
            resultSet = dataBase.getResultSet();

            if( resultSet == null )
            {
                countPasswords = 0;
            }

            else
            {
                resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                countPasswords = resultSet.getInt(1);
            }
        }

        catch ( SQLException e)
        {
            e.printStackTrace();
        }
    }

    private class ActivityLauncher
    {
        private String name;
        private String mobileNumber;
        private String emailId;
        private String country;
        private boolean isDonor;
        private String bloodGroup;

        private Intent intent;

        protected void launch()
        {
            startHomeActivity();
        }

        private void startHomeActivity()
        {
            getUserInfo();

            if(isDonor)
            {
                intent = new Intent( context, HomeDonor.class );
            }

            else
            {
                intent = new Intent( context, HomeRecipient.class );
            }

            fillIntent();
            context.startActivity(intent);
        }

        private void fillIntent()
        {
            intent.putExtra("labelStartActivity", "Login");
            intent.putExtra("labelUsername", username);
            intent.putExtra("labelPassword", password);
            intent.putExtra("labelName", name);
            intent.putExtra("labelMobile", mobileNumber);
            intent.putExtra("labelEmail", emailId);
            intent.putExtra("labelCountry", country);
            intent.putExtra("labelIsDonor", isDonor);
            intent.putExtra("labelBloodGroup", bloodGroup);
        }

        private void getUserInfo()
        {
            dataBase = new DataBase(context);
            String command = getCommand();
            dataBase.executeQuery( command, false );
            resultSet = dataBase.getResultSet();
            parseResultSet();
        }

        private String getCommand()
        {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("select * from user where username='").append(username).append("';");
            return stringBuilder.toString();
        }

        private void parseResultSet()
        {
            try
            {
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                resultSet.next();
                name = resultSet.getString(1);
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
    }
}
