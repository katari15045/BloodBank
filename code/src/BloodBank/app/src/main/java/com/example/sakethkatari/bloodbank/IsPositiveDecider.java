package com.example.sakethkatari.bloodbank;

/**
 * Created by root on 9/4/17.
 */

public class IsPositiveDecider
{
    private String bloodGroup;
    private boolean isPositive;

    public void decide(String inpBloodGroup)
    {
        bloodGroup = inpBloodGroup;

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
