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
    private String email;
    private String mobileNumber;
    private String country;
    private String bloodGroup;
    private boolean isDonor;

    private String commandCreateUserTable;
    private String commandInsertIntoUser;
    private String commandCountUsername;
    private String commandCountMobileNumber;
    private  String commandCountEmail;

    private ExtraTableCreatorCumInserter extraTableCreatorCumInserter;

    private int countUserNames;
    private int countMobiles;
    private int countEmails;


    private ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;
    private DataBase dataBase;
    private String toastMessage;

    public SignupValidator(Context inpContext, String inpName, String inpUsername, String inpPassword, String inpEmail, String inpMobileNumber, String inpCountry, String inpBloodGroup, boolean inpIsDonor )
    {
        context = inpContext;
        name = inpName;
        username = inpUsername;
        password = inpPassword;
        email = inpEmail;
        mobileNumber = inpMobileNumber;
        country = inpCountry;
        bloodGroup = inpBloodGroup;
        isDonor = inpIsDonor;
    }

    public void validate()
    {
        initializeCommands();
        dataBase = new DataBase(context);
        sendCommands();
        toastMessage = "Welcome to Blood Bank!!!";

        if( countUserNames == 1 )
        {
            toastMessage = "Username Already Exists!!!";
        }

        else if( countMobiles == 1 )
        {
            toastMessage = "Mobile number already registered!!!";
        }

        else if( countEmails == 1 )
        {
            toastMessage = "Email already registered!!!";
        }

        else
        {
            storeUserInfo();
        }

        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
    }

    private void storeUserInfo()
    {
        dataBase.executeQuery(commandInsertIntoUser, true);
        extraTableCreatorCumInserter = new ExtraTableCreatorCumInserter(bloodGroup, isDonor, username, name, mobileNumber, email, country, context);
        extraTableCreatorCumInserter.start();
    }

    private void initializeCommands()
    {
        StringBuilder stringBuilder = new StringBuilder();
        commandCreateUserTable = "CREATE TABLE IF NOT EXISTS user(name VARCHAR(100) NOT NULL, username VARCHAR(100), password VARCHAR(100) NOT NULL, mobileNumber VARCHAR(20) NOT NULL, email VARCHAR(100) NOT NULL, country VARCHAR(50) NOT NULL, isDonor BOOL NOT NULL, bloodGroup VARCHAR(5) NOT NULL, PRIMARY KEY(username) );";

        stringBuilder.append("INSERT INTO user VALUES('").append(name).append("','").append(username).append("','").append(password).append("','").append(mobileNumber).append("','").append(email).append("','").append(country).append("',").append(isDonor).append(",'").append(bloodGroup).append("');");
        commandInsertIntoUser = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("select count(*) from user where username='").append(username).append("';");
        commandCountUsername = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("select count(*) from user where mobileNumber='").append(mobileNumber).append("';");
        commandCountMobileNumber = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("select count(*) from user where email='").append(email).append("';");
        commandCountEmail = stringBuilder.toString();
    }

    private void sendCommands()
    {
        try
        {
            dataBase.executeQuery(commandCreateUserTable, true);

            dataBase.executeQuery(commandCountUsername, false);
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countUserNames = resultSet.getInt(1);

            if(countUserNames == 1)
            {
                return;
            }

            dataBase.executeQuery(commandCountMobileNumber, false);
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countMobiles = resultSet.getInt(1);

            if(countMobiles == 1)
            {
                return;
            }

            dataBase.executeQuery(commandCountEmail, false);
            resultSet = dataBase.getResultSet();
            resultSetMetaData = resultSet.getMetaData();
            resultSet.next();
            countEmails = resultSet.getInt(1);

            if( countEmails == 1 )
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
