package com.example.root.home;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 13/4/17.
 */

public class AttributeCounter
{
    private String mobile;
    private String email;
    private String commandCountMobiles;
    private String commandCountEmail;

    private String currentCommand;
    private int currentCount;

    private Context context;
    private DataBase dataBase;
    private ResultSet resultSet;

    public AttributeCounter(Context inpContext)
    {
        context = inpContext;
    }

    public int countMobiles(String inpMobile)
    {
        mobile = inpMobile;
        initializeCommands();
        currentCommand = commandCountMobiles;
        countAttributes();

        return currentCount;
    }

    public int countEmails(String inpEmail)
    {
        email = inpEmail;
        initializeCommands();
        currentCommand = commandCountEmail;
        countAttributes();

        return currentCount;
    }

    private void initializeCommands()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("select count(*) from user where mobileNumber='").append(mobile).append("';");
        commandCountMobiles = stringBuilder.toString();

        stringBuilder.setLength(0);
        stringBuilder.append("select count(*) from user where email='").append(email).append("';");
        commandCountEmail = stringBuilder.toString();

    }

    private void countAttributes()
    {
        currentCount = 0;
        dataBase = new DataBase(context);
        dataBase.executeQuery(currentCommand, false);
        resultSet = dataBase.getResultSet();

        try
        {
            resultSet.getMetaData();
            resultSet.next();

            currentCount = resultSet.getInt(1);
        }

        catch( SQLException e)
        {
            e.printStackTrace();
        }
    }

}
