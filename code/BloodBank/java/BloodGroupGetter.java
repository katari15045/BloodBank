package com.example.root.home;

/**
 * Created by root on 10/4/17.
 */

public class BloodGroupGetter
{
    private String table;
    private boolean isPositive;
    private StringBuilder bloodGroup;

    public BloodGroupGetter()
    {
        bloodGroup = new StringBuilder();
    }

    public String get(String inpTable, boolean inpIsPositive)
    {
        table = inpTable;
        isPositive = inpIsPositive;
        bloodGroup.setLength(0);
        findLHS();
        findRHS();

        return bloodGroup.toString();
    }

    private void findLHS()
    {
        if( table.charAt(0) == 'a' )
        {
            if( table.charAt(1) == 'D' )
            {
                bloodGroup.append("A ");
            }

            else
            {
                bloodGroup.append("AB ");
            }
        }

        else if( table.charAt(0) == 'b' )
        {
            bloodGroup.append("B ");
        }

        else
        {
            bloodGroup.append("O ");
        }
    }

    private void findRHS()
    {
        if(isPositive)
        {
            bloodGroup.append("+");
        }

        else
        {
            bloodGroup.append("-");
        }
    }

}
