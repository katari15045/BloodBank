package com.example.root.home;

import android.content.Context;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;

/**
 * Created by root on 10/4/17.
 */

public class TargetTableFinder
{
    private Context context;
    private String bloodGroup;

    private TableDecider tableDecider;
    private IsPositiveDecider isPositiveDecider;

    private LinkedHashSet<String> targetTableSet;

    public TargetTableFinder(Context inpContext)
    {
        context = inpContext;
    }


    public void find(String inpBloodGroup)
    {
        bloodGroup = inpBloodGroup;
        initializeObjects();
        addSameGroup();
        addOGroup();    // Till this point all groups except AB are taken care
        handleAB();
    }

    public LinkedHashSet<String> getTables()
    {
        return targetTableSet;
    }

    private void initializeObjects()
    {
        targetTableSet = new LinkedHashSet<String>();
        tableDecider = new TableDecider();
        isPositiveDecider = new IsPositiveDecider();
    }

    private void checkAndAddToSet()
    {
        DataBase dataBase = new DataBase(context);
        dataBase.executeQuery( "SHOW TABLES LIKE '" + tableDecider.getTable() + "';", false );
        ResultSet resultSet = dataBase.getResultSet();

        try
        {
            if( resultSet.next() )
            {
                targetTableSet.add( tableDecider.getTable() );
            }
        }

        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }


    private void addSameGroup()
    {
        tableDecider.decide(bloodGroup, true);
        checkAndAddToSet();
    }

    private void addOGroup()
    {
        if( !( bloodGroup.equals("O +") || bloodGroup.equals("O -") ) )
        {
            tableDecider.decide( "O +", true );
            checkAndAddToSet();
        }
    }

    private void handleAB()
    {
        if( bloodGroup.equals("AB +") || bloodGroup.equals("AB -") )
        {
            tableDecider.decide( "A +", true );
            checkAndAddToSet();

            tableDecider.decide( "B +", true );
            checkAndAddToSet();
        }
    }


}
