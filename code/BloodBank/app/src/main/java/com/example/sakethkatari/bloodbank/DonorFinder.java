package com.example.sakethkatari.bloodbank;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Created by root on 10/4/17.
 */

public class DonorFinder
{
    private String bloodGroup;
    private boolean isPositive;
    private String country;
    private Context context;
    private Intent intent;

    private TargetTableFinder targetTableFinder;
    private LinkedHashSet<String> targetTableSet;
    private DataBase dataBase;

    private StringBuilder stringBuilder;
    private String currentTable;
    private StringBuilder result;

    public DonorFinder()
    {
        result = new StringBuilder();
    }

    void find(String inpBloodGroup, boolean inpIsPositive, String inpCountry, Context inpContext, Intent inpIntent)
    {
        bloodGroup = inpBloodGroup;
        isPositive = inpIsPositive;
        country = inpCountry;
        context = inpContext;
        intent = inpIntent;
        dataBase = new DataBase(context);
        stringBuilder = new StringBuilder();

        findTargetTables();
        executeCommands();
        startNewActivity();
    }

    private void findTargetTables()
    {
        targetTableFinder = new TargetTableFinder(context);
        targetTableFinder.find(bloodGroup);
        targetTableSet = targetTableFinder.getTables();
    }

    private void executeCommands()
    {
        Iterator<String> iterator = targetTableSet.iterator();

        while( iterator.hasNext() )
        {
            currentTable = iterator.next();
            prepareCommandAndExecute(currentTable);
            getDataFromDatabase();
        }
    }

    private void startNewActivity()
    {
        intent.putExtra("labelResult", result.toString());
        context.startActivity(intent);
    }

    private void prepareCommandAndExecute(String table)
    {
        stringBuilder.setLength(0);

        if(isPositive)
        {
            if( country.equals("none") )
            {
                stringBuilder.append("SELECT * FROM ").append(table).append(";");
            }

            else
            {
                stringBuilder.append("SELECT * FROM ").append(table).append(" WHERE country='").append(country)
                        .append("';");
            }
        }

        else
        {
            if( country.equals("none") )
            {
                stringBuilder.append("SELECT * FROM ").append(table).append(" WHERE isPositive=false;");
            }

            else
            {
                stringBuilder.append("SELECT * FROM ").append(table)
                        .append(" WHERE isPositive=false and country='").append(country).append("';");
            }
        }

        dataBase.executeQuery( stringBuilder.toString(), false );
    }

    private void getDataFromDatabase()
    {
        ResultSet resultSet = dataBase.getResultSet();
        parseResultSet(currentTable, resultSet);
    }

    private void parseResultSet(String table, ResultSet resultSet)
    {
        BloodGroupGetter bloodGroupGetter = new BloodGroupGetter();
        String tempName, tempMobile, tempEmail, tempCountry, tempBloodGroup;
        boolean tempIsPositive;

        try
        {
            while( resultSet.next() )
            {
                tempIsPositive = resultSet.getBoolean(2);
                tempName = resultSet.getString(3);
                tempMobile = resultSet.getString(4);
                tempEmail = resultSet.getString(5);
                tempCountry = resultSet.getString(6);
                tempBloodGroup = bloodGroupGetter.get(table, tempIsPositive);

                appendToResult(tempName, tempMobile, tempEmail, tempCountry, tempBloodGroup);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void appendToResult(String tempName, String tempMobile, String tempEmail, String tempCountry, String tempBloodGroup)
    {
        result.append("Name -> ").append(tempName).append("\n");
        result.append("BloodGroup -> ").append(tempBloodGroup).append("\n");
        result.append("Mobile -> ").append(tempMobile).append("\n");
        result.append("Email -> ").append(tempEmail).append("\n");
        result.append("Country -> ").append(tempCountry).append("\n").append("\n");
    }
}
