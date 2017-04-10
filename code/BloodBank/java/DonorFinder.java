package com.example.root.home;

import android.content.Context;

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

    private TargetTableFinder targetTableFinder;
    private LinkedHashSet<String> targetTableSet;
    private DataBase dataBase;

    private StringBuilder stringBuilder;
    private String currentTable;
    private StringBuilder result;

    public void find(String inpBloodGroup, boolean inpIsPositive, String inpCountry, Context inpContext)
    {
        bloodGroup = inpBloodGroup;
        isPositive = inpIsPositive;
        country = inpCountry;
        context = inpContext;
        dataBase = new DataBase(context);
        stringBuilder = new StringBuilder();
        result = new StringBuilder();

        findTargetTables();
    }

    private void findTargetTables()
    {
        targetTableFinder = new TargetTableFinder(context);
        targetTableFinder.find(bloodGroup);
        targetTableSet = targetTableFinder.getTables();
        executeCommands();
    }

    private void executeCommands()
    {
        Iterator<String> iterator = targetTableSet.iterator();
        result.setLength(0);

        while( iterator.hasNext() )
        {
            currentTable = iterator.next();
            prepareCommandAndExecute(currentTable);
            getDataFromDatabase();
        }
    }

    private void prepareCommandAndExecute(String table)
    {
        stringBuilder.setLength(0);

        if(isPositive)
        {
            stringBuilder.append("SELECT * FROM ").append(table).append(";");
        }

        else
        {
            stringBuilder.append("SELECT * FROM ").append(table).append(" WHERE isPositive=false;");
        }

        dataBase.executeQuery( stringBuilder.toString(), false );
    }

    private void getDataFromDatabase()
    {
        try
        {
            ResultSet resultSet = dataBase.getResultSet();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            storeResult( resultSet, resultSetMetaData );
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void storeResult( ResultSet resultSet, ResultSetMetaData resultSetMetaData )
    {
        try
        {
            while( resultSet.next() )
            {
                result.append( resultSet.getString(1) ).append(" -> ");
                result.append( resultSet.getBoolean(2) ).append(" -> ");
                result.append( resultSet.getString(3) ).append(" -> ");
                result.append( resultSet.getString(4) ).append(" -> ");
                result.append( resultSet.getString(5) ).append(" -> ");
                result.append( resultSet.getString(6) );
                result.append("\n");
                result.append("\n");// Extra
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public String getResult()
    {
        return result.toString();
    }

}
