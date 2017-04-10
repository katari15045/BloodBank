package com.example.root.home;

import android.widget.TextView;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 10/4/17.
 */

public class ResultDisplayer
{
    private String table;
    private ResultSet resultSet;

    private boolean isPositive;
    private String name;
    private String mobile;
    private String email;
    private String country;
    private String bloodGroup;
    private StringBuilder result;

    private BloodGroupGetter bloodGroupGetter;

    public ResultDisplayer()
    {
        result = new StringBuilder();
        bloodGroupGetter = new BloodGroupGetter();
    }

    public void append(String inpTable, ResultSet inpResultSet)
    {
        table = inpTable;
        resultSet = inpResultSet;
        parseResultSet();
    }

    private void parseResultSet()
    {
        try
        {
            while( resultSet.next() )
            {
                isPositive = resultSet.getBoolean(2);
                name = resultSet.getString(3);
                mobile = resultSet.getString(4);
                email = resultSet.getString(5);
                country = resultSet.getString(6);
                bloodGroup = bloodGroupGetter.get(table, isPositive);

                appendToResult();
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void appendToResult()
    {
        result.append("Name -> ").append(name).append("\n");
        result.append("BloodGroup -> ").append(bloodGroup).append("\n");
        result.append("Mobile -> ").append(mobile).append("\n");
        result.append("Email -> ").append(email).append("\n");
        result.append("Country -> ").append(country).append("\n").append("\n");
    }

    public void display(TextView textView)
    {
        textView.setText( result.toString() );
    }

}
