package com.example.root.home;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 13/4/17.
 */

public class MobileNumberCounter
{
    private String mobile;
    private int countMobiles;
    private String commandCountMobiles;

    private Context context;
    private DataBase dataBase;
    private ResultSet resultSet;

    public MobileNumberCounter(Context inpContext)
    {
        context = inpContext;
    }

    public int getCount(String inpMobile)
    {
        mobile = inpMobile;
        initializeCommand();
        countMobiles();

        return countMobiles;
    }

    private void initializeCommand()
    {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("select count(*) from user where mobileNumber='").append(mobile).append("';");
        commandCountMobiles = stringBuilder.toString();
    }

    private void countMobiles()
    {
        dataBase = new DataBase(context);
        dataBase.executeQuery(commandCountMobiles, false);
        resultSet = dataBase.getResultSet();

        try
        {
            resultSet.getMetaData();
            resultSet.next();

            countMobiles = resultSet.getInt(1);
        }

        catch( SQLException e)
        {
            e.printStackTrace();
        }
    }

}
