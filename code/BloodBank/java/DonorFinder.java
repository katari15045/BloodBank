package com.example.root.home;

import android.content.Context;
import android.widget.TextView;

import java.sql.ResultSet;
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
    private TextView textView;
    private Context context;

    private TargetTableFinder targetTableFinder;
    private ResultDisplayer resultDisplayer;
    private LinkedHashSet<String> targetTableSet;
    private DataBase dataBase;

    private StringBuilder stringBuilder;
    private String currentTable;

    public void find(String inpBloodGroup, boolean inpIsPositive, String inpCountry, TextView inpTextView, Context inpContext)
    {
        bloodGroup = inpBloodGroup;
        isPositive = inpIsPositive;
        country = inpCountry;
        textView = inpTextView;
        context = inpContext;
        dataBase = new DataBase(context);
        resultDisplayer = new ResultDisplayer();
        stringBuilder = new StringBuilder();

        findTargetTables();
        executeCommands();
        resultDisplayer.display(textView);
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
        ResultSet resultSet = dataBase.getResultSet();
        resultDisplayer.append(currentTable, resultSet);
    }
}
