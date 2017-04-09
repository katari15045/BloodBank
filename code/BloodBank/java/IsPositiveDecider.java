package com.example.root.home;

/**
 * Created by root on 9/4/17.
 */

public class IsPositiveDecider
{
    private String bloodGroup;
    private boolean isPositive;

    public IsPositiveDecider( String inpBloodGroup )
    {
        bloodGroup = inpBloodGroup;
    }

    public void decide()
    {
        if( bloodGroup.contains("+") )
        {
            isPositive = true;
        }

        else
        {
            isPositive = false;
        }
    }

    public boolean getDecision()
    {
        return  isPositive;
    }
}
