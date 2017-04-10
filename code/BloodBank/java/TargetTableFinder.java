package com.example.root.home;

import java.util.LinkedHashSet;

/**
 * Created by root on 10/4/17.
 */

public class TargetTableFinder
{
    private String bloodGroup;

    private TableDecider tableDecider;
    private IsPositiveDecider isPositiveDecider;

    private LinkedHashSet<String> targetTableSet;

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

    private void addSameGroup()
    {
        tableDecider.decide(bloodGroup, true);
        targetTableSet.add( tableDecider.getTable() );
    }

    private void addOGroup()
    {
        if( !( bloodGroup.equals("O +") || bloodGroup.equals("O -") ) )
        {
            tableDecider.decide( "O +", true );
            targetTableSet.add( tableDecider.getTable() );
        }
    }

    private void handleAB()
    {
        if( bloodGroup.equals("AB +") || bloodGroup.equals("AB -") )
        {
            tableDecider.decide( "A +", true );
            targetTableSet.add( tableDecider.getTable() );

            tableDecider.decide( "B +", true );
            targetTableSet.add( tableDecider.getTable() );
        }
    }


}
